package by.tananushka.project.dao.impl;

import by.tananushka.project.bean.Film;
import by.tananushka.project.bean.Show;
import by.tananushka.project.dao.DaoConstant;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.ShowDao;
import by.tananushka.project.dao.SqlColumnsName;
import by.tananushka.project.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * The type Show dao.
 */
public class ShowDaoImpl implements ShowDao {

	private static final String FIND_SHOW_BY_ID =
					"SELECT DISTINCT cinema_name, films_shows_id, film_id_fk, show_date_time, ticket_cost, show_free_places\n"
									+ "FROM films INNER JOIN (cinemas INNER JOIN films_shows\n"
									+ "ON (cinema_id = cinema_id_fk)) ON (film_id = film_id_fk)\n"
									+ "WHERE films_shows_id = ?;";
	private static final String FIND_ACTIVE_SHOWS_BY_DATE =
					"SELECT DISTINCT cinema_name, films_shows_id, film_id_fk, show_date_time, ticket_cost, show_free_places\n"
									+ "FROM films INNER JOIN (cinemas INNER JOIN films_shows\n"
									+ "ON (cinema_id = cinema_id_fk)) ON (film_id = film_id_fk)\n"
									+ "WHERE film_active = True AND show_active = True AND cinema_active = True\n"
									+ "AND show_date_time>=current_time() AND show_date_time>= TIMESTAMP(?)\n"
									+ "AND show_date_time < TIMESTAMPADD(day,1,?)\n"
									+ "GROUP BY cinema_name, films_shows_id, film_id_fk, show_date_time, ticket_cost, show_free_places\n"
									+ "ORDER BY cinema_name, show_date_time;";
	private static final String FIND_ACTIVE_SHOWS_BY_DATE_AND_CINEMA =
					"SELECT DISTINCT cinema_name, films_shows_id, film_id_fk, show_date_time, ticket_cost, show_free_places\n"
									+ "FROM films INNER JOIN (cinemas INNER JOIN films_shows \n"
									+ "ON (cinema_id = cinema_id_fk)) ON (film_id = film_id_fk)\n"
									+ "WHERE film_active = True AND show_active = True AND cinema_active = True\n"
									+ "AND show_date_time>=current_time() AND show_date_time>= TIMESTAMP(?)\n"
									+ "AND show_date_time < TIMESTAMPADD(day,1,?) AND cinema_name = ?\n"
									+ "GROUP BY cinema_name, films_shows_id, film_id_fk, show_date_time, ticket_cost, show_free_places\n"
									+ "ORDER BY cinema_name, show_date_time;";
	private static final String FIND_ACTIVE_SHOWS_BY_DATE_AND_CINEMA_ID =
					"SELECT DISTINCT cinema_name, films_shows_id, film_id_fk, show_date_time, ticket_cost, show_free_places\n"
									+ "FROM films INNER JOIN (cinemas INNER JOIN films_shows\n"
									+ "ON (cinema_id = cinema_id_fk)) ON (film_id = film_id_fk)"
									+ "WHERE film_active = True AND show_active = True AND cinema_active = True\n"
									+ "AND show_date_time>=current_time() AND show_date_time>= TIMESTAMP(?) "
									+ "AND show_date_time < TIMESTAMPADD(day,1,?) AND cinema_id = ?\n"
									+ "GROUP BY cinema_name, films_shows_id, film_id_fk, show_date_time, ticket_cost, show_free_places\n"
									+ "ORDER BY cinema_name, show_date_time;";
	private static final String FIND_DATES_FOR_ACTIVE_SHOWS =
					"SELECT DISTINCT show_date_time FROM films_shows "
									+ "WHERE show_active = True AND show_date_time>=current_time();";
	private static final String FIND_ACTIVE_CINEMAS_FOR_ACTIVE_SHOWS =
					"SELECT DISTINCT cinema_id, cinema_name FROM cinemas\n"
									+ "INNER JOIN films_shows ON cinema_id = cinema_id_fk\n"
									+ "WHERE cinema_active = True AND show_active = True\n"
									+ "ORDER BY cinema_name";
	private static final String FIND_ACTIVE_CINEMAS =
					"SELECT DISTINCT cinema_id, cinema_name FROM cinemas\n"
									+ "WHERE cinema_active = True\n"
									+ "ORDER BY cinema_name";
	private static final String FIND_INACTIVE_CINEMAS =
					"SELECT DISTINCT cinema_id, cinema_name FROM cinemas\n"
									+ "WHERE cinema_active = False\n"
									+ "ORDER BY cinema_name";
	private static final String UPDATE_SHOW =
					"UPDATE films_shows\n"
									+ "SET film_id_fk = ?, cinema_id_fk = (SELECT cinema_id FROM cinemas WHERE cinema_name = ?),"
									+ " show_date_time = ?, ticket_cost = ?, show_free_places = ?\n"
									+ "WHERE films_shows_id = ?;";
	private static final String CREATE_SHOW =
					"INSERT INTO films_shows\n"
									+ "(film_id_fk, cinema_id_fk, show_date_time, ticket_cost, show_free_places)"
									+ "VALUES (?, (SELECT cinema_id FROM cinemas WHERE cinema_name = ?), ?, ?, ?);";
	private static final String SET_SHOW_INACTIVE =
					"UPDATE films_shows\n"
									+ "SET show_active = false WHERE films_shows_id = ?;";
	private static final String CREATE_CINEMA =
					"INSERT INTO cinemas (cinema_name) VALUE (?);";
	private static final String UPDATE_CINEMA =
					"UPDATE cinemas SET cinema_name = ? WHERE cinema_id = ?;";
	private static final String SET_CINEMA_INACTIVE =
					"UPDATE cinemas\n"
									+ "SET cinema_active = false WHERE cinema_name = ?;";
	private static final String SET_CINEMA_ACTIVE =
					"UPDATE cinemas\n"
									+ "SET cinema_active = true WHERE cinema_name = ?;";
	private static final String CHECK_CINEMA_NAME = "SELECT cinema_name FROM cinemas "
					+ "WHERE cinema_name = ? AND cinema_active = true";
	private static final String CHECK_CINEMA_NAME_BY_ID = "SELECT cinema_name FROM cinemas "
					+ "WHERE cinema_name = ? AND cinema_id <> ?";
	private static Logger log = LogManager.getLogger();
	private static ShowDao showDao = new ShowDaoImpl();
	private final Calendar timezone = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"));

	private ShowDaoImpl() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static ShowDao getInstance() {
		return showDao;
	}

	@Override
	public Optional<Show> findShowById(int showId) throws DaoException {
		Optional<Show> showOptional = Optional.empty();
		ResultSet findFilmResultSet;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findShowStatement = connection.prepareStatement(
						     FIND_SHOW_BY_ID);
		     PreparedStatement findFilmStatement =
						     connection.prepareStatement(DaoConstant.FIND_FILM_BY_ID);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement =
						     connection.prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findShowStatement.setInt(1, showId);
			ResultSet findShowResultSet = findShowStatement.executeQuery();
			while (findShowResultSet.next()) {
				Show show = new Show();
				String cinema = findShowResultSet.getString(SqlColumnsName.CINEMA_NAME);
				show.setCinemaName(cinema);
				show.setId(findShowResultSet.getInt(SqlColumnsName.FILMS_SHOWS_ID));
				show.setDateTime(findShowResultSet.getTimestamp(SqlColumnsName.SHOW_DATE_TIME,
								timezone).toLocalDateTime());
				show.setCost(findShowResultSet.getBigDecimal(SqlColumnsName.TICKET_COST));
				show.setFreePlace(findShowResultSet.getInt(SqlColumnsName.SHOW_FREE_PLACES));
				int filmId = findShowResultSet.getInt(SqlColumnsName.FILM_ID_FK);
				findFilmStatement.setInt(1, filmId);
				findFilmResultSet = findFilmStatement.executeQuery();
				Film film = new Film();
				if (findFilmResultSet.first()) {
					film = findFilmQuery(findFilmResultSet, findCountriesStatement, findGenresStatement);
				}
				show.setFilm(film);
				showOptional = Optional.of(show);
			}
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting show by ID", e);
		}
		return showOptional;
	}

	@Override
	public Map<String, List<Show>> findShow(LocalDate date) throws DaoException {
		Map<String, List<Show>> showsMap;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findShowStatement = connection.prepareStatement(
						     FIND_ACTIVE_SHOWS_BY_DATE);
		     PreparedStatement findFilmStatement =
						     connection.prepareStatement(DaoConstant.FIND_FILM_BY_ID);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement =
						     connection.prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			Timestamp timestamp = Timestamp.valueOf(date.atTime(3, 0));
			findShowStatement.setTimestamp(1, timestamp);
			findShowStatement.setTimestamp(2, timestamp);
			showsMap = findShowsMapQuery(findShowStatement, findFilmStatement, findCountriesStatement,
							findGenresStatement);
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting shows by date", e);
		}
		log.debug("Return showMap");
		return showsMap;
	}

	@Override
	public Map<String, List<Show>> findShow(LocalDate date, String cinema) throws DaoException {
		Map<String, List<Show>> showsMap;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findShowStatement = connection
						     .prepareStatement(FIND_ACTIVE_SHOWS_BY_DATE_AND_CINEMA);
		     PreparedStatement findFilmStatement =
						     connection.prepareStatement(DaoConstant.FIND_FILM_BY_ID);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement =
						     connection.prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			Timestamp timestamp = Timestamp.valueOf(date.atTime(3, 0));
			findShowStatement.setTimestamp(1, timestamp);
			findShowStatement.setTimestamp(2, timestamp);
			findShowStatement.setString(3, cinema);
			showsMap = findShowsMapQuery(findShowStatement, findFilmStatement, findCountriesStatement,
							findGenresStatement);
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting shows by date and cinema.", e);
		}
		return showsMap;
	}

	@Override
	public Map<String, List<Show>> findShow(LocalDate date, int cinemaId) throws DaoException {
		Map<String, List<Show>> showsMap;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findShowStatement = connection
						     .prepareStatement(FIND_ACTIVE_SHOWS_BY_DATE_AND_CINEMA_ID);
		     PreparedStatement findFilmStatement =
						     connection.prepareStatement(DaoConstant.FIND_FILM_BY_ID);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement =
						     connection.prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			Timestamp timestamp = Timestamp.valueOf(date.atTime(3, 0));
			findShowStatement.setTimestamp(1, timestamp);
			findShowStatement.setTimestamp(2, timestamp);
			findShowStatement.setInt(3, cinemaId);
			showsMap = findShowsMapQuery(findShowStatement, findFilmStatement, findCountriesStatement,
							findGenresStatement);
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting shows by date and cinema.", e);
		}
		return showsMap;
	}

	@Override
	public List<LocalDate> findDates() throws DaoException {
		List<LocalDate> datesList = new ArrayList<>();
		ResultSet findDatesResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findDatesStatement = connection.prepareStatement(
						     FIND_DATES_FOR_ACTIVE_SHOWS)) {
			findDatesResultSet = findDatesStatement.executeQuery();
			while (findDatesResultSet.next()) {
				LocalDate localDate = findDatesResultSet.getTimestamp(SqlColumnsName.SHOW_DATE_TIME,
								timezone).toLocalDateTime().toLocalDate();
				datesList.add(localDate);
			}
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting show dates.", e);
		} finally {
			closeResultSet(findDatesResultSet);
		}
		datesList = datesList.stream().distinct().collect(Collectors.toList());
		return datesList;
	}

	@Override
	public Map<Integer, String> findCinemasMap() throws DaoException {
		Map<Integer, String> cinemasMap = new LinkedHashMap<>();
		ResultSet findCinemasResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findCinemasStatement = connection.prepareStatement(
						     FIND_ACTIVE_CINEMAS_FOR_ACTIVE_SHOWS)) {
			findCinemasResultSet = findCinemasStatement.executeQuery();
			while (findCinemasResultSet.next()) {
				int cinemaId = findCinemasResultSet.getInt(SqlColumnsName.CINEMA_ID);
				String cinema = findCinemasResultSet.getString(SqlColumnsName.CINEMA_NAME);
				cinemasMap.put(cinemaId, cinema);
			}
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting cinemas.", e);
		} finally {
			closeResultSet(findCinemasResultSet);
		}
		return cinemasMap;
	}

	@Override
	public Map<Integer, String> findActiveCinemasMap() throws DaoException {
		Map<Integer, String> cinemasMap = new LinkedHashMap<>();
		ResultSet findCinemasResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findCinemasStatement = connection.prepareStatement(
						     FIND_ACTIVE_CINEMAS)) {
			findCinemasResultSet = findCinemasStatement.executeQuery();
			while (findCinemasResultSet.next()) {
				int cinemaId = findCinemasResultSet.getInt(SqlColumnsName.CINEMA_ID);
				String cinema = findCinemasResultSet.getString(SqlColumnsName.CINEMA_NAME);
				cinemasMap.put(cinemaId, cinema);
			}
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting cinemas.", e);
		} finally {
			closeResultSet(findCinemasResultSet);
		}
		return cinemasMap;
	}

	@Override
	public Map<Integer, String> findInactiveCinemasMap() throws DaoException {
		Map<Integer, String> cinemasMap = new LinkedHashMap<>();
		ResultSet findCinemasResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findCinemasStatement = connection.prepareStatement(
						     FIND_INACTIVE_CINEMAS)) {
			findCinemasResultSet = findCinemasStatement.executeQuery();
			while (findCinemasResultSet.next()) {
				int cinemaId = findCinemasResultSet.getInt(SqlColumnsName.CINEMA_ID);
				String cinema = findCinemasResultSet.getString(SqlColumnsName.CINEMA_NAME);
				cinemasMap.put(cinemaId, cinema);
			}
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting cinemas.", e);
		} finally {
			closeResultSet(findCinemasResultSet);
		}
		return cinemasMap;
	}

	@Override
	public Optional<Show> updateShow(Show show) throws DaoException {
		Optional<Show> showOptional = Optional.empty();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement updateShowStatement = connection.prepareStatement(UPDATE_SHOW)) {
			try {
				connection.setAutoCommit(false);
				updateShowStatement.setInt(1, show.getFilm().getId());
				updateShowStatement.setString(2, show.getCinemaName());
				updateShowStatement.setTimestamp(3, Timestamp.valueOf(show.getDateTime()),
								timezone);
				updateShowStatement.setBigDecimal(4, show.getCost());
				updateShowStatement.setInt(5, show.getFreePlace());
				updateShowStatement.setInt(6, show.getId());
				updateShowStatement.execute();
				connection.commit();
				showOptional = findShowById(show.getId());
			} catch (SQLException e) {
				connection.rollback();
				throw new DaoException("Transaction while updating show failed; not committed.", e);
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while updating show.", e);
		}
		return showOptional;
	}

	@Override
	public boolean deleteShow(int showId) throws DaoException {
		boolean result;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement deleteShowStatement = connection
						     .prepareStatement(SET_SHOW_INACTIVE)) {
			try {
				deleteShowStatement.setInt(1, showId);
				deleteShowStatement.execute();
				result = true;
			} catch (SQLException e) {
				throw new DaoException("Show deletion failed.", e);
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while show deletion.", e);
		}
		return result;
	}

	private Map<String, List<Show>> findShowsMapQuery(PreparedStatement findShowStatement,
	                                                  PreparedStatement findFilmStatement,
	                                                  PreparedStatement findCountriesStatement,
	                                                  PreparedStatement findGenresStatement)
					throws DaoException, SQLException {
		ResultSet findFilmResultSet = null;
		Map<String, List<Show>> showsMap = new LinkedHashMap<>();
		try {
			ResultSet findShowResultSet = findShowStatement.executeQuery();
			while (findShowResultSet.next()) {
				Show show = new Show();
				String cinemaName = findShowResultSet.getString(SqlColumnsName.CINEMA_NAME);
				show.setCinemaName(cinemaName);
				show.setId(findShowResultSet.getInt(SqlColumnsName.FILMS_SHOWS_ID));
				show.setDateTime(findShowResultSet.getTimestamp(SqlColumnsName.SHOW_DATE_TIME,
								timezone).toLocalDateTime());
				show.setCost(findShowResultSet.getBigDecimal(SqlColumnsName.TICKET_COST));
				show.setFreePlace(findShowResultSet.getInt(SqlColumnsName.SHOW_FREE_PLACES));
				int filmId = findShowResultSet.getInt(SqlColumnsName.FILM_ID_FK);
				findFilmStatement.setInt(1, filmId);
				findFilmResultSet = findFilmStatement.executeQuery();
				Film film = new Film();
				if (findFilmResultSet.first()) {
					film = findFilmQuery(findFilmResultSet, findCountriesStatement, findGenresStatement);
				}
				show.setFilm(film);
				showsMap.putIfAbsent(cinemaName, new ArrayList<>());
				List<Show> showsList = showsMap.get(cinemaName);
				showsList.add(show);
				showsMap.put(cinemaName, showsList);
			}
		} finally {
			closeResultSet(findFilmResultSet);
		}
		return showsMap;
	}

	@Override
	public Optional<Show> createShow(Show show) throws DaoException {
		Optional<Show> showOptional;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement createShowStatement = connection.prepareStatement(CREATE_SHOW,
						     Statement.RETURN_GENERATED_KEYS)) {
			ResultSet resultSet = null;
			try {
				createShowStatement.setInt(1, show.getFilm().getId());
				createShowStatement.setString(2, show.getCinemaName());
				createShowStatement.setTimestamp(3, Timestamp.valueOf(show.getDateTime()),
								timezone);
				createShowStatement.setBigDecimal(4, show.getCost());
				createShowStatement.setInt(5, show.getFreePlace());
				createShowStatement.execute();
				resultSet = createShowStatement.getGeneratedKeys();
				if (resultSet.next()) {
					show.setId(resultSet.getInt(1));
				}
				showOptional = findShowById(show.getId());
			} catch (SQLException e) {
				throw new DaoException("Show creation failed.", e);
			} finally {
				closeResultSet(resultSet);
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while show creation.", e);
		}
		return showOptional;
	}

	@Override
	public boolean createCinema(String cinemaName) throws DaoException {
		boolean result;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement createStatement = connection
						     .prepareStatement(CREATE_CINEMA)) {
			try {
				createStatement.setString(1, cinemaName);
				createStatement.execute();
				result = true;
			} catch (SQLException e) {
				throw new DaoException("Cinema creation failed.", e);
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while cinema creation.", e);
		}
		return result;
	}

	@Override
	public boolean updateCinema(String cinemaName, int cinemaId) throws DaoException {
		boolean result;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement createStatement = connection
						     .prepareStatement(UPDATE_CINEMA)) {
			try {
				createStatement.setString(1, cinemaName);
				createStatement.setInt(2, cinemaId);
				createStatement.execute();
				result = true;
			} catch (SQLException e) {
				throw new DaoException("Cinema updating failed.", e);
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while cinema updating.", e);
		}
		return result;
	}

	@Override
	public boolean deleteCinema(String cinemaName) throws DaoException {
		boolean result;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement deleteStatement = connection
						     .prepareStatement(SET_CINEMA_INACTIVE)) {
			try {
				deleteStatement.setString(1, cinemaName);
				deleteStatement.execute();
				result = true;
			} catch (SQLException e) {
				throw new DaoException("Cinema deletion failed.", e);
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while cinema deletion.", e);
		}
		return result;
	}

	@Override
	public boolean restoreCinema(String cinemaName) throws DaoException {
		boolean result;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement deleteStatement = connection
						     .prepareStatement(SET_CINEMA_ACTIVE)) {
			try {
				deleteStatement.setString(1, cinemaName);
				deleteStatement.execute();
				result = true;
			} catch (SQLException e) {
				throw new DaoException("Cinema restoration failed.", e);
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while cinema restoration.", e);
		}
		return result;
	}

	@Override
	public boolean checkCinema(String cinemaName) throws DaoException {
		boolean isCinemaNameFree = false;
		ResultSet resultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement cinameNameStatement = connection.prepareStatement(CHECK_CINEMA_NAME)) {
			cinameNameStatement.setString(1, cinemaName);
			resultSet = cinameNameStatement.executeQuery();
			if (!resultSet.first()) {
				isCinemaNameFree = true;
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while checking cinema name.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return isCinemaNameFree;
	}

	@Override
	public boolean checkCinema(String cinemaName, int cinemaId) throws DaoException {
		boolean isCinemaNameFree = false;
		ResultSet resultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement cinemeNameStatement = connection
						     .prepareStatement(CHECK_CINEMA_NAME_BY_ID)) {
			cinemeNameStatement.setString(1, cinemaName);
			cinemeNameStatement.setInt(2, cinemaId);
			resultSet = cinemeNameStatement.executeQuery();
			if (!resultSet.first()) {
				isCinemaNameFree = true;
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while checking cinema name.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return isCinemaNameFree;
	}
}


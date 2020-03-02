package by.tananushka.project.dao.impl;

import by.tananushka.project.bean.Film;
import by.tananushka.project.dao.DaoConstant;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.FilmDao;
import by.tananushka.project.dao.SqlColumnsName;
import by.tananushka.project.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * The type Film dao.
 */
public class FilmDaoImpl implements FilmDao {

	private static final String FIND_ACTIVE_AVAILABLE_FILMS =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM cinemas INNER JOIN (films INNER JOIN films_shows\n"
									+ "ON (film_id = film_id_fk)) ON (cinema_id = cinema_id_fk)\n"
									+ "WHERE film_active = true AND show_active=true AND cinema_active=true\n"
									+ "AND show_date_time >= current_time() ORDER BY film_title;";
	private static final String FIND_ACTIVE_FILMS =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM films WHERE film_active = true ORDER BY film_title;";
	private static final String FIND_AVAILABLE_FILMS_BY_GENRE =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM genres INNER JOIN ((cinemas INNER JOIN (films INNER JOIN films_shows\n"
									+ "ON (film_id = films_shows.film_id_fk)) ON (cinema_id = cinema_id_fk))\n"
									+ "INNER JOIN films_genres ON film_id = films_genres.film_id_fk)\n"
									+ "ON (genre_id = genre_id_fk)\n"
									+ "WHERE film_active = true AND show_active = true AND cinema_active = true\n"
									+ "AND show_date_time >= current_time()\n"
									+ "AND (genre_name = ? AND genre_name LIKE '%')\n"
									+ "ORDER BY film_title;";
	private static final String FIND_ACTIVE_FILMS_BY_GENRE =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM genres INNER JOIN (films INNER JOIN films_genres \n"
									+ "ON film_id = film_id_fk)\n"
									+ "ON genre_id = genre_id_fk\n"
									+ "WHERE film_active=true AND (genre_name = ? AND genre_name LIKE '%')\n"
									+ "ORDER BY film_title;";
	private static final String FIND_AVAILABLE_FILMS_BY_GENRE_ID =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM genres INNER JOIN (films INNER JOIN films_shows\n"
									+ "ON (film_id = films_shows.film_id_fk)\n"
									+ "INNER JOIN films_genres ON film_id = films_genres.film_id_fk)\n"
									+ "ON (genre_id = genre_id_fk)\n"
									+ "WHERE film_active = true AND genre_id = ?\n"
									+ "ORDER BY film_title;";
	private static final String FIND_ACTIVE_FILMS_BY_GENRE_ID =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM genres INNER JOIN (films INNER JOIN films_genres \n"
									+ "ON film_id = film_id_fk)\n"
									+ "ON genre_id = genre_id_fk\n"
									+ "WHERE film_active=true AND genre_id = ?\n"
									+ "ORDER BY film_title;";
	private static final String FIND_AVAILABLE_FILMS_BY_COUNTRY =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM countries INNER JOIN ((cinemas INNER JOIN (films INNER JOIN films_shows\n"
									+ "ON (film_id = films_shows.film_id_fk)) ON (cinema_id = cinema_id_fk))\n"
									+ "INNER JOIN films_countries ON film_id = films_countries.film_id_fk)\n"
									+ "ON (country_id = country_id_fk)\n"
									+ "WHERE film_active = true AND show_active = true AND cinema_active = true\n"
									+ "AND show_date_time >= current_time()\n"
									+ "AND (country_name = ? AND country_name LIKE '%')\n"
									+ "ORDER BY film_title;";
	private static final String FIND_ACTIVE_FILMS_BY_COUNTRY =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM countries INNER JOIN (films INNER JOIN films_countries \n"
									+ "ON film_id = film_id_fk)\n"
									+ "ON country_id = country_id_fk\n"
									+ "WHERE film_active=true AND (country_name = ? AND country_name LIKE '%')\n"
									+ "ORDER BY film_title;";
	private static final String FIND_AVAILABLE_FILMS_BY_COUNTRY_ID =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM countries INNER JOIN ((cinemas INNER JOIN (films INNER JOIN films_shows\n"
									+ "ON (film_id = films_shows.film_id_fk)) ON (cinema_id = cinema_id_fk))\n"
									+ "INNER JOIN films_countries ON film_id = films_countries.film_id_fk)\n"
									+ "ON (country_id = country_id_fk)\n"
									+ "WHERE film_active = true AND show_active = true AND cinema_active = true\n"
									+ "AND show_date_time >= current_time() AND country_id = ?\n"
									+ "ORDER BY film_title;";
	private static final String FIND_ACTIVE_FILMS_BY_COUNTRY_ID =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM countries INNER JOIN (films INNER JOIN films_countries \n"
									+ "ON film_id = film_id_fk)\n"
									+ "ON country_id = country_id_fk\n"
									+ "WHERE film_active=true AND country_id = ?\n"
									+ "ORDER BY film_title;";
	private static final String FIND_AVAILABLE_FILMS_BY_AGE =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM countries INNER JOIN ((cinemas INNER JOIN (films INNER JOIN films_shows\n"
									+ "ON (film_id = films_shows.film_id_fk)) ON (cinema_id = cinema_id_fk))\n"
									+ "INNER JOIN films_countries ON film_id = films_countries.film_id_fk)\n"
									+ "ON (country_id = country_id_fk)\n"
									+ "WHERE film_active = true AND show_active = true AND cinema_active = true\n"
									+ "AND film_age = ? AND show_date_time >= current_time();";
	private static final String FIND_ACTIVE_FILMS_BY_AGE =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM films WHERE film_active = true AND film_age = ?\n"
									+ "ORDER BY film_title;";
	private static final String FIND_AVAILABLE_FILMS_BY_YEAR =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM countries INNER JOIN ((cinemas INNER JOIN (films INNER JOIN films_shows\n"
									+ "ON (film_id = films_shows.film_id_fk)) ON (cinema_id = cinema_id_fk))\n"
									+ "INNER JOIN films_countries ON film_id = films_countries.film_id_fk)\n"
									+ "ON (country_id = country_id_fk)\n"
									+ "WHERE film_active = true AND show_active = true AND cinema_active = true\n"
									+ "AND film_year = ? AND show_date_time >= current_time();";
	private static final String FIND_ACTIVE_FILMS_BY_YEAR =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img, film_active\n"
									+ "FROM films WHERE film_active = true AND film_year = ?\n"
									+ "ORDER BY film_title;";
	private static final String FIND_GENRES =
					"SELECT DISTINCT genre_name FROM genres ORDER BY genre_name;";
	private static final String FIND_GENRES_WITH_ID =
					"SELECT DISTINCT genre_id, genre_name FROM genres ORDER BY genre_name;";
	private static final String FIND_COUNTRIES =
					"SELECT DISTINCT country_name FROM countries ORDER BY country_name;";
	private static final String FIND_COUNTRIES_WITH_ID =
					"SELECT DISTINCT country_id, country_name FROM countries ORDER BY country_name;";
	private static final String FIND_AGES =
					"SELECT DISTINCT film_age FROM films ORDER BY film_age;";
	private static final String FIND_YEARS =
					"SELECT DISTINCT film_year FROM films ORDER BY film_year;";
	private static final String FIND_ACTIVE_TITLES =
					"SELECT DISTINCT film_id, film_title FROM films\n"
									+ "WHERE film_active = true ORDER BY film_title;";
	private static final String DELETE_FILMS_GENRES =
					"DELETE FROM films_genres WHERE film_id_fk = ?;";
	private static final String INSERT_FILMS_GENRES =
					"INSERT INTO films_genres (film_id_fk, genre_id_fk)\n"
									+ "VALUES (?, (SELECT genre_id FROM genres WHERE genre_name = ?));";
	private static final String DELETE_FILMS_COUNTRIES =
					"DELETE FROM films_countries WHERE film_id_fk = ?;";
	private static final String INSERT_FILMS_COUNTRIES =
					"INSERT INTO films_countries (film_id_fk, country_id_fk)\n"
									+ "VALUES (?, (SELECT country_id FROM countries WHERE country_name = ?));";
	private static final String UPDATE_FILM =
					"UPDATE films\n"
									+ "SET film_title = ?, film_age = ?, film_year = ?, film_img = ?\n"
									+ "WHERE film_id = ?;";
	private static final String CREATE_FILM =
					"INSERT INTO films (film_title, film_age, film_year)\n"
									+ "VALUES (?, ?, ?);";
	private static final String SET_FILM_INACTIVE =
					"UPDATE films\n"
									+ "SET film_active = false WHERE film_id = ?;";
	private static final String SET_SHOW_INACTIVE_BY_FILM =
					"UPDATE films_shows\n"
									+ "SET show_active = false\n"
									+ "WHERE film_id_fk = ?";
	private static final String CHECK_TITLE_BY_ID = "SELECT film_title FROM films "
					+ "WHERE film_title = ? AND film_id <> ?";
	private static final String CHECK_TITLE = "SELECT film_title FROM films "
					+ "WHERE film_title = ?";
	private static final String UPDATE_IMAGE =
					"UPDATE films SET film_img = ? WHERE film_id = ?;";
	private static final String CREATE_GENRE =
					"INSERT INTO genres (genre_name) VALUE (?);";
	private static final String UPDATE_GENRE =
					"UPDATE genres SET genre_name = ? WHERE genre_id = ?;";
	private static final String CHECK_GENRE_NAME = "SELECT genre_name FROM genres "
					+ "WHERE genre_name = ?";
	private static final String CHECK_GENRE_NAME_BY_ID = "SELECT genre_name FROM genres "
					+ "WHERE genre_name = ? AND genre_id <> ?";
	private static FilmDao filmDao = new FilmDaoImpl();
	private static Logger log = LogManager.getLogger();

	private FilmDaoImpl() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static FilmDao getInstance() {
		return filmDao;
	}

	@Override
	public Optional<Film> findFilmById(int filmId) throws DaoException {
		Optional<Film> filmOptional = Optional.empty();
		ResultSet findFilmResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(
						     DaoConstant.FIND_FILM_BY_ID);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement =
						     connection.prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findFilmStatement.setInt(1, filmId);
			findFilmResultSet = findFilmStatement.executeQuery();
			if (findFilmResultSet.next()) {
				Film film = findFilmQuery(findFilmResultSet, findCountriesStatement, findGenresStatement);
				filmOptional = Optional.of(film);
			}
		} catch (SQLException e) {
			log.error("SQLException while getting available films.");
			throw new DaoException("SQLException while getting available films.", e);
		} finally {
			closeResultSet(findFilmResultSet);
		}
		return filmOptional;
	}

	@Override
	public List<Film> findAvailableFilms() throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(
						     FIND_ACTIVE_AVAILABLE_FILMS);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement =
						     connection.prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting available films.");
			throw new DaoException("SQLException while getting available films.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findActiveFilms() throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(
						     FIND_ACTIVE_FILMS);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement =
						     connection.prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting active films.");
			throw new DaoException("SQLException while getting active films.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByGenre(String genre) throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(
						     FIND_AVAILABLE_FILMS_BY_GENRE);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement =
						     connection.prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findFilmStatement.setString(1, genre);
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting films by genre.");
			throw new DaoException("SQLException while getting films by genre.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findActiveFilmsByGenre(String genre) throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(
						     FIND_ACTIVE_FILMS_BY_GENRE);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection
						     .prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findFilmStatement.setString(1, genre);
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting films by genre.");
			throw new DaoException("SQLException while getting films by genre.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByGenre(int genre) throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(
						     FIND_AVAILABLE_FILMS_BY_GENRE_ID);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection
						     .prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findFilmStatement.setInt(1, genre);
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting films by genre.");
			throw new DaoException("SQLException while getting films by genre.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findActiveFilmsByGenre(int genre) throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(
						     FIND_ACTIVE_FILMS_BY_GENRE_ID);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection
						     .prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findFilmStatement.setInt(1, genre);
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting films by genre.");
			throw new DaoException("SQLException while getting films by genre.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByCountry(String country) throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(
						     FIND_AVAILABLE_FILMS_BY_COUNTRY);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection
						     .prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findFilmStatement.setString(1, country);
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting films by country.");
			throw new DaoException("SQLException while getting films by country.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findActiveFilmsByCountry(String country) throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(
						     FIND_ACTIVE_FILMS_BY_COUNTRY);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection
						     .prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findFilmStatement.setString(1, country);
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting films by country.");
			throw new DaoException("SQLException while getting films by country.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByCountry(int country) throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement =
						     connection.prepareStatement(FIND_AVAILABLE_FILMS_BY_COUNTRY_ID);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection
						     .prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findFilmStatement.setInt(1, country);
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting films by country.");
			throw new DaoException("SQLException while getting films by country.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findActiveFilmsByCountry(int country) throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement =
						     connection.prepareStatement(FIND_ACTIVE_FILMS_BY_COUNTRY_ID);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection
						     .prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findFilmStatement.setInt(1, country);
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting films by country.");
			throw new DaoException("SQLException while getting films by country.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByAge(int age) throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(
						     FIND_AVAILABLE_FILMS_BY_AGE);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection
						     .prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findFilmStatement.setInt(1, age);
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting films by age.");
			throw new DaoException("SQLException while getting films by age.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findActiveFilmsByAge(int age) throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(
						     FIND_ACTIVE_FILMS_BY_AGE);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection
						     .prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findFilmStatement.setInt(1, age);
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting films by age.");
			throw new DaoException("SQLException while getting films by age.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByYear(int year) throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(
						     FIND_AVAILABLE_FILMS_BY_YEAR);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection
						     .prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findFilmStatement.setInt(1, year);
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting films by year.");
			throw new DaoException("SQLException while getting films by year.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findActiveFilmsByYear(int year) throws DaoException {
		List<Film> filmsList;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(
						     FIND_ACTIVE_FILMS_BY_YEAR);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(DaoConstant.FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection
						     .prepareStatement(DaoConstant.FIND_GENRES_BY_FILM)) {
			findFilmStatement.setInt(1, year);
			filmsList =
							findFilmsListQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			log.error("SQLException while getting films by year.");
			throw new DaoException("SQLException while getting films by year.", e);
		}
		return filmsList;
	}

	@Override
	public List<String> findGenres() throws DaoException {
		List<String> genresList = new ArrayList<>();
		ResultSet findGenresResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findGenresStatement = connection.prepareStatement(FIND_GENRES)) {
			findGenresResultSet = findGenresStatement.executeQuery();
			while (findGenresResultSet.next()) {
				String genre = findGenresResultSet.getString(SqlColumnsName.GENRE_NAME);
				genresList.add(genre);
			}
		} catch (SQLException e) {
			log.error("SQLException while getting films genres.");
			throw new DaoException("SQLException while getting films genres.", e);
		} finally {
			closeResultSet(findGenresResultSet);
		}
		return genresList;
	}

	@Override
	public Map<Integer, String> findGenresMap() throws DaoException {
		Map<Integer, String> genresMap = new LinkedHashMap<>();
		ResultSet findGenresResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findGenresStatement = connection.prepareStatement(FIND_GENRES_WITH_ID)) {
			findGenresResultSet = findGenresStatement.executeQuery();
			while (findGenresResultSet.next()) {
				int genreId = findGenresResultSet.getInt(SqlColumnsName.GENRE_ID);
				String genre = findGenresResultSet.getString(SqlColumnsName.GENRE_NAME);
				genresMap.put(genreId, genre);
			}
		} catch (SQLException e) {
			log.error("SQLException while getting films genres.");
			throw new DaoException("SQLException while getting films genres.", e);
		} finally {
			closeResultSet(findGenresResultSet);
		}
		return genresMap;
	}

	@Override
	public List<String> findCountries() throws DaoException {
		List<String> countriesList = new ArrayList<>();
		ResultSet findCountriesResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findCountriesStatement = connection.prepareStatement(FIND_COUNTRIES)) {
			findCountriesResultSet = findCountriesStatement.executeQuery();
			while (findCountriesResultSet.next()) {
				String country = findCountriesResultSet.getString(SqlColumnsName.COUNTRY_NAME);
				countriesList.add(country);
			}
		} catch (SQLException e) {
			log.error("SQLException while getting films countries.");
			throw new DaoException("SQLException while getting films countries.", e);
		} finally {
			closeResultSet(findCountriesResultSet);
		}
		return countriesList;
	}

	@Override
	public Map<Integer, String> findCountriesMap() throws DaoException {
		Map<Integer, String> countriesMap = new LinkedHashMap<>();
		ResultSet findCountriesResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(FIND_COUNTRIES_WITH_ID)) {
			findCountriesResultSet = findCountriesStatement.executeQuery();
			while (findCountriesResultSet.next()) {
				int countryId = findCountriesResultSet.getInt(SqlColumnsName.COUNTRY_ID);
				String country = findCountriesResultSet.getString(SqlColumnsName.COUNTRY_NAME);
				countriesMap.put(countryId, country);
			}
		} catch (SQLException e) {
			log.error("SQLException while getting films countries.");
			throw new DaoException("SQLException while getting films countries.", e);
		} finally {
			closeResultSet(findCountriesResultSet);
		}
		return countriesMap;
	}

	@Override
	public List<Integer> findAges() throws DaoException {
		List<Integer> agesList = new ArrayList<>();
		ResultSet findAgesResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findCountriesStatement = connection.prepareStatement(FIND_AGES)) {
			findAgesResultSet = findCountriesStatement.executeQuery();
			while (findAgesResultSet.next()) {
				int age = findAgesResultSet.getInt(SqlColumnsName.FILM_AGE);
				agesList.add(age);
			}
		} catch (SQLException e) {
			log.error("SQLException while getting film ages.");
			throw new DaoException("SQLException while getting film ages.", e);
		} finally {
			closeResultSet(findAgesResultSet);
		}
		return agesList;
	}

	@Override
	public Map<Integer, Integer> findAgesMap() throws DaoException {
		Map<Integer, Integer> agesMap = new LinkedHashMap<>();
		ResultSet findAgesResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findCountriesStatement = connection.prepareStatement(FIND_AGES)) {
			findAgesResultSet = findCountriesStatement.executeQuery();
			while (findAgesResultSet.next()) {
				int age = findAgesResultSet.getInt(SqlColumnsName.FILM_AGE);
				agesMap.put(age, age);
			}
		} catch (SQLException e) {
			log.error("SQLException while getting film ages.");
			throw new DaoException("SQLException while getting film ages.", e);
		} finally {
			closeResultSet(findAgesResultSet);
		}
		return agesMap;
	}

	@Override
	public List<Integer> findYears() throws DaoException {
		List<Integer> yearsList = new ArrayList<>();
		ResultSet findYearsResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findCountriesStatement = connection.prepareStatement(FIND_YEARS)) {
			findYearsResultSet = findCountriesStatement.executeQuery();
			while (findYearsResultSet.next()) {
				int year = findYearsResultSet.getInt(SqlColumnsName.FILM_YEAR);
				yearsList.add(year);
			}
		} catch (SQLException e) {
			log.error("SQLException while getting films years.");
			throw new DaoException("SQLException while getting films years.", e);
		} finally {
			closeResultSet(findYearsResultSet);
		}
		return yearsList;
	}

	@Override
	public Map<Integer, Integer> findYearsMap() throws DaoException {
		Map<Integer, Integer> yearsMap = new LinkedHashMap<>();
		ResultSet findYearsResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findCountriesStatement = connection.prepareStatement(FIND_YEARS)) {
			findYearsResultSet = findCountriesStatement.executeQuery();
			while (findYearsResultSet.next()) {
				int year = findYearsResultSet.getInt(SqlColumnsName.FILM_YEAR);
				yearsMap.put(year, year);
			}
		} catch (SQLException e) {
			log.error("SQLException while getting films years.");
			throw new DaoException("SQLException while getting films years.", e);
		} finally {
			closeResultSet(findYearsResultSet);
		}
		return yearsMap;
	}

	@Override
	public Map<Integer, String> findTitlesMap() throws DaoException {
		Map<Integer, String> titlesMap = new LinkedHashMap<>();
		ResultSet findTitlesResultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findTitlesStatement = connection
						     .prepareStatement(FIND_ACTIVE_TITLES)) {
			findTitlesResultSet = findTitlesStatement.executeQuery();
			while (findTitlesResultSet.next()) {
				int filmId = findTitlesResultSet.getInt(SqlColumnsName.FILM_ID);
				String title = findTitlesResultSet.getString(SqlColumnsName.FILM_TITLE);
				titlesMap.put(filmId, title);
			}
		} catch (SQLException e) {
			log.error("SQLException while getting films titles.");
			throw new DaoException("SQLException while getting films titles.", e);
		} finally {
			closeResultSet(findTitlesResultSet);
		}
		return titlesMap;
	}

	@Override
	public Optional<Film> updateFilm(Film film) throws DaoException {
		Optional<Film> filmOptional;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement updateFilmStatement = connection
						     .prepareStatement(UPDATE_FILM)) {
			try {
				connection.setAutoCommit(false);
				updateFilmsGenres(film);
				updateFilmsCountries(film);
				updateFilmStatement.setString(1, film.getTitle());
				updateFilmStatement.setInt(2, film.getAge());
				updateFilmStatement.setInt(3, film.getYear());
				updateFilmStatement.setString(4, film.getImg());
				updateFilmStatement.setInt(5, film.getId());
				updateFilmStatement.execute();
				connection.commit();
				filmOptional = findFilmById(film.getId());
			} catch (SQLException e) {
				connection.rollback();
				log.error("Transaction while updating film failed; not committed.");
				throw new DaoException("Transaction while updating film failed; not committed.", e);
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			log.error("SQL exception while updating film.");
			throw new DaoException("SQL exception while updating film.", e);
		}
		return filmOptional;
	}

	@Override
	public boolean deleteFilm(int filmId) throws DaoException {
		boolean result;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement deleteFilmStatement = connection
						     .prepareStatement(SET_FILM_INACTIVE);
		     PreparedStatement deleteShowStatement = connection
						     .prepareStatement(SET_SHOW_INACTIVE_BY_FILM)) {
			try {
				connection.setAutoCommit(false);
				deleteFilmStatement.setInt(1, filmId);
				deleteFilmStatement.execute();
				deleteShowStatement.setInt(1, filmId);
				deleteShowStatement.execute();
				connection.commit();
				result = true;
			} catch (SQLException e) {
				connection.rollback();
				log.error("Transaction while film deletion failed; not committed.");
				throw new DaoException("Transaction while film deletion failed; not committed.", e);
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			log.error("SQL exception while film deletion.");
			throw new DaoException("SQL exception while film deletion.", e);
		}
		return result;
	}

	private void updateFilmsGenres(Film film) throws SQLException {
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement deleteGenresStatement = connection
						     .prepareStatement(DELETE_FILMS_GENRES);
		     PreparedStatement insertGenresStatement = connection
						     .prepareStatement(INSERT_FILMS_GENRES)) {
			try {
				connection.setAutoCommit(false);
				deleteGenresStatement.setInt(1, film.getId());
				deleteGenresStatement.execute();
				insertGenresStatement.setInt(1, film.getId());
				Set<String> genres = film.getGenres();
				for (String genre : genres) {
					insertGenresStatement.setString(2, genre);
					insertGenresStatement.execute();
				}
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				log.error("Transaction while updating films_genres table failed; not committed.");
				throw new SQLException(
								"Transaction while updating films_genres table failed; not committed.", e);
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			log.error("SQL exception while updating films_genres table.");
			throw new SQLException("SQL exception while updating films_genres table.", e);
		}
	}

	private void updateFilmsCountries(Film film) throws SQLException {
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement deleteCountriesStatement = connection
						     .prepareStatement(DELETE_FILMS_COUNTRIES);
		     PreparedStatement insertCountriesStatement = connection
						     .prepareStatement(INSERT_FILMS_COUNTRIES)) {
			try {
				connection.setAutoCommit(false);
				deleteCountriesStatement.setInt(1, film.getId());
				deleteCountriesStatement.execute();
				insertCountriesStatement.setInt(1, film.getId());
				Set<String> countries = film.getCountries();
				for (String country : countries) {
					insertCountriesStatement.setString(2, country);
					insertCountriesStatement.execute();
				}
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				log.error("Transaction while updating films_countries table failed; not committed.");
				throw new SQLException(
								"Transaction while updating films_countries table failed; not committed.", e);
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			log.error("SQL exception while updating films_countries table.");
			throw new SQLException("SQL exception while updating films_countries table.", e);
		}
	}

	@Override
	public boolean checkTitle(String title, int filmId) throws DaoException {
		boolean isTitleFree = false;
		ResultSet resultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement titleStatement = connection.prepareStatement(CHECK_TITLE_BY_ID)) {
			titleStatement.setString(1, title);
			titleStatement.setInt(2, filmId);
			resultSet = titleStatement.executeQuery();
			if (!resultSet.first()) {
				isTitleFree = true;
			}
		} catch (SQLException e) {
			log.error("SQL exception while checking title.");
			throw new DaoException("SQL exception while checking title.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return isTitleFree;
	}

	@Override
	public boolean checkTitle(String title) throws DaoException {
		boolean isTitleFree = false;
		ResultSet resultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement titleStatement = connection.prepareStatement(CHECK_TITLE)) {
			titleStatement.setString(1, title);
			resultSet = titleStatement.executeQuery();
			if (!resultSet.first()) {
				isTitleFree = true;
			}
		} catch (SQLException e) {
			log.error("SQL exception while checking title.");
			throw new DaoException("SQL exception while checking title.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return isTitleFree;
	}

	@Override
	public Optional<Film> createFilm(Film film) throws DaoException {
		Optional<Film> filmOptional;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement createFilmStatement = connection
						     .prepareStatement(CREATE_FILM, Statement.RETURN_GENERATED_KEYS)) {
			ResultSet resultSet = null;
			try {
				createFilmStatement.setString(1, film.getTitle());
				createFilmStatement.setInt(2, film.getAge());
				createFilmStatement.setInt(3, film.getYear());
				createFilmStatement.execute();
				resultSet = createFilmStatement.getGeneratedKeys();
				if (resultSet.next()) {
					film.setId(resultSet.getInt(1));
				}
				createFilmsGenres(film);
				createFilmsCountries(film);
				filmOptional = findFilmById(film.getId());
			} catch (SQLException e) {
				log.error("Film creation failed.");
				throw new DaoException("Film creation failed.", e);
			} finally {
				closeResultSet(resultSet);
			}
		} catch (SQLException e) {
			log.error("SQL exception while creation film.");
			throw new DaoException("SQL exception while creation film.", e);
		}
		return filmOptional;
	}

	private void createFilmsGenres(Film film) throws SQLException {
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement insertGenresStatement = connection
						     .prepareStatement(INSERT_FILMS_GENRES)) {
			try {
				connection.setAutoCommit(false);
				insertGenresStatement.setInt(1, film.getId());
				Set<String> genres = film.getGenres();
				for (String genre : genres) {
					insertGenresStatement.setString(2, genre);
					insertGenresStatement.execute();
				}
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				log.error("Transaction while updating films_genres table failed; not committed.");
				throw new SQLException(
								"Transaction while updating films_genres table failed; not committed.", e);
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			log.error("SQL exception while updating films_genres table.");
			throw new SQLException("SQL exception while updating films_genres table.", e);
		}
	}

	private void createFilmsCountries(Film film) throws SQLException {
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement insertCountriesStatement = connection
						     .prepareStatement(INSERT_FILMS_COUNTRIES)) {
			try {
				connection.setAutoCommit(false);
				insertCountriesStatement.setInt(1, film.getId());
				Set<String> countries = film.getCountries();
				for (String country : countries) {
					insertCountriesStatement.setString(2, country);
					insertCountriesStatement.execute();
				}
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				log.error("Transaction while updating films_countries table failed; not committed.");
				throw new SQLException(
								"Transaction while updating films_countries table failed; not committed.", e);
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			log.error("SQL exception while updating films_countries table.");
			throw new SQLException("SQL exception while updating films_countries table.", e);
		}
	}

	@Override
	public Optional<Film> updateImage(int filmId, String img) throws DaoException {
		Optional<Film> filmOptional;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement updateImageStatement = connection
						     .prepareStatement(UPDATE_IMAGE)) {
			try {
				updateImageStatement.setString(1, img);
				updateImageStatement.setInt(2, filmId);
				updateImageStatement.execute();
				filmOptional = findFilmById(filmId);
			} catch (SQLException e) {
				log.error("Image updating failed.");
				throw new DaoException("Image updating failed.", e);
			}
		} catch (SQLException e) {
			log.error("SQL exception while image updating.");
			throw new DaoException("SQL exception while image updating.", e);
		}
		return filmOptional;
	}

	@Override
	public boolean createGenre(String genreName) throws DaoException {
		boolean result;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement createStatement = connection
						     .prepareStatement(CREATE_GENRE)) {
			try {
				createStatement.setString(1, genreName);
				createStatement.execute();
				result = true;
			} catch (SQLException e) {
				log.error("Genre creation failed.");
				throw new DaoException("Genre creation failed.", e);
			}
		} catch (SQLException e) {
			log.error("SQL exception while genre creation.");
			throw new DaoException("SQL exception while genre creation.", e);
		}
		return result;
	}

	@Override
	public boolean updateGenre(String genreName, int genreId) throws DaoException {
		boolean result;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement createStatement = connection
						     .prepareStatement(UPDATE_GENRE)) {
			try {
				createStatement.setString(1, genreName);
				createStatement.setInt(2, genreId);
				createStatement.execute();
				result = true;
			} catch (SQLException e) {
				log.error("Genre updating failed.");
				throw new DaoException("Genre updating failed.", e);
			}
		} catch (SQLException e) {
			log.error("SQL exception while genre updating.");
			throw new DaoException("SQL exception while genre updating.", e);
		}
		return result;
	}

	@Override
	public boolean checkGenre(String genreName) throws DaoException {
		boolean isGenreNameFree = false;
		ResultSet resultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement genreNameStatement = connection.prepareStatement(CHECK_GENRE_NAME)) {
			genreNameStatement.setString(1, genreName);
			resultSet = genreNameStatement.executeQuery();
			if (!resultSet.first()) {
				isGenreNameFree = true;
			}
		} catch (SQLException e) {
			log.error("SQL exception while checking genre name.");
			throw new DaoException("SQL exception while checking genre name.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return isGenreNameFree;
	}

	@Override
	public boolean checkGenre(String genreName, int genreId) throws DaoException {
		boolean isGenreNameFree = false;
		ResultSet resultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement genreNameStatement =
						     connection.prepareStatement(CHECK_GENRE_NAME_BY_ID)) {
			genreNameStatement.setString(1, genreName);
			genreNameStatement.setInt(2, genreId);
			resultSet = genreNameStatement.executeQuery();
			if (!resultSet.first()) {
				isGenreNameFree = true;
			}
		} catch (SQLException e) {
			log.error("SQL exception while checking genre name.");
			throw new DaoException("SQL exception while checking genre name.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return isGenreNameFree;
	}
}

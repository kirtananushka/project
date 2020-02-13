package by.tananushka.project.dao.impl;

import by.tananushka.project.bean.Film;
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
import java.util.ArrayList;
import java.util.List;

public class FilmDaoImpl implements FilmDao {

	private static final String FIND_FILMS_AVAILABLE =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img\n"
									+ "FROM films INNER JOIN films_shows ON film_id = film_id_fk\n"
									+ "WHERE show_date_time>=Now()\n ORDER BY film_title;";
	private static final String FIND_FILMS_BY_GENRE =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img\n"
									+ "FROM genres INNER JOIN ((films INNER JOIN films_shows ON film_id = film_id_fk)\n"
									+ "INNER JOIN films_genres ON film_id = films_genres.film_id_fk)\n"
									+ "ON genre_name = genre_name_fk\n"
									+ "WHERE show_date_time>=Now() AND (genre_name=? AND genre_name LIKE '%')\n"
									+ "ORDER BY film_title;";
	private static final String FIND_FILMS_BY_COUNTRY =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img\n"
									+ "FROM countries INNER JOIN ((films INNER JOIN films_shows ON film_id = film_id_fk)\n"
									+ "INNER JOIN films_countries ON film_id = films_countries.film_id_fk)\n"
									+ "ON country_name = country_name_fk\n"
									+ "WHERE show_date_time>=Now() AND (country_name=? AND country_name LIKE '%')\n"
									+ "ORDER BY film_title;";
	private static final String FIND_FILMS_BY_AGE =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img\n"
									+ "FROM films INNER JOIN films_shows ON film_id = film_id_fk\n"
									+ "WHERE film_age=? AND show_date_time>=Now();";
	private static final String FIND_FILMS_BY_YEAR =
					"SELECT DISTINCT film_id, film_title, film_age, film_year, film_img\n"
									+ "FROM films INNER JOIN films_shows ON film_id = film_id_fk\n"
									+ "WHERE film_year=? AND show_date_time>=Now();";
	private static final String FIND_COUNTRIES_BY_FILM =
					"SELECT country_name_fk FROM films_countries WHERE film_id_fk=?;";
	private static final String FIND_GENRES_BY_FILM =
					"SELECT genre_name_fk FROM films_genres WHERE film_id_fk=?;";
	private static final String FIND_GENRES =
					"SELECT DISTINCT genre_name FROM genres ORDER BY genre_name;";
	private static final String FIND_COUNTRIES =
					"SELECT DISTINCT country_name FROM countries ORDER BY country_name;";
	private static final String FIND_AGES =
					"SELECT DISTINCT film_age FROM films ORDER BY film_age;";
	private static final String FIND_YEARS =
					"SELECT DISTINCT film_year FROM films ORDER BY film_year;";
	private static Logger log = LogManager.getLogger();
	private static FilmDao filmDao = new FilmDaoImpl();

	private FilmDaoImpl() {
	}

	public static FilmDao getInstance() {
		return filmDao;
	}

	@Override
	public List<Film> findAvailableFilms() throws DaoException {
		List<Film> filmsList = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(FIND_FILMS_AVAILABLE);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection.prepareStatement(FIND_GENRES_BY_FILM)) {
			filmsList = findFilmQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting available films", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByGenre(String genre) throws DaoException {
		List<Film> filmsList = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(FIND_FILMS_BY_GENRE);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection.prepareStatement(FIND_GENRES_BY_FILM)) {
			findFilmStatement.setString(1, genre);
			filmsList = findFilmQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting films by genre", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByCountry(String country) throws DaoException {
		List<Film> filmsList = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(FIND_FILMS_BY_COUNTRY);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection.prepareStatement(FIND_GENRES_BY_FILM)) {
			findFilmStatement.setString(1, country);
			filmsList = findFilmQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting films by country", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByAge(int age) throws DaoException {
		List<Film> filmsList = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(FIND_FILMS_BY_AGE);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection.prepareStatement(FIND_GENRES_BY_FILM)) {
			findFilmStatement.setInt(1, age);
			filmsList = findFilmQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting films by age", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByYear(int year) throws DaoException {
		List<Film> filmsList = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement findFilmStatement = connection.prepareStatement(FIND_FILMS_BY_YEAR);
		     PreparedStatement findCountriesStatement = connection
						     .prepareStatement(FIND_COUNTRIES_BY_FILM);
		     PreparedStatement findGenresStatement = connection.prepareStatement(FIND_GENRES_BY_FILM)) {
			findFilmStatement.setInt(1, year);
			filmsList = findFilmQuery(findFilmStatement, findCountriesStatement, findGenresStatement);
		} catch (SQLException e) {
			throw new DaoException("SQLException while getting films by year", e);
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
			throw new DaoException("SQLException while getting films genres", e);
		} finally {
			closeResultSet(findGenresResultSet);
		}
		return genresList;
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
			throw new DaoException("SQLException while getting films countries", e);
		} finally {
			closeResultSet(findCountriesResultSet);
		}
		return countriesList;
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
			throw new DaoException("SQLException while getting film ages", e);
		} finally {
			closeResultSet(findAgesResultSet);
		}
		return agesList;
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
			throw new DaoException("SQLException while getting films years", e);
		} finally {
			closeResultSet(findYearsResultSet);
		}
		return yearsList;
	}

	private List<Film> findFilmQuery(PreparedStatement findFilmStatement,
	                                 PreparedStatement findCountriesStatement,
	                                 PreparedStatement findGenresStatement)
					throws SQLException, DaoException {
		List<Film> filmsList = new ArrayList<>();
		ResultSet findCountriesResultSet = null;
		ResultSet findGenresResultSet = null;
		ResultSet findFilmResultSet = null;
		try {
			findFilmResultSet = findFilmStatement.executeQuery();
			while (findFilmResultSet.next()) {
				Film film = new Film();
				film.setId(findFilmResultSet.getInt(SqlColumnsName.FILM_ID));
				film.setTitle(findFilmResultSet.getString(SqlColumnsName.FILM_TITLE));
				film.setAge(findFilmResultSet.getInt(SqlColumnsName.FILM_AGE));
				film.setYear(findFilmResultSet.getInt(SqlColumnsName.FILM_YEAR));
				film.setImg(findFilmResultSet.getString(SqlColumnsName.FILM_IMG));
				findCountriesStatement.setInt(1, film.getId());
				findCountriesResultSet = findCountriesStatement.executeQuery();
				while (findCountriesResultSet.next()) {
					film.addCountry(findCountriesResultSet.getString(SqlColumnsName.COUNTRY_NAME_FK));
				}
				findGenresStatement.setInt(1, film.getId());
				findGenresResultSet = findGenresStatement.executeQuery();
				while (findGenresResultSet.next()) {
					film.addGenre(findGenresResultSet.getString(SqlColumnsName.GENRE_NAME_FK));
				}
				filmsList.add(film);
			}
		} finally {
			closeResultSet(findGenresResultSet);
			closeResultSet(findCountriesResultSet);
			closeResultSet(findFilmResultSet);
		}
		return filmsList;
	}
}

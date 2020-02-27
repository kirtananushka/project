package by.tananushka.project.dao;

import by.tananushka.project.bean.Film;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface AbstractDao {

	String FIND_FILM_BY_ID =
					"SELECT film_id, film_title, film_age, film_year, film_img, film_active"
									+ " FROM films WHERE film_id = ?;";
	String FIND_COUNTRIES_BY_FILM =
					"SELECT country_name FROM countries INNER JOIN films_countries "
									+ "ON country_id = country_id_fk WHERE film_id_fk=?;";
	String FIND_GENRES_BY_FILM =
					"SELECT genre_name FROM genres INNER JOIN films_genres "
									+ "ON genre_id = genre_id_fk WHERE film_id_fk=?;";

	default void closeResultSet(ResultSet resultSet) throws DaoException {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new DaoException("Result set was not close.", e);
			}
		}
	}

	default List<Film> findFilmsListQuery(PreparedStatement findFilmStatement,
	                                      PreparedStatement findCountriesStatement,
	                                      PreparedStatement findGenresStatement)
					throws SQLException, DaoException {
		List<Film> filmsList = new ArrayList<>();
		ResultSet findFilmResultSet = null;
		try {
			findFilmResultSet = findFilmStatement.executeQuery();
			while (findFilmResultSet.next()) {
				Film film = findFilmQuery(findFilmResultSet, findCountriesStatement,
								findGenresStatement);
				filmsList.add(film);
			}
		} finally {
			closeResultSet(findFilmResultSet);
		}
		return filmsList;
	}

	default Film findFilmQuery(ResultSet findFilmResultSet,
	                           PreparedStatement findCountriesStatement,
	                           PreparedStatement findGenresStatement)
					throws SQLException, DaoException {
		ResultSet findCountriesResultSet = null;
		ResultSet findGenresResultSet = null;
		Film film;
		try {
			film = new Film();
			film.setId(findFilmResultSet.getInt(SqlColumnsName.FILM_ID));
			film.setTitle(findFilmResultSet.getString(SqlColumnsName.FILM_TITLE));
			film.setAge(findFilmResultSet.getInt(SqlColumnsName.FILM_AGE));
			film.setYear(findFilmResultSet.getInt(SqlColumnsName.FILM_YEAR));
			film.setImg(findFilmResultSet.getString(SqlColumnsName.FILM_IMG));
			film.setActive(findFilmResultSet.getBoolean(SqlColumnsName.FILM_ACTIVE));
			findCountriesStatement.setInt(1, film.getId());
			findCountriesResultSet = findCountriesStatement.executeQuery();
			while (findCountriesResultSet.next()) {
				film.addCountry(findCountriesResultSet.getString(SqlColumnsName.COUNTRY_NAME));
			}
			findGenresStatement.setInt(1, film.getId());
			findGenresResultSet = findGenresStatement.executeQuery();
			while (findGenresResultSet.next()) {
				film.addGenre(findGenresResultSet.getString(SqlColumnsName.GENRE_NAME));
			}
		} finally {
			closeResultSet(findGenresResultSet);
			closeResultSet(findCountriesResultSet);
		}
		return film;
	}
}


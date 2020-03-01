package by.tananushka.project.dao;

/**
 * The type Dao constant.
 */
public class DaoConstant {

	/**
	 * The constant FIND_FILM_BY_ID.
	 */
	public static final String FIND_FILM_BY_ID =
					"SELECT film_id, film_title, film_age, film_year, film_img, film_active"
									+ " FROM films WHERE film_id = ?;";
	/**
	 * The constant FIND_COUNTRIES_BY_FILM.
	 */
	public static final String FIND_COUNTRIES_BY_FILM =
					"SELECT country_name FROM countries INNER JOIN films_countries "
									+ "ON country_id = country_id_fk WHERE film_id_fk=?;";
	/**
	 * The constant FIND_GENRES_BY_FILM.
	 */
	public static final String FIND_GENRES_BY_FILM =
					"SELECT genre_name FROM genres INNER JOIN films_genres "
									+ "ON genre_id = genre_id_fk WHERE film_id_fk=?;";

	private DaoConstant() {
	}
}

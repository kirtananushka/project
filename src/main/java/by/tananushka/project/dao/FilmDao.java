package by.tananushka.project.dao;

import by.tananushka.project.bean.Film;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Film dao.
 */
public interface FilmDao extends AbstractDao {

	/**
	 * Find film by id optional.
	 *
	 * @param filmId the film id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Film> findFilmById(int filmId) throws DaoException;

	/**
	 * Find available films list.
	 *
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findAvailableFilms() throws DaoException;

	/**
	 * Find active films list.
	 *
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findActiveFilms() throws DaoException;

	/**
	 * Find films by genre list.
	 *
	 * @param genre the genre
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findFilmsByGenre(String genre) throws DaoException;

	/**
	 * Find active films by genre list.
	 *
	 * @param genre the genre
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findActiveFilmsByGenre(String genre) throws DaoException;

	/**
	 * Find films by genre list.
	 *
	 * @param genre the genre
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findFilmsByGenre(int genre) throws DaoException;

	/**
	 * Find active films by genre list.
	 *
	 * @param genre the genre
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findActiveFilmsByGenre(int genre) throws DaoException;

	/**
	 * Find films by country list.
	 *
	 * @param country the country
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findFilmsByCountry(String country) throws DaoException;

	/**
	 * Find active films by country list.
	 *
	 * @param country the country
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findActiveFilmsByCountry(String country) throws DaoException;

	/**
	 * Find films by country list.
	 *
	 * @param country the country
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findFilmsByCountry(int country) throws DaoException;

	/**
	 * Find active films by country list.
	 *
	 * @param country the country
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findActiveFilmsByCountry(int country) throws DaoException;

	/**
	 * Find films by age list.
	 *
	 * @param age the age
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findFilmsByAge(int age) throws DaoException;

	/**
	 * Find active films by age list.
	 *
	 * @param age the age
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findActiveFilmsByAge(int age) throws DaoException;

	/**
	 * Find films by year list.
	 *
	 * @param year the year
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findFilmsByYear(int year) throws DaoException;

	/**
	 * Find active films by year list.
	 *
	 * @param year the year
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Film> findActiveFilmsByYear(int year) throws DaoException;

	/**
	 * Find genres list.
	 *
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<String> findGenres() throws DaoException;

	/**
	 * Find genres map map.
	 *
	 * @return the map
	 * @throws DaoException the dao exception
	 */
	Map<Integer, String> findGenresMap() throws DaoException;

	/**
	 * Find countries list.
	 *
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<String> findCountries() throws DaoException;

	/**
	 * Find countries map map.
	 *
	 * @return the map
	 * @throws DaoException the dao exception
	 */
	Map<Integer, String> findCountriesMap() throws DaoException;

	/**
	 * Find ages list.
	 *
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Integer> findAges() throws DaoException;

	/**
	 * Find ages map map.
	 *
	 * @return the map
	 * @throws DaoException the dao exception
	 */
	Map<Integer, Integer> findAgesMap() throws DaoException;

	/**
	 * Find years list.
	 *
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Integer> findYears() throws DaoException;

	/**
	 * Find years map map.
	 *
	 * @return the map
	 * @throws DaoException the dao exception
	 */
	Map<Integer, Integer> findYearsMap() throws DaoException;

	/**
	 * Find titles map map.
	 *
	 * @return the map
	 * @throws DaoException the dao exception
	 */
	Map<Integer, String> findTitlesMap() throws DaoException;

	/**
	 * Update film optional.
	 *
	 * @param film the film
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Film> updateFilm(Film film) throws DaoException;

	/**
	 * Delete film boolean.
	 *
	 * @param filmId the film id
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean deleteFilm(int filmId) throws DaoException;

	/**
	 * Check title boolean.
	 *
	 * @param title  the title
	 * @param filmId the film id
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean checkTitle(String title, int filmId) throws DaoException;

	/**
	 * Check title boolean.
	 *
	 * @param title the title
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean checkTitle(String title) throws DaoException;

	/**
	 * Create film optional.
	 *
	 * @param film the film
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Film> createFilm(Film film) throws DaoException;

	/**
	 * Update image optional.
	 *
	 * @param filmId the film id
	 * @param img    the img
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Film> updateImage(int filmId, String img) throws DaoException;

	/**
	 * Create genre boolean.
	 *
	 * @param genreName the genre name
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean createGenre(String genreName) throws DaoException;

	/**
	 * Update genre boolean.
	 *
	 * @param genreName the genre name
	 * @param genreId   the genre id
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean updateGenre(String genreName, int genreId) throws DaoException;

	/**
	 * Check genre boolean.
	 *
	 * @param cinemaName the cinema name
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean checkGenre(String cinemaName) throws DaoException;

	/**
	 * Check genre boolean.
	 *
	 * @param genreName the genre name
	 * @param genreId   the genre id
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean checkGenre(String genreName, int genreId) throws DaoException;
}

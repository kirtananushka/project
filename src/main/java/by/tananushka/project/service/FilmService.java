package by.tananushka.project.service;

import by.tananushka.project.bean.Film;
import by.tananushka.project.controller.SessionContent;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Film service.
 */
public interface FilmService {

	/**
	 * Find film by id optional.
	 *
	 * @param filmId the film id
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Film> findFilmById(String filmId) throws ServiceException;

	/**
	 * Find available films list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Film> findAvailableFilms() throws ServiceException;

	/**
	 * Find active films list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Film> findActiveFilms() throws ServiceException;

	/**
	 * Find films by genre list.
	 *
	 * @param genre the genre
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Film> findFilmsByGenre(String genre) throws ServiceException;

	/**
	 * Find active films by genre list.
	 *
	 * @param genre the genre
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Film> findActiveFilmsByGenre(String genre) throws ServiceException;

	/**
	 * Find films by country list.
	 *
	 * @param country the country
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Film> findFilmsByCountry(String country) throws ServiceException;

	/**
	 * Find active films by country list.
	 *
	 * @param country the country
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Film> findActiveFilmsByCountry(String country) throws ServiceException;

	/**
	 * Find films by age list.
	 *
	 * @param strAge the str age
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Film> findFilmsByAge(String strAge) throws ServiceException;

	/**
	 * Find active films by age list.
	 *
	 * @param strAge the str age
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Film> findActiveFilmsByAge(String strAge) throws ServiceException;

	/**
	 * Find films by year list.
	 *
	 * @param strAge the str age
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Film> findFilmsByYear(String strAge) throws ServiceException;

	/**
	 * Find active films by year list.
	 *
	 * @param strAge the str age
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Film> findActiveFilmsByYear(String strAge) throws ServiceException;

	/**
	 * Find genres list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<String> findGenres() throws ServiceException;

	/**
	 * Find genres map map.
	 *
	 * @return the map
	 * @throws ServiceException the service exception
	 */
	Map<Integer, String> findGenresMap() throws ServiceException;

	/**
	 * Find countries list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<String> findCountries() throws ServiceException;

	/**
	 * Find countries map map.
	 *
	 * @return the map
	 * @throws ServiceException the service exception
	 */
	Map<Integer, String> findCountriesMap() throws ServiceException;

	/**
	 * Find ages list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Integer> findAges() throws ServiceException;

	/**
	 * Find ages map map.
	 *
	 * @return the map
	 * @throws ServiceException the service exception
	 */
	Map<Integer, Integer> findAgesMap() throws ServiceException;

	/**
	 * Find years list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Integer> findYears() throws ServiceException;

	/**
	 * Find years map map.
	 *
	 * @return the map
	 * @throws ServiceException the service exception
	 */
	Map<Integer, Integer> findYearsMap() throws ServiceException;

	/**
	 * Find titles map map.
	 *
	 * @return the map
	 * @throws ServiceException the service exception
	 */
	Map<Integer, String> findTitlesMap() throws ServiceException;

	/**
	 * Update film optional.
	 *
	 * @param content the content
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Film> updateFilm(SessionContent content) throws ServiceException;

	/**
	 * Create film optional.
	 *
	 * @param content the content
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Film> createFilm(SessionContent content) throws ServiceException;

	/**
	 * Delete film boolean.
	 *
	 * @param filmId the film id
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean deleteFilm(String filmId) throws ServiceException;

	/**
	 * Check title boolean.
	 *
	 * @param title  the title
	 * @param filmId the film id
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean checkTitle(String title, int filmId) throws ServiceException;

	/**
	 * Check title boolean.
	 *
	 * @param title the title
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean checkTitle(String title) throws ServiceException;

	/**
	 * Update image optional.
	 *
	 * @param filmId the film id
	 * @param img    the img
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Film> updateImage(int filmId, String img) throws ServiceException;

	/**
	 * Create genre boolean.
	 *
	 * @param content the content
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean createGenre(SessionContent content) throws ServiceException;

	/**
	 * Update genre boolean.
	 *
	 * @param content the content
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean updateGenre(SessionContent content) throws ServiceException;
}

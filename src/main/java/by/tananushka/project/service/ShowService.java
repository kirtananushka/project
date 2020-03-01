package by.tananushka.project.service;

import by.tananushka.project.bean.Show;
import by.tananushka.project.controller.SessionContent;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Show service.
 */
public interface ShowService {

	/**
	 * Find show by id optional.
	 *
	 * @param strFilmId the str film id
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Show> findShowById(String strFilmId) throws ServiceException;

	/**
	 * Find show map.
	 *
	 * @param strDate the str date
	 * @return the map
	 * @throws ServiceException the service exception
	 */
	Map<String, List<Show>> findShow(String strDate)
					throws ServiceException;

	/**
	 * Find show map.
	 *
	 * @param strDate     the str date
	 * @param strCinemaId the str cinema id
	 * @return the map
	 * @throws ServiceException the service exception
	 */
	Map<String, List<Show>> findShow(String strDate, String strCinemaId)
					throws ServiceException;

	/**
	 * Find dates list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<LocalDate> findDates() throws ServiceException;

	/**
	 * Find cinemas map.
	 *
	 * @return the map
	 * @throws ServiceException the service exception
	 */
	Map<Integer, String> findCinemas() throws ServiceException;

	/**
	 * Find active cinemas map.
	 *
	 * @return the map
	 * @throws ServiceException the service exception
	 */
	Map<Integer, String> findActiveCinemas() throws ServiceException;

	/**
	 * Find inactive cinemas map.
	 *
	 * @return the map
	 * @throws ServiceException the service exception
	 */
	Map<Integer, String> findInactiveCinemas() throws ServiceException;

	/**
	 * Update show optional.
	 *
	 * @param content the content
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Show> updateShow(SessionContent content) throws ServiceException;

	/**
	 * Create show optional.
	 *
	 * @param content the content
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Show> createShow(SessionContent content) throws ServiceException;

	/**
	 * Delete show boolean.
	 *
	 * @param showId the show id
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean deleteShow(String showId) throws ServiceException;

	/**
	 * Create cinema boolean.
	 *
	 * @param content the content
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean createCinema(SessionContent content) throws ServiceException;

	/**
	 * Delete cinema boolean.
	 *
	 * @param content the content
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean deleteCinema(SessionContent content) throws ServiceException;

	/**
	 * Restore cinema boolean.
	 *
	 * @param content the content
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean restoreCinema(SessionContent content) throws ServiceException;

	/**
	 * Update cinema boolean.
	 *
	 * @param content the content
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean updateCinema(SessionContent content) throws ServiceException;
}

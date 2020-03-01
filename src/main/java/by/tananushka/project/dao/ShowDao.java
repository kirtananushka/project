package by.tananushka.project.dao;

import by.tananushka.project.bean.Show;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Show dao.
 */
public interface ShowDao extends AbstractDao {

	/**
	 * Find show by id optional.
	 *
	 * @param showId the show id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Show> findShowById(int showId) throws DaoException;

	/**
	 * Find show map.
	 *
	 * @param date the date
	 * @return the map
	 * @throws DaoException the dao exception
	 */
	Map<String, List<Show>> findShow(LocalDate date) throws DaoException;

	/**
	 * Find show map.
	 *
	 * @param date   the date
	 * @param cinema the cinema
	 * @return the map
	 * @throws DaoException the dao exception
	 */
	Map<String, List<Show>> findShow(LocalDate date, String cinema) throws DaoException;

	/**
	 * Find show map.
	 *
	 * @param date     the date
	 * @param cinemaId the cinema id
	 * @return the map
	 * @throws DaoException the dao exception
	 */
	Map<String, List<Show>> findShow(LocalDate date, int cinemaId) throws DaoException;

	/**
	 * Find dates list.
	 *
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<LocalDate> findDates() throws DaoException;

	/**
	 * Find cinemas map map.
	 *
	 * @return the map
	 * @throws DaoException the dao exception
	 */
	Map<Integer, String> findCinemasMap() throws DaoException;

	/**
	 * Find active cinemas map map.
	 *
	 * @return the map
	 * @throws DaoException the dao exception
	 */
	Map<Integer, String> findActiveCinemasMap() throws DaoException;

	/**
	 * Find inactive cinemas map map.
	 *
	 * @return the map
	 * @throws DaoException the dao exception
	 */
	Map<Integer, String> findInactiveCinemasMap() throws DaoException;

	/**
	 * Update show optional.
	 *
	 * @param show the show
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Show> updateShow(Show show) throws DaoException;

	/**
	 * Delete show boolean.
	 *
	 * @param showId the show id
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean deleteShow(int showId) throws DaoException;

	/**
	 * Create show optional.
	 *
	 * @param show the show
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Show> createShow(Show show) throws DaoException;

	/**
	 * Create cinema boolean.
	 *
	 * @param cinemaName the cinema name
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean createCinema(String cinemaName) throws DaoException;

	/**
	 * Update cinema boolean.
	 *
	 * @param cinemaName the cinema name
	 * @param cinemaId   the cinema id
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean updateCinema(String cinemaName, int cinemaId) throws DaoException;

	/**
	 * Check cinema boolean.
	 *
	 * @param cinemaName the cinema name
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean checkCinema(String cinemaName) throws DaoException;

	/**
	 * Check cinema boolean.
	 *
	 * @param cinemaName the cinema name
	 * @param cinemaId   the cinema id
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean checkCinema(String cinemaName, int cinemaId) throws DaoException;

	/**
	 * Delete cinema boolean.
	 *
	 * @param cinemaName the cinema name
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean deleteCinema(String cinemaName) throws DaoException;

	/**
	 * Restore cinema boolean.
	 *
	 * @param cinemaName the cinema name
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean restoreCinema(String cinemaName) throws DaoException;
}

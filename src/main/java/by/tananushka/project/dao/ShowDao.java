package by.tananushka.project.dao;

import by.tananushka.project.bean.Show;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ShowDao extends AbstractDao {

	Optional<Show> findShowById(int showId) throws DaoException;

	Map<String, List<Show>> findShow(LocalDate date) throws DaoException;

	Map<String, List<Show>> findShow(LocalDate date, String cinema) throws DaoException;

	Map<String, List<Show>> findShow(LocalDate date, int cinemaId) throws DaoException;

	List<LocalDate> findDates() throws DaoException;

	Map<Integer, String> findCinemasMap() throws DaoException;

	Map<Integer, String> findActiveCinemasMap() throws DaoException;

	Map<Integer, String> findInactiveCinemasMap() throws DaoException;

	Optional<Show> updateShow(Show show) throws DaoException;

	boolean deleteShow(int showId) throws DaoException;

	Optional<Show> createShow(Show show) throws DaoException;

	boolean createCinema(String cinemaName) throws DaoException;

	boolean updateCinema(String cinemaName, int cinemaId) throws DaoException;

	boolean checkCinema(String cinemaName) throws DaoException;

	boolean checkCinema(String cinemaName, int cinemaId) throws DaoException;

	boolean deleteCinema(String cinemaName) throws DaoException;

	boolean restoreCinema(String cinemaName) throws DaoException;
}

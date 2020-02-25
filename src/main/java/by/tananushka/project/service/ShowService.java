package by.tananushka.project.service;

import by.tananushka.project.bean.Show;
import by.tananushka.project.controller.SessionContent;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ShowService {

	Optional<Show> findShowById(String strFilmId) throws ServiceException;

	Map<String, List<Show>> findShow(String strDate)
					throws ServiceException;

	Map<String, List<Show>> findShow(String strDate, String strCinemaId)
					throws ServiceException;

	List<LocalDate> findDates() throws ServiceException;

	Map<Integer, String> findCinemas() throws ServiceException;

	Map<Integer, String> findActiveCinemas() throws ServiceException;

	Map<Integer, String> findInactiveCinemas() throws ServiceException;

	Optional<Show> updateShow(SessionContent content) throws ServiceException;

	Optional<Show> createShow(SessionContent content) throws ServiceException;

	boolean deleteShow(String showId) throws ServiceException;

	boolean createCinema(SessionContent content) throws ServiceException;

	boolean deleteCinema(SessionContent content) throws ServiceException;

	boolean restoreCinema(SessionContent content) throws ServiceException;

	boolean updateCinema(SessionContent content) throws ServiceException;
}

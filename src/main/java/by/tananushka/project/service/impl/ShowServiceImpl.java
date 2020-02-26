package by.tananushka.project.service.impl;

import by.tananushka.project.bean.Film;
import by.tananushka.project.bean.Show;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.DaoProvider;
import by.tananushka.project.dao.ShowDao;
import by.tananushka.project.parsing.DateParser;
import by.tananushka.project.parsing.MoneyParser;
import by.tananushka.project.parsing.ParsingException;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.ShowService;
import by.tananushka.project.service.validation.EscapeCharactersChanger;
import by.tananushka.project.service.validation.FilmDataValidator;
import by.tananushka.project.service.validation.ShowDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShowServiceImpl implements ShowService {

	private static final ShowService instance = new ShowServiceImpl();
	private static final ShowDataValidator validator = ShowDataValidator.getInstance();
	private static final FilmDataValidator filmValidator = FilmDataValidator.getInstance();
	private static final EscapeCharactersChanger changer = EscapeCharactersChanger.getInstance();
	private static Logger log = LogManager.getLogger();
	private ShowDao showDao = DaoProvider.getInstance().getShowDao();

	private ShowServiceImpl() {
	}

	public static ShowService getInstance() {
		return instance;
	}

	@Override
	public Optional<Show> findShowById(String strShowId) throws ServiceException {
		Optional<Show> showOptional = Optional.empty();
		if (filmValidator.checkIdString(strShowId) && filmValidator.checkId(strShowId)) {
			int showId = Integer.parseInt(strShowId);
			try {
				showOptional = showDao.findShowById(showId);
			} catch (DaoException e) {
				throw new ServiceException("Exception while getting film by id");
			}
		}
		return showOptional;
	}

	@Override
	public Map<String, List<Show>> findShow(String strDate) throws
					ServiceException {
		Map<String, List<Show>> showsMap = new LinkedHashMap<>();
		if (validator.checkDate(strDate)) {
			log.debug("Cinema '{}' is valid.", strDate);
			try {
				Optional<LocalDate> dateOptional = DateParser.getInstance().parseDate(strDate);
				LocalDate date = null;
				if (dateOptional.isPresent()) {
					date = dateOptional.get();
				} else {
					log.error("Exception while date parsing; date is null.");
					throw new ServiceException("Exception while date parsing; date is null.");
				}
				showsMap = showDao.findShow(date);
			} catch (DaoException e) {
				throw new ServiceException("Exception while getting shows by date.", e);
			} catch (ParsingException e) {
				throw new ServiceException("Exception while date parsing.", e);
			}
		}
		return showsMap;
	}

	@Override
	public Map<String, List<Show>> findShow(String strDate, String strCinemaId)
					throws ServiceException {
		Map<String, List<Show>> showsMap = new LinkedHashMap<>();
		if (validator.checkDate(strDate) && validator.checkTwoDigits(strCinemaId)) {
			log.debug("Date '{}' and cinema '{}' are valid.", strDate, strCinemaId);
			int cinemaId = Integer.parseInt(strCinemaId);
			try {
				Optional<LocalDate> dateOptional = DateParser.getInstance().parseDate(strDate);
				LocalDate date;
				if (dateOptional.isPresent()) {
					date = dateOptional.get();
				} else {
					log.error("Exception while date parsing; date is null.");
					throw new ServiceException("Exception while date parsing; date is null.");
				}
				showsMap = showDao.findShow(date, cinemaId);
			} catch (DaoException e) {
				throw new ServiceException("Exception while getting shows by date and cinema.", e);
			} catch (ParsingException e) {
				throw new ServiceException("Exception while date parsing.", e);
			}
		}
		return showsMap;
	}

	@Override
	public List<LocalDate> findDates() throws ServiceException {
		List<LocalDate> datesList;
		try {
			datesList = showDao.findDates();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting dates list.", e);
		}
		return datesList;
	}

	@Override
	public Map<Integer, String> findCinemas() throws ServiceException {
		Map<Integer, String> cinemasMap;
		try {
			cinemasMap = showDao.findCinemasMap();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting cinemas map.", e);
		}
		return cinemasMap;
	}

	@Override
	public Map<Integer, String> findActiveCinemas() throws ServiceException {
		Map<Integer, String> cinemasMap;
		try {
			cinemasMap = showDao.findActiveCinemasMap();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting active cinemas map.", e);
		}
		return cinemasMap;
	}

	@Override
	public Map<Integer, String> findInactiveCinemas() throws ServiceException {
		Map<Integer, String> cinemasMap;
		try {
			cinemasMap = showDao.findInactiveCinemasMap();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting inactive cinemas map.", e);
		}
		return cinemasMap;
	}

	@Override
	public Optional<Show> updateShow(SessionContent content) throws ServiceException {
		Optional<Show> showOptional;
		boolean isParameterValid = true;
		Map<String, String> errorsMap = new LinkedHashMap<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_SHOW_MESSAGE, null);
		String strShowId = content.getRequestParameter(ParamName.PARAM_SHOW_ID).strip();
		int showId = 0;
		if (!filmValidator.checkIdString(strShowId) || !filmValidator.checkId(strShowId)) {
			errorsMap.put(ErrorMessageKey.INVALID_ID, strShowId);
			isParameterValid = false;
		} else {
			showId = Integer.parseInt(strShowId);
		}
		String strFilmId = content.getRequestParameter(ParamName.PARAM_TITLE).strip();
		int filmId = 0;
		if (!filmValidator.checkIdString(strFilmId) || !filmValidator.checkId(strFilmId)) {
			errorsMap.put(ErrorMessageKey.INVALID_ID, strFilmId);
			isParameterValid = false;
		} else {
			filmId = Integer.parseInt(strFilmId);
		}
		String strCinemaId = content.getRequestParameter(ParamName.PARAM_CINEMA).strip();
		int cinemaId = 0;
		if (!validator.checkTwoDigits(strCinemaId)) {
			errorsMap.put(ErrorMessageKey.INVALID_ID, strCinemaId);
			isParameterValid = false;
		} else {
			cinemaId = Integer.parseInt(strCinemaId);
		}
		Map<Integer, String> cinemasMap =
						(Map<Integer, String>) content.getSessionAttribute(ParamName.PARAM_CINEMAS_MAP);
		String cinemaName = cinemasMap.get(cinemaId);
		String date = content.getRequestParameter(ParamName.PARAM_DATE);
		String hour = content.getRequestParameter(ParamName.PARAM_HOUR);
		String minute = content.getRequestParameter(ParamName.PARAM_MINUTE);
		LocalDateTime dateTime = null;
		try {
			Optional<LocalDateTime> dateTimeOptional =
							DateParser.getInstance().parseDateTime(date, hour, minute);
			if (dateTimeOptional.isPresent()) {
				dateTime = dateTimeOptional.get();
			} else {
				throw new ServiceException("Exception while parsing date and time: object is null");
			}
		} catch (ParsingException e) {
			throw new ServiceException("Exception while parsing date and time.", e);
		}
		String ruble = content.getRequestParameter(ParamName.PARAM_RUBLE);
		String copeck = content.getRequestParameter(ParamName.PARAM_COPECK);
		BigDecimal cost = null;
		try {
			Optional<BigDecimal> optionalBigDecimal =
							MoneyParser.getInstance().parseToBigDecimal(ruble, copeck);
			if (optionalBigDecimal.isPresent()) {
				cost = optionalBigDecimal.get();
			} else {
				throw new ServiceException("Exception while parsing money: object is null");
			}
		} catch (ParsingException e) {
			throw new ServiceException("Exception while parsing money.", e);
		}
		String strFreePlace = content.getRequestParameter(ParamName.PARAM_FREE_PLACE);
		int freePlace = 0;
		if (!validator.checkTwoDigits(strFreePlace)) {
			errorsMap.put(ErrorMessageKey.INVALID_PLACE_NUMBER, strCinemaId);
			isParameterValid = false;
		} else {
			freePlace = Integer.parseInt(strFreePlace);
		}
		Show show;
		if (isParameterValid) {
			Film film = new Film();
			show = new Show();
			film.setId(filmId);
			show.setFilm(film);
			show.setId(showId);
			show.setCinemaName(cinemaName);
			show.setDateTime(dateTime);
			show.setCost(cost);
			show.setFreePlace(freePlace);
		} else {
			content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_SHOW_MESSAGE, errorsMap);
			throw new ServiceException("Invalid parameter(s)");
		}
		try {
			showOptional = showDao.updateShow(show);
		} catch (DaoException e) {
			log.error("Exception while updating show.", e);
			throw new ServiceException("Exception while updating show.", e);
		}
		return showOptional;
	}

	@Override
	public boolean deleteShow(String strShowId) throws ServiceException {
		boolean result;
		try {
			int showId = Integer.parseInt(strShowId);
			result = showDao.deleteShow(showId);
		} catch (DaoException e) {
			throw new ServiceException("Exception while show deletion.", e);
		}
		return result;
	}

	@Override
	public boolean createCinema(SessionContent content) throws ServiceException {
		boolean result = false;
		boolean isParameterValid = true;
		Map<String, String> errorsMap = new LinkedHashMap<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_CINEMA_MESSAGE, null);
		String cinemaName = content.getRequestParameter(ParamName.PARAM_CINEMA).strip();
		cinemaName = changer.changeCharacters(cinemaName);
		if (!validator.checkCinema(cinemaName)) {
			isParameterValid = false;
			errorsMap.put(ErrorMessageKey.INVALID_NAME, cinemaName);
		}
		if (!checkCinema(cinemaName)) {
			isParameterValid = false;
			errorsMap.put(ErrorMessageKey.CINEMA_EXISTS, cinemaName);
		}
		content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_CINEMA_MESSAGE, errorsMap);
		if (isParameterValid) {
			try {
				result = showDao.createCinema(cinemaName);
			} catch (DaoException e) {
				throw new ServiceException("Exception while cinema creation.", e);
			}
		}
		return result;
	}

	@Override
	public boolean updateCinema(SessionContent content) throws ServiceException {
		boolean result = false;
		boolean isParameterValid = true;
		Map<String, String> errorsMap = new LinkedHashMap<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_CINEMA_MESSAGE, null);
		String cinemaName = content.getRequestParameter(ParamName.PARAM_CINEMA).strip();
		String strCinemaId = content.getRequestParameter(ParamName.PARAM_CINEMA_ID).strip();
		int cinemaId = 0;
		if (!validator.checkTwoDigits(strCinemaId)) {
			isParameterValid = false;
			errorsMap.put(ErrorMessageKey.INVALID_ID, strCinemaId);
		} else {
			cinemaId = Integer.parseInt(strCinemaId);
		}
		cinemaName = changer.changeCharacters(cinemaName);
		if (!validator.checkCinema(cinemaName)) {
			isParameterValid = false;
			errorsMap.put(ErrorMessageKey.INVALID_NAME, cinemaName);
		}
		if (!checkCinema(cinemaName, cinemaId)) {
			isParameterValid = false;
			errorsMap.put(ErrorMessageKey.CINEMA_EXISTS, cinemaName);
		}
		content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_CINEMA_MESSAGE, errorsMap);
		if (isParameterValid) {
			try {
				result = showDao.updateCinema(cinemaName, cinemaId);
			} catch (DaoException e) {
				throw new ServiceException("Exception while cinema updating.", e);
			}
		} else {
			content.assignRequestAttribute(ParamName.PARAM_CINEMA, cinemaName);
			content.assignRequestAttribute(ParamName.PARAM_CINEMA_ID, cinemaId);
		}
		return result;
	}

	private boolean checkCinema(String cinemaName) throws ServiceException {
		boolean isCinemaNameFree;
		try {
			isCinemaNameFree = showDao.checkCinema(cinemaName);
		} catch (DaoException e) {
			throw new ServiceException("Exception while cinema name checking", e);
		}
		return isCinemaNameFree;
	}

	private boolean checkCinema(String cinemaName, int cinemaId) throws ServiceException {
		boolean isCinemaNameFree;
		try {
			isCinemaNameFree = showDao.checkCinema(cinemaName, cinemaId);
		} catch (DaoException e) {
			throw new ServiceException("Exception while cinema name checking", e);
		}
		return isCinemaNameFree;
	}

	@Override
	public boolean deleteCinema(SessionContent content) throws ServiceException {
		boolean result = false;
		boolean isParameterValid = true;
		Map<String, String> errorsMap = new LinkedHashMap<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_DELETE_CINEMA_MESSAGE, null);
		String strCinemaId = content.getRequestParameter(ParamName.PARAM_CINEMA).strip();
		int cinemaId;
		String cinemaName = null;
		if (!validator.checkTwoDigits(strCinemaId)) {
			errorsMap.put(ErrorMessageKey.INVALID_ID, strCinemaId);
			isParameterValid = false;
		} else {
			cinemaId = Integer.parseInt(strCinemaId);
			Map<Integer, String> cinemasMap =
							(Map<Integer, String>) content.getSessionAttribute(ParamName.PARAM_CINEMAS_MAP);
			cinemaName = cinemasMap.get(cinemaId);
			if (checkCinema(cinemaName)) {
				isParameterValid = false;
				errorsMap.put(ErrorMessageKey.CINEMA_DOES_NOT_EXIST, cinemaName);
			}
		}
		content.assignSessionAttribute(ParamName.PARAM_ERR_DELETE_CINEMA_MESSAGE, errorsMap);
		if (isParameterValid) {
			try {
				result = showDao.deleteCinema(cinemaName);
			} catch (DaoException e) {
				throw new ServiceException("Exception while cinema creation.", e);
			}
		}
		return result;
	}

	@Override
	public boolean restoreCinema(SessionContent content) throws ServiceException {
		boolean result = false;
		boolean isParameterValid = true;
		Map<String, String> errorsMap = new LinkedHashMap<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_RESTORE_CINEMA_MESSAGE, null);
		String strCinemaId = content.getRequestParameter(ParamName.PARAM_CINEMA).strip();
		int cinemaId;
		String cinemaName = null;
		if (!validator.checkTwoDigits(strCinemaId)) {
			errorsMap.put(ErrorMessageKey.INVALID_ID, strCinemaId);
			isParameterValid = false;
		} else {
			cinemaId = Integer.parseInt(strCinemaId);
			Map<Integer, String> cinemasMap =
							(Map<Integer, String>) content.getSessionAttribute(ParamName.PARAM_CINEMAS_MAP);
			cinemaName = cinemasMap.get(cinemaId);
			if (!checkCinema(cinemaName)) {
				isParameterValid = false;
				errorsMap.put(ErrorMessageKey.CINEMA_EXISTS, cinemaName);
			}
		}
		content.assignSessionAttribute(ParamName.PARAM_ERR_RESTORE_CINEMA_MESSAGE, errorsMap);
		if (isParameterValid) {
			try {
				result = showDao.restoreCinema(cinemaName);
			} catch (DaoException e) {
				throw new ServiceException("Exception while cinema creation.", e);
			}
		}
		return result;
	}

	@Override
	public Optional<Show> createShow(SessionContent content) throws ServiceException {
		Optional<Show> showOptional;
		boolean isParameterValid = true;
		Map<String, String> errorsMap = new LinkedHashMap<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_SHOW_MESSAGE, null);
		String strFilmId = content.getRequestParameter(ParamName.PARAM_TITLE).strip();
		int filmId = 0;
		if (!filmValidator.checkIdString(strFilmId) || !filmValidator.checkId(strFilmId)) {
			errorsMap.put(ErrorMessageKey.INVALID_ID, strFilmId);
			isParameterValid = false;
		} else {
			filmId = Integer.parseInt(strFilmId);
		}
		String strCinemaId = content.getRequestParameter(ParamName.PARAM_CINEMA).strip();
		int cinemaId = 0;
		if (!validator.checkTwoDigits(strCinemaId)) {
			errorsMap.put(ErrorMessageKey.INVALID_ID, strCinemaId);
			isParameterValid = false;
		} else {
			cinemaId = Integer.parseInt(strCinemaId);
		}
		Map<Integer, String> cinemasMap =
						(Map<Integer, String>) content.getSessionAttribute(ParamName.PARAM_CINEMAS_MAP);
		String cinemaName = cinemasMap.get(cinemaId);
		String date = content.getRequestParameter(ParamName.PARAM_DATE);
		String hour = content.getRequestParameter(ParamName.PARAM_HOUR);
		String minute = content.getRequestParameter(ParamName.PARAM_MINUTE);
		LocalDateTime dateTime = null;
		try {
			Optional<LocalDateTime> dateTimeOptional =
							DateParser.getInstance().parseDateTime(date, hour, minute);
			if (dateTimeOptional.isPresent()) {
				dateTime = dateTimeOptional.get();
			} else {
				throw new ServiceException("Exception while parsing date and time: object is null");
			}
		} catch (ParsingException e) {
			throw new ServiceException("Exception while parsing date and time.", e);
		}
		String ruble = content.getRequestParameter(ParamName.PARAM_RUBLE);
		String copeck = content.getRequestParameter(ParamName.PARAM_COPECK);
		BigDecimal cost = null;
		try {
			Optional<BigDecimal> optionalBigDecimal =
							MoneyParser.getInstance().parseToBigDecimal(ruble, copeck);
			if (optionalBigDecimal.isPresent()) {
				cost = optionalBigDecimal.get();
			} else {
				throw new ServiceException("Exception while parsing money: object is null");
			}
		} catch (ParsingException e) {
			throw new ServiceException("Exception while parsing money.", e);
		}
		String strFreePlace = content.getRequestParameter(ParamName.PARAM_FREE_PLACE);
		int freePlace = 0;
		if (!validator.checkTwoDigits(strFreePlace)) {
			errorsMap.put(ErrorMessageKey.INVALID_PLACE_NUMBER, strCinemaId);
			isParameterValid = false;
		} else {
			freePlace = Integer.parseInt(strFreePlace);
		}
		Show show;
		if (isParameterValid) {
			Film film = new Film();
			show = new Show();
			film.setId(filmId);
			show.setFilm(film);
			show.setCinemaName(cinemaName);
			show.setDateTime(dateTime);
			show.setCost(cost);
			show.setFreePlace(freePlace);
		} else {
			content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_SHOW_MESSAGE, errorsMap);
			throw new ServiceException("Invalid parameter(s)");
		}
		try {
			showOptional = showDao.createShow(show);
		} catch (DaoException e) {
			log.error("Exception while creating show.", e);
			throw new ServiceException("Exception while creating show.", e);
		}
		return showOptional;
	}
}

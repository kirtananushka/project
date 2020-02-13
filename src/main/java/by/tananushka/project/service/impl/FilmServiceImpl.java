package by.tananushka.project.service.impl;

import by.tananushka.project.bean.Film;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.DaoProvider;
import by.tananushka.project.dao.FilmDao;
import by.tananushka.project.service.FilmService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.validation.FilmDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FilmServiceImpl implements FilmService {

	private static final FilmService instance = new FilmServiceImpl();
	private static final FilmDataValidator validator = FilmDataValidator.getInstance();
	private static Logger log = LogManager.getLogger();
	private FilmDao filmDao = DaoProvider.getInstance().getFilmDao();

	private FilmServiceImpl() {
	}

	public static FilmService getInstance() {
		return instance;
	}

	@Override
	public List<Film> findAvailableFilms() throws ServiceException {
		List<Film> filmsList;
		try {
			filmsList = filmDao.findAvailableFilms();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting available films");
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByGenre(String genre) throws ServiceException {
		List<Film> filmsList = new ArrayList<>();
		if (genre.isBlank() || validator.checkString(genre)) {
			log.debug("Genre is valid");
			try {
				filmsList = filmDao.findFilmsByGenre(genre);
			} catch (DaoException e) {
				throw new ServiceException("Exception while getting films by genre");
			}
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByCountry(String country) throws ServiceException {
		List<Film> filmsList = new ArrayList<>();
		if (country.isBlank() || validator.checkString(country)) {
			log.debug("Country is valid");
			try {
				filmsList = filmDao.findFilmsByCountry(country);
			} catch (DaoException e) {
				throw new ServiceException("Exception while getting films by country");
			}
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByAge(String strAge) throws ServiceException {
		List<Film> filmsList = new ArrayList<>();
		if (validator.checkNumber(strAge)) {
			int age = Integer.parseInt(strAge);
			if (validator.checkAge(age)) {
				try {
					filmsList = filmDao.findFilmsByAge(age);
				} catch (DaoException e) {
					throw new ServiceException("Exception while getting films by age");
				}
			}
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByYear(String strYear) throws ServiceException {
		List<Film> filmsList = new ArrayList<>();
		if (validator.checkNumber(strYear)) {
			int year = Integer.parseInt(strYear);
			if (validator.checkYear(year)) {
				try {
					filmsList = filmDao.findFilmsByYear(year);
				} catch (DaoException e) {
					throw new ServiceException("Exception while getting films by year");
				}
			}
		}
		return filmsList;
	}

	@Deprecated
	public List<Film> findFilmsByParams(String genre, String country,
	                                    String strAge, String strYear) throws ServiceException {
		boolean isParamValid = true;
		List<Film> filmsList = new ArrayList<>();
		int age = 0;
		int year = 0;
		if (!validator.checkString(genre) && !genre.isBlank()) {
			isParamValid = false;
		}
		if (!validator.checkString(country) && !country.isBlank()) {
			isParamValid = false;
		}
		if (!strYear.isBlank()) {
			if (validator.checkNumber(strAge)) {
				age = Integer.parseInt(strAge);
				if (!validator.checkAge(age)) {
					isParamValid = false;
				}
			} else {
				isParamValid = false;
			}
		}
		if (!strYear.isBlank()) {
			if (validator.checkNumber(strYear)) {
				year = Integer.parseInt(strYear);
				if (!validator.checkAge(year)) {
					isParamValid = false;
				}
			} else {
				isParamValid = false;
			}
		}
		log.debug(isParamValid);
		return filmsList;
	}

	@Override
	public List<String> findGenres() throws ServiceException {
		List<String> genresList;
		try {
			genresList = filmDao.findGenres();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting genres");
		}
		return genresList;
	}

	@Override
	public List<String> findCountries() throws ServiceException {
		List<String> countriesList;
		try {
			countriesList = filmDao.findCountries();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting countries");
		}
		return countriesList;
	}

	@Override
	public List<Integer> findAges() throws ServiceException {
		List<Integer> agesList;
		try {
			agesList = filmDao.findAges();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting ages");
		}
		return agesList;
	}

	@Override
	public List<Integer> findYears() throws ServiceException {
		List<Integer> yearsList;
		try {
			yearsList = filmDao.findYears();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting years");
		}
		return yearsList;
	}
}

package by.tananushka.project.service.impl;

import by.tananushka.project.bean.Film;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.DaoProvider;
import by.tananushka.project.dao.FilmDao;
import by.tananushka.project.service.FilmService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.validation.FilmDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
	public Optional<Film> findFilmById(String strFilmId) throws ServiceException {
		Optional<Film> filmOptional = Optional.empty();
		if (validator.checkIdString(strFilmId) && validator.checkId(strFilmId)) {
			int filmId = Integer.parseInt(strFilmId);
			try {
				filmOptional = filmDao.findFilmById(filmId);
			} catch (DaoException e) {
				throw new ServiceException("Exception while getting film by id.");
			}
		}
		return filmOptional;
	}

	@Override
	public List<Film> findAvailableFilms() throws ServiceException {
		List<Film> filmsList;
		try {
			filmsList = filmDao.findAvailableFilms();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting available films.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findActiveFilms() throws ServiceException {
		List<Film> filmsList;
		try {
			filmsList = filmDao.findActiveFilms();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting active films.", e);
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByGenre(String genre) throws ServiceException {
		List<Film> filmsList = new ArrayList<>();
		if (genre.isBlank() || validator.checkString(genre) || validator.checkNumber(genre)) {
			log.debug("Genre is valid");
			try {
				filmsList = validator.checkNumber(genre) ?
				            filmDao.findFilmsByGenre(Integer.parseInt(genre)) :
				            filmDao.findFilmsByGenre(genre);
			} catch (DaoException e) {
				throw new ServiceException("Exception while getting films by genre.", e);
			}
		}
		return filmsList;
	}

	@Override
	public List<Film> findActiveFilmsByGenre(String genre) throws ServiceException {
		List<Film> filmsList = new ArrayList<>();
		if (genre.isBlank() || validator.checkString(genre) || validator.checkNumber(genre)) {
			log.debug("Genre is valid");
			try {
				filmsList = validator.checkNumber(genre) ?
				            filmDao.findActiveFilmsByGenre(Integer.parseInt(genre)) :
				            filmDao.findActiveFilmsByGenre(genre);
			} catch (DaoException e) {
				throw new ServiceException("Exception while getting films by genre.", e);
			}
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByCountry(String country) throws ServiceException {
		List<Film> filmsList = new ArrayList<>();
		if (country.isBlank() || validator.checkString(country) || validator.checkNumber(country)) {
			log.debug("Country is valid");
			try {
				filmsList = validator.checkNumber(country) ?
				            filmDao.findFilmsByCountry(Integer.parseInt(country)) :
				            filmDao.findFilmsByCountry(country);
			} catch (DaoException e) {
				throw new ServiceException("Exception while getting films by country.", e);
			}
		}
		return filmsList;
	}

	@Override
	public List<Film> findActiveFilmsByCountry(String country) throws ServiceException {
		List<Film> filmsList = new ArrayList<>();
		if (country.isBlank() || validator.checkString(country) || validator.checkNumber(country)) {
			log.debug("Country is valid");
			try {
				filmsList = validator.checkNumber(country) ?
				            filmDao.findActiveFilmsByCountry(Integer.parseInt(country)) :
				            filmDao.findActiveFilmsByCountry(country);
			} catch (DaoException e) {
				throw new ServiceException("Exception while getting films by country.", e);
			}
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByAge(String strAge) throws ServiceException {
		List<Film> filmsList = new ArrayList<>();
		if (validator.checkTwoDigitsString(strAge)) {
			int age = Integer.parseInt(strAge);
			if (validator.checkAge(age)) {
				try {
					filmsList = filmDao.findFilmsByAge(age);
				} catch (DaoException e) {
					throw new ServiceException("Exception while getting films by age.", e);
				}
			}
		}
		return filmsList;
	}

	@Override
	public List<Film> findActiveFilmsByAge(String strAge) throws ServiceException {
		List<Film> filmsList = new ArrayList<>();
		if (validator.checkTwoDigitsString(strAge)) {
			int age = Integer.parseInt(strAge);
			if (validator.checkAge(age)) {
				try {
					filmsList = filmDao.findActiveFilmsByAge(age);
				} catch (DaoException e) {
					throw new ServiceException("Exception while getting films by age.", e);
				}
			}
		}
		return filmsList;
	}

	@Override
	public List<Film> findFilmsByYear(String strYear) throws ServiceException {
		List<Film> filmsList = new ArrayList<>();
		if (validator.checkYearString(strYear)) {
			int year = Integer.parseInt(strYear);
			if (validator.checkYear(year)) {
				try {
					filmsList = filmDao.findFilmsByYear(year);
				} catch (DaoException e) {
					throw new ServiceException("Exception while getting films by year.", e);
				}
			}
		}
		return filmsList;
	}

	@Override
	public List<Film> findActiveFilmsByYear(String strYear) throws ServiceException {
		List<Film> filmsList = new ArrayList<>();
		if (validator.checkYearString(strYear)) {
			int year = Integer.parseInt(strYear);
			if (validator.checkYear(year)) {
				try {
					filmsList = filmDao.findActiveFilmsByYear(year);
				} catch (DaoException e) {
					throw new ServiceException("Exception while getting films by year.", e);
				}
			}
		}
		return filmsList;
	}

	@Override
	public List<String> findGenres() throws ServiceException {
		List<String> genresList;
		try {
			genresList = filmDao.findGenres();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting genres.", e);
		}
		return genresList;
	}

	@Override
	public Map<Integer, String> findGenresMap() throws ServiceException {
		Map<Integer, String> genresMap;
		try {
			genresMap = filmDao.findGenresMap();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting genres.", e);
		}
		return genresMap;
	}

	@Override
	public List<String> findCountries() throws ServiceException {
		List<String> countriesList;
		try {
			countriesList = filmDao.findCountries();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting countries.");
		}
		return countriesList;
	}

	@Override
	public Map<Integer, String> findCountriesMap() throws ServiceException {
		Map<Integer, String> countriesMap;
		try {
			countriesMap = filmDao.findCountriesMap();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting countries.");
		}
		return countriesMap;
	}

	@Override
	public List<Integer> findAges() throws ServiceException {
		List<Integer> agesList;
		try {
			agesList = filmDao.findAges();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting ages.");
		}
		return agesList;
	}

	@Override
	public Map<Integer, Integer> findAgesMap() throws ServiceException {
		Map<Integer, Integer> agesList;
		try {
			agesList = filmDao.findAgesMap();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting ages.");
		}
		return agesList;
	}

	@Override
	public List<Integer> findYears() throws ServiceException {
		List<Integer> yearsList;
		try {
			yearsList = filmDao.findYears();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting years.");
		}
		return yearsList;
	}

	@Override
	public Map<Integer, Integer> findYearsMap() throws ServiceException {
		Map<Integer, Integer> yearsMap;
		try {
			yearsMap = filmDao.findYearsMap();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting years.");
		}
		return yearsMap;
	}

	@Override
	public Map<Integer, String> findTitlesMap() throws ServiceException {
		Map<Integer, String> titlesMap;
		try {
			titlesMap = filmDao.findTitlesMap();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting titles.");
		}
		return titlesMap;
	}

	@Override
	public Optional<Film> updateFilm(SessionContent content) throws ServiceException {
		Optional<Film> filmOptional;
		boolean isParameterValid = true;
		Map<String, String> errorsMap = new LinkedHashMap<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_FILM_MESSAGE, null);
		String strFilmId = content.getRequestParameter(ParamName.PARAM_FILM_ID).strip();
		int filmId = 0;
		if (!validator.checkIdString(strFilmId) || !validator.checkId(strFilmId)) {
			errorsMap.put(ErrorMessageKey.INVALID_ID, strFilmId);
			isParameterValid = false;
		} else {
			filmId = Integer.parseInt(strFilmId);
		}
		String title = content.getRequestParameter(ParamName.PARAM_TITLE).strip();
		if (!validator.checkTitle(title)) {
			errorsMap.put(ErrorMessageKey.INVALID_TITLE, title);
			isParameterValid = false;
		}
		if (!checkTitle(title, filmId)) {
			errorsMap.put(ErrorMessageKey.TITLE_EXISTS, title);
			isParameterValid = false;
		}
		Map<Integer, String> genresMap = findGenresMap();
		String[] genres = content.getParameterValuesByName(ParamName.PARAM_GENRES_ARR);
		Set<String> genresSet = null;
		if (!validator.checkStringNumberArray(genres)) {
			errorsMap.put(ErrorMessageKey.GENRES_NOT_VALID, "");
			isParameterValid = false;
		} else {
			genresSet = Arrays.stream(genres)
			                  .map(Integer::parseInt)
			                  .map(genresMap::get)
			                  .collect(Collectors.toSet());
		}
		Map<Integer, String> countriesMap = findCountriesMap();
		String[] countries = content.getParameterValuesByName(ParamName.PARAM_COUNTRIES_ARR);
		Set<String> countriesSet = null;
		if (!validator.checkStringNumberArray(countries)) {
			errorsMap.put(ErrorMessageKey.COUNTRIES_NOT_VALID, "");
			isParameterValid = false;
		} else {
			countriesSet = Arrays.stream(countries)
			                     .map(Integer::parseInt)
			                     .map(countriesMap::get)
			                     .collect(Collectors.toSet());
		}
		String strAge = content.getRequestParameter(ParamName.PARAM_AGE).strip();
		int age = 0;
		if (!validator.checkTwoDigitsString(strAge) || !validator.checkAge(Integer.parseInt(strAge))) {
			errorsMap.put(ErrorMessageKey.INVALID_AGE, strAge);
		} else {
			age = Integer.parseInt(strAge);
		}
		String strYear = content.getRequestParameter(ParamName.PARAM_YEAR).strip();
		int year = 0;
		if (!validator.checkYearString(strYear) || !validator.checkYear(Integer.parseInt(strYear))) {
			errorsMap.put(ErrorMessageKey.INVALID_YEAR, strYear);
		} else {
			year = Integer.parseInt(strYear);
		}
		String img = content.getRequestParameter(ParamName.PARAM_IMG);
		Film film;
		if (isParameterValid) {
			film = new Film();
			film.setId(filmId);
			film.setTitle(title);
			film.setGenres(genresSet);
			film.setCountries(countriesSet);
			film.setAge(age);
			film.setYear(year);
			film.setImg(img);
		} else {
			content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_FILM_MESSAGE, errorsMap);
			throw new ServiceException("Invalid parameter(s)");
		}
		try {
			filmOptional = filmDao.updateFilm(film);
		} catch (DaoException e) {
			throw new ServiceException("Exception while updating film.", e);
		}
		return filmOptional;
	}

	@Override
	public boolean deleteFilm(String strFilmId) throws ServiceException {
		boolean result;
		try {
			int filmId = Integer.parseInt(strFilmId);
			result = filmDao.deleteFilm(filmId);
		} catch (DaoException e) {
			throw new ServiceException("Exception while film deletion.", e);
		}
		return result;
	}

	@Override
	public Optional<Film> createFilm(SessionContent content) throws ServiceException {
		Optional<Film> filmOptional;
		boolean isParameterValid = true;
		Map<String, String> errorsMap = new LinkedHashMap<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_FILM_MESSAGE, null);
		String title = content.getRequestParameter(ParamName.PARAM_TITLE).strip();
		content.assignSessionAttribute(ParamName.PARAM_TITLE_DEFAULT, title);
		if (!validator.checkTitle(title)) {
			errorsMap.put(ErrorMessageKey.INVALID_TITLE, title);
			isParameterValid = false;
		}
		if (!checkTitle(title)) {
			errorsMap.put(ErrorMessageKey.TITLE_EXISTS, title);
			isParameterValid = false;
		}
		Map<Integer, String> genresMap = findGenresMap();
		String[] genres = content.getParameterValuesByName(ParamName.PARAM_GENRES_ARR);
		Set<String> genresSet = null;
		if (!validator.checkStringNumberArray(genres)) {
			errorsMap.put(ErrorMessageKey.GENRES_NOT_VALID, String.join(", ", genres));
			isParameterValid = false;
		} else {
			genresSet = Arrays.stream(genres)
			                  .map(Integer::parseInt)
			                  .map(genresMap::get)
			                  .collect(Collectors.toSet());
		}
		Map<Integer, String> countriesMap = findCountriesMap();
		String[] countries = content.getParameterValuesByName(ParamName.PARAM_COUNTRIES_ARR);
		Set<String> countriesSet = null;
		if (!validator.checkStringNumberArray(countries)) {
			errorsMap.put(ErrorMessageKey.COUNTRIES_NOT_VALID, String.join(", ", countries));
			isParameterValid = false;
		} else {
			countriesSet = Arrays.stream(countries)
			                     .map(Integer::parseInt)
			                     .map(countriesMap::get)
			                     .collect(Collectors.toSet());
		}
		String strAge = content.getRequestParameter(ParamName.PARAM_AGE).strip();
		int age = 0;
		if (!validator.checkTwoDigitsString(strAge) || !validator.checkAge(Integer.parseInt(strAge))) {
			errorsMap.put(ErrorMessageKey.INVALID_AGE, strAge);
		} else {
			age = Integer.parseInt(strAge);
		}
		String strYear = content.getRequestParameter(ParamName.PARAM_YEAR).strip();
		int year = 0;
		if (!validator.checkYearString(strYear) || !validator.checkYear(Integer.parseInt(strYear))) {
			errorsMap.put(ErrorMessageKey.INVALID_YEAR, strYear);
		} else {
			year = Integer.parseInt(strYear);
		}
		Film film;
		if (isParameterValid) {
			film = new Film();
			film.setTitle(title);
			film.setGenres(genresSet);
			film.setCountries(countriesSet);
			film.setAge(age);
			film.setYear(year);
		} else {
			content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_FILM_MESSAGE, errorsMap);
			throw new ServiceException("Invalid parameter(s)");
		}
		try {
			filmOptional = filmDao.createFilm(film);
		} catch (DaoException e) {
			throw new ServiceException("Exception while creating film.", e);
		}
		return filmOptional;
	}

	@Override
	public boolean checkTitle(String title, int filmId) throws ServiceException {
		boolean isTitleFree;
		try {
			isTitleFree = filmDao.checkTitle(title, filmId);
		} catch (DaoException e) {
			throw new ServiceException("Exception while title checking", e);
		}
		return isTitleFree;
	}

	@Override
	public boolean checkTitle(String title) throws ServiceException {
		boolean isTitleFree;
		try {
			isTitleFree = filmDao.checkTitle(title);
		} catch (DaoException e) {
			throw new ServiceException("Exception while title checking", e);
		}
		return isTitleFree;
	}

	@Override
	public boolean createGenre(SessionContent content) throws ServiceException {
		boolean result = false;
		boolean isParameterValid = true;
		Map<String, String> errorsMap = new LinkedHashMap<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_GENRE_MESSAGE, null);
		String genreName = content.getRequestParameter(ParamName.PARAM_GENRE).strip();
		if (!validator.checkGenre(genreName)) {
			isParameterValid = false;
			errorsMap.put(ErrorMessageKey.INVALID_NAME, genreName);
		}
		if (!checkGenre(genreName)) {
			isParameterValid = false;
			errorsMap.put(ErrorMessageKey.GENRE_EXISTS, genreName);
		}
		content.assignSessionAttribute(ParamName.PARAM_ERR_CREATE_GENRE_MESSAGE, errorsMap);
		if (isParameterValid) {
			try {
				result = filmDao.createGenre(genreName);
			} catch (DaoException e) {
				throw new ServiceException("Exception while genre creation.", e);
			}
		}
		return result;
	}

	@Override
	public boolean updateGenre(SessionContent content) throws ServiceException {
		boolean result = false;
		boolean isParameterValid = true;
		Map<String, String> errorsMap = new LinkedHashMap<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_GENRE_MESSAGE, null);
		String genreName = content.getRequestParameter(ParamName.PARAM_GENRE).strip();
		String strGenreId = content.getRequestParameter(ParamName.PARAM_GENRE_ID).strip();
		int genreId = 0;
		if (!validator.checkTwoDigitsString(strGenreId)) {
			isParameterValid = false;
			errorsMap.put(ErrorMessageKey.INVALID_ID, strGenreId);
		} else {
			genreId = Integer.parseInt(strGenreId);
		}
		if (!validator.checkGenre(genreName)) {
			isParameterValid = false;
			errorsMap.put(ErrorMessageKey.INVALID_NAME, genreName);
		}
		if (!checkGenre(genreName, genreId)) {
			isParameterValid = false;
			errorsMap.put(ErrorMessageKey.GENRE_EXISTS, genreName);
		}
		content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_GENRE_MESSAGE, errorsMap);
		if (isParameterValid) {
			try {
				result = filmDao.updateGenre(genreName, genreId);
			} catch (DaoException e) {
				throw new ServiceException("Exception while genre updating.", e);
			}
		} else {
			content.assignRequestAttribute(ParamName.PARAM_CINEMA, genreName);
			content.assignRequestAttribute(ParamName.PARAM_CINEMA_ID, genreId);
		}
		return result;
	}

	private boolean checkGenre(String genreName) throws ServiceException {
		boolean isGenreNameFree;
		try {
			isGenreNameFree = filmDao.checkGenre(genreName);
		} catch (DaoException e) {
			throw new ServiceException("Exception while genre name checking", e);
		}
		return isGenreNameFree;
	}

	private boolean checkGenre(String genreName, int genreId) throws ServiceException {
		boolean isGenreNameFree;
		try {
			isGenreNameFree = filmDao.checkGenre(genreName, genreId);
		} catch (DaoException e) {
			throw new ServiceException("Exception while genre checking", e);
		}
		return isGenreNameFree;
	}

	@Override
	public Optional<Film> updateImage(int filmId, String img) throws ServiceException {
		Optional<Film> filmOptional;
		try {
			filmOptional = filmDao.updateImage(filmId, img);
		} catch (DaoException e) {
			throw new ServiceException("Exception while image updating.", e);
		}
		return filmOptional;
	}
}

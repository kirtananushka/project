package by.tananushka.project.service;

import by.tananushka.project.bean.Film;
import by.tananushka.project.controller.SessionContent;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FilmService {

	Optional<Film> findFilmById(String filmId) throws ServiceException;

	List<Film> findAvailableFilms() throws ServiceException;

	List<Film> findActiveFilms() throws ServiceException;

	List<Film> findFilmsByGenre(String genre) throws ServiceException;

	List<Film> findActiveFilmsByGenre(String genre) throws ServiceException;

	List<Film> findFilmsByCountry(String country) throws ServiceException;

	List<Film> findActiveFilmsByCountry(String country) throws ServiceException;

	List<Film> findFilmsByAge(String strAge) throws ServiceException;

	List<Film> findActiveFilmsByAge(String strAge) throws ServiceException;

	List<Film> findFilmsByYear(String strAge) throws ServiceException;

	List<Film> findActiveFilmsByYear(String strAge) throws ServiceException;

	List<String> findGenres() throws ServiceException;

	Map<Integer, String> findGenresMap() throws ServiceException;

	List<String> findCountries() throws ServiceException;

	Map<Integer, String> findCountriesMap() throws ServiceException;

	List<Integer> findAges() throws ServiceException;

	Map<Integer, Integer> findAgesMap() throws ServiceException;

	List<Integer> findYears() throws ServiceException;

	Map<Integer, Integer> findYearsMap() throws ServiceException;

	Map<Integer, String> findTitlesMap() throws ServiceException;

	Optional<Film> updateFilm(SessionContent content) throws ServiceException;

	Optional<Film> createFilm(SessionContent content) throws ServiceException;

	boolean deleteFilm(String filmId) throws ServiceException;

	boolean checkTitle(String title, int filmId) throws ServiceException;

	boolean checkTitle(String title) throws ServiceException;

	Optional<Film> updateImage(int filmId, String img) throws ServiceException;

	boolean createGenre(SessionContent content) throws ServiceException;

	boolean updateGenre(SessionContent content) throws ServiceException;

}

package by.tananushka.project.service;

import by.tananushka.project.bean.Film;

import java.util.List;

public interface FilmService {

	List<Film> findAvailableFilms() throws ServiceException;

	List<Film> findFilmsByGenre(String genre) throws ServiceException;

	List<Film> findFilmsByCountry(String country) throws ServiceException;

	List<Film> findFilmsByAge(String strAge) throws ServiceException;

	List<Film> findFilmsByYear(String strAge) throws ServiceException;

	List<String> findGenres() throws ServiceException;

	List<String> findCountries() throws ServiceException;

	List<Integer> findAges() throws ServiceException;

	List<Integer> findYears() throws ServiceException;
}

package by.tananushka.project.dao;

import by.tananushka.project.bean.Film;

import java.util.List;

public interface FilmDao extends AbstractDao {

	List<Film> findAvailableFilms() throws DaoException;

	List<Film> findFilmsByGenre(String genre) throws DaoException;

	List<Film> findFilmsByCountry(String country) throws DaoException;

	List<Film> findFilmsByAge(int age) throws DaoException;

	List<Film> findFilmsByYear(int year) throws DaoException;

	List<String> findGenres() throws DaoException;

	List<String> findCountries() throws DaoException;

	List<Integer> findAges() throws DaoException;

	List<Integer> findYears() throws DaoException;
}

package by.tananushka.project.dao;

import by.tananushka.project.bean.Film;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FilmDao extends AbstractDao {

	Optional<Film> findFilmById(int filmId) throws DaoException;

	List<Film> findAvailableFilms() throws DaoException;

	List<Film> findActiveFilms() throws DaoException;

	List<Film> findFilmsByGenre(String genre) throws DaoException;

	List<Film> findActiveFilmsByGenre(String genre) throws DaoException;

	List<Film> findFilmsByGenre(int genre) throws DaoException;

	List<Film> findActiveFilmsByGenre(int genre) throws DaoException;

	List<Film> findFilmsByCountry(String country) throws DaoException;

	List<Film> findActiveFilmsByCountry(String country) throws DaoException;

	List<Film> findFilmsByCountry(int country) throws DaoException;

	List<Film> findActiveFilmsByCountry(int country) throws DaoException;

	List<Film> findFilmsByAge(int age) throws DaoException;

	List<Film> findActiveFilmsByAge(int age) throws DaoException;

	List<Film> findFilmsByYear(int year) throws DaoException;

	List<Film> findActiveFilmsByYear(int year) throws DaoException;

	List<String> findGenres() throws DaoException;

	Map<Integer, String> findGenresMap() throws DaoException;

	List<String> findCountries() throws DaoException;

	Map<Integer, String> findCountriesMap() throws DaoException;

	List<Integer> findAges() throws DaoException;

	Map<Integer, Integer> findAgesMap() throws DaoException;

	List<Integer> findYears() throws DaoException;

	Map<Integer, Integer> findYearsMap() throws DaoException;

	Map<Integer, String> findTitlesMap() throws DaoException;

	Optional<Film> updateFilm(Film film) throws DaoException;

	boolean deleteFilm(int filmId) throws DaoException;

	boolean checkTitle(String title, int filmId) throws DaoException;

	boolean checkTitle(String title) throws DaoException;

	Optional<Film> createFilm(Film film) throws DaoException;

	Optional<Film> updateImage(int filmId, String img) throws DaoException;

	boolean createGenre(String genreName) throws DaoException;

	boolean updateGenre(String genreName, int genreId) throws DaoException;

	boolean checkGenre(String cinemaName) throws DaoException;

	boolean checkGenre(String genreName, int genreId) throws DaoException;




}

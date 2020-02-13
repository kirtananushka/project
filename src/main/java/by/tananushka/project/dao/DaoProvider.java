package by.tananushka.project.dao;

import by.tananushka.project.dao.impl.ClientDaoImpl;
import by.tananushka.project.dao.impl.FilmDaoImpl;
import by.tananushka.project.dao.impl.UserDaoImpl;

public class DaoProvider {

	private static final DaoProvider instance = new DaoProvider();
	private final UserDao userDao = UserDaoImpl.getInstance();
	private final ClientDao clientDao = ClientDaoImpl.getInstance();
	private final FilmDao filmDao = FilmDaoImpl.getInstance();

	private DaoProvider() {
	}

	public static DaoProvider getInstance() {
		return instance;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public ClientDao getClientDao() {
		return clientDao;
	}

	public FilmDao getFilmDao() {
		return filmDao;
	}
}

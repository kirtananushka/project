package by.tananushka.project.dao;

import by.tananushka.project.dao.impl.AdminDaoImpl;
import by.tananushka.project.dao.impl.ClientDaoImpl;
import by.tananushka.project.dao.impl.FilmDaoImpl;
import by.tananushka.project.dao.impl.ManagerDaoImpl;
import by.tananushka.project.dao.impl.ShowDaoImpl;
import by.tananushka.project.dao.impl.UserDaoImpl;

public class DaoProvider {

	private static final DaoProvider instance = new DaoProvider();
	private final UserDao userDao = UserDaoImpl.getInstance();
	private final ClientDao clientDao = ClientDaoImpl.getInstance();
	private final ManagerDao managerDao = ManagerDaoImpl.getInstance();
	private final AdminDao adminDao = AdminDaoImpl.getInstance();
	private final FilmDao filmDao = FilmDaoImpl.getInstance();
	private final ShowDao showDao = ShowDaoImpl.getInstance();

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

	public ManagerDao getManagerDao() {
		return managerDao;
	}

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public FilmDao getFilmDao() {
		return filmDao;
	}

	public ShowDao getShowDao() {
		return showDao;
	}
}

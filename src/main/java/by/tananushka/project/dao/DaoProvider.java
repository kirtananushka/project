package by.tananushka.project.dao;

import by.tananushka.project.dao.impl.AdminDaoImpl;
import by.tananushka.project.dao.impl.ClientDaoImpl;
import by.tananushka.project.dao.impl.FilmDaoImpl;
import by.tananushka.project.dao.impl.ManagerDaoImpl;
import by.tananushka.project.dao.impl.OrderDaoImpl;
import by.tananushka.project.dao.impl.ShowDaoImpl;
import by.tananushka.project.dao.impl.UserDaoImpl;

/**
 * The type Dao provider.
 */
public class DaoProvider {

	private static final DaoProvider instance = new DaoProvider();
	private final UserDao userDao = UserDaoImpl.getInstance();
	private final ClientDao clientDao = ClientDaoImpl.getInstance();
	private final ManagerDao managerDao = ManagerDaoImpl.getInstance();
	private final AdminDao adminDao = AdminDaoImpl.getInstance();
	private final FilmDao filmDao = FilmDaoImpl.getInstance();
	private final ShowDao showDao = ShowDaoImpl.getInstance();
	private final OrderDao orderDao = OrderDaoImpl.getInstance();

	private DaoProvider() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static DaoProvider getInstance() {
		return instance;
	}

	/**
	 * Gets user dao.
	 *
	 * @return the user dao
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * Gets client dao.
	 *
	 * @return the client dao
	 */
	public ClientDao getClientDao() {
		return clientDao;
	}

	/**
	 * Gets manager dao.
	 *
	 * @return the manager dao
	 */
	public ManagerDao getManagerDao() {
		return managerDao;
	}

	/**
	 * Gets admin dao.
	 *
	 * @return the admin dao
	 */
	public AdminDao getAdminDao() {
		return adminDao;
	}

	/**
	 * Gets film dao.
	 *
	 * @return the film dao
	 */
	public FilmDao getFilmDao() {
		return filmDao;
	}

	/**
	 * Gets show dao.
	 *
	 * @return the show dao
	 */
	public ShowDao getShowDao() {
		return showDao;
	}

	/**
	 * Gets order dao.
	 *
	 * @return the order dao
	 */
	public OrderDao getOrderDao() {
		return orderDao;
	}
}

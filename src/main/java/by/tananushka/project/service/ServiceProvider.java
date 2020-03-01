package by.tananushka.project.service;

import by.tananushka.project.service.impl.ClientServiceImpl;
import by.tananushka.project.service.impl.FilmServiceImpl;
import by.tananushka.project.service.impl.ManagerServiceImpl;
import by.tananushka.project.service.impl.OrderServiceImpl;
import by.tananushka.project.service.impl.ShowServiceImpl;
import by.tananushka.project.service.impl.UserServiceImpl;

/**
 * The type Service provider.
 */
public class ServiceProvider {

	private static final ServiceProvider instance = new ServiceProvider();
	private final UserService userService = UserServiceImpl.getInstance();
	private final ClientService clientService = ClientServiceImpl.getInstance();
	private final ManagerService managerService = ManagerServiceImpl.getInstance();
	private final FilmService filmService = FilmServiceImpl.getInstance();
	private final ShowService showService = ShowServiceImpl.getInstance();
	private final OrderService orderService = OrderServiceImpl.getInstance();

	private ServiceProvider() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static ServiceProvider getInstance() {
		return instance;
	}

	/**
	 * Gets user service.
	 *
	 * @return the user service
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Gets client service.
	 *
	 * @return the client service
	 */
	public ClientService getClientService() {
		return clientService;
	}

	/**
	 * Gets manager service.
	 *
	 * @return the manager service
	 */
	public ManagerService getManagerService() {
		return managerService;
	}

	/**
	 * Gets film service.
	 *
	 * @return the film service
	 */
	public FilmService getFilmService() {
		return filmService;
	}

	/**
	 * Gets show service.
	 *
	 * @return the show service
	 */
	public ShowService getShowService() {
		return showService;
	}

	/**
	 * Gets order service.
	 *
	 * @return the order service
	 */
	public OrderService getOrderService() {
		return orderService;
	}
}

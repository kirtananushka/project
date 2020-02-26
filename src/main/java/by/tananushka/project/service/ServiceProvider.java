package by.tananushka.project.service;

import by.tananushka.project.service.impl.ClientServiceImpl;
import by.tananushka.project.service.impl.FilmServiceImpl;
import by.tananushka.project.service.impl.ManagerServiceImpl;
import by.tananushka.project.service.impl.ShowServiceImpl;
import by.tananushka.project.service.impl.UserServiceImpl;

public class ServiceProvider {

	private static final ServiceProvider instance = new ServiceProvider();
	private final UserService userService = UserServiceImpl.getInstance();
	private final ClientService clientService = ClientServiceImpl.getInstance();
	private final ManagerService managerService = ManagerServiceImpl.getInstance();
	private final FilmService filmService = FilmServiceImpl.getInstance();
	private final ShowService showService = ShowServiceImpl.getInstance();

	private ServiceProvider() {
	}

	public static ServiceProvider getInstance() {
		return instance;
	}

	public UserService getUserService() {
		return userService;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public ManagerService getManagerService() {
		return managerService;
	}

	public FilmService getFilmService() {
		return filmService;
	}

	public ShowService getShowService() {
		return showService;
	}
}

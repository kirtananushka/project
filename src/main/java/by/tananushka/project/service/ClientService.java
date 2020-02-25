package by.tananushka.project.service;

import by.tananushka.project.bean.Client;
import by.tananushka.project.controller.SessionContent;

import java.util.List;

public interface ClientService {

	Client createClient(SessionContent content) throws ServiceException;

	List<Client> findActiveClients() throws ServiceException;

	boolean checkLogin(String login) throws ServiceException;
}

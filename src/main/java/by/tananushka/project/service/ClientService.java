package by.tananushka.project.service;

import by.tananushka.project.bean.Client;
import by.tananushka.project.controller.SessionContent;

import java.util.List;
import java.util.Optional;

public interface ClientService {

	Optional<Client> createClient(SessionContent content) throws ServiceException;

	Optional<Client> updateClient(SessionContent content) throws ServiceException;

	List<Client> findActiveClients() throws ServiceException;

	List<Client> findAllClients() throws ServiceException;

	Optional<Client> findClientById(String strClientId) throws ServiceException;

	Optional<Client> findClientById(int clientId) throws ServiceException;

	boolean checkLogin(String login) throws ServiceException;
}

package by.tananushka.project.service;

import by.tananushka.project.bean.Client;
import by.tananushka.project.controller.SessionContent;

import java.util.List;
import java.util.Optional;

/**
 * The interface Client service.
 */
public interface ClientService {

	/**
	 * Create client optional.
	 *
	 * @param content the content
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Client> createClient(SessionContent content) throws ServiceException;

	/**
	 * Update client optional.
	 *
	 * @param content the content
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Client> updateClient(SessionContent content) throws ServiceException;

	/**
	 * Find active clients list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Client> findActiveClients() throws ServiceException;

	/**
	 * Find all clients list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<Client> findAllClients() throws ServiceException;

	/**
	 * Find client by id optional.
	 *
	 * @param strClientId the str client id
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Client> findClientById(String strClientId) throws ServiceException;

	/**
	 * Find client by id optional.
	 *
	 * @param clientId the client id
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<Client> findClientById(int clientId) throws ServiceException;

	/**
	 * Check login boolean.
	 *
	 * @param login the login
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean checkLogin(String login) throws ServiceException;
}

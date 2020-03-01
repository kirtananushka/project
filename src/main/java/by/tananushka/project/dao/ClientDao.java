package by.tananushka.project.dao;

import by.tananushka.project.bean.Client;

import java.util.List;
import java.util.Optional;

/**
 * The interface Client dao.
 */
public interface ClientDao extends AbstractDao {

	/**
	 * Create client optional.
	 *
	 * @param client the client
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Client> createClient(Client client) throws DaoException;

	/**
	 * Update client optional.
	 *
	 * @param client the client
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Client> updateClient(Client client) throws DaoException;

	/**
	 * Find active clients list.
	 *
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Client> findActiveClients() throws DaoException;

	/**
	 * Find all clients list.
	 *
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<Client> findAllClients() throws DaoException;

	/**
	 * Find client by id optional.
	 *
	 * @param clientId the client id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<Client> findClientById(int clientId) throws DaoException;

	/**
	 * Check login boolean.
	 *
	 * @param login the login
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean checkLogin(String login) throws DaoException;
}

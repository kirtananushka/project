package by.tananushka.project.dao;

import by.tananushka.project.bean.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDao extends AbstractDao {

	Optional<Client> createClient(Client client) throws DaoException;

	Optional<Client> updateClient(Client client) throws DaoException;

	List<Client> findActiveClients() throws DaoException;

	List<Client> findAllClients() throws DaoException;

	Optional<Client> findClientById(int clientId) throws DaoException;

	boolean checkLogin(String login) throws DaoException;
}

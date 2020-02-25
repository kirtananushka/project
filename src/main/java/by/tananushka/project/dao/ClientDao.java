package by.tananushka.project.dao;

import by.tananushka.project.bean.Client;

import java.util.List;

public interface ClientDao extends AbstractDao {

	Client createClient(Client client) throws DaoException;

	List<Client> findActiveClients() throws DaoException;

	boolean checkLogin(String login) throws DaoException;
}

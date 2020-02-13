package by.tananushka.project.dao;

import by.tananushka.project.bean.Client;

public interface ClientDao extends AbstractDao {

	Client createClient(Client client) throws DaoException;

	boolean checkLogin(String login) throws DaoException;
}

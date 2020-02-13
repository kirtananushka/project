package by.tananushka.project.dao.impl;

import by.tananushka.project.bean.Client;
import by.tananushka.project.dao.ClientDao;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The type Client dao.
 */
public class ClientDaoImpl implements ClientDao {

	private static final String INSERT_USER =
					"INSERT INTO users (user_login, user_password, user_role, user_active) VALUES (?, ?, ?, ?)";
	private static final String INSERT_CLIENT =
					"INSERT INTO clients (client_id, client_name, client_surname, client_phone, client_email) VALUES (?, ?, ?, ?, ?)";
	private static final String CHECK_LOGIN = "SELECT user_login FROM users WHERE user_login = ?";
	private static ClientDao clientDao = new ClientDaoImpl();
	private static Logger log = LogManager.getLogger();

	private ClientDaoImpl() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static ClientDao getInstance() {
		return clientDao;
	}

	@Override
	public Client createClient(Client client) throws DaoException {
		String password = client.getPassword();
		String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		ResultSet resultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement userStatement = connection
						     .prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
		     PreparedStatement clientStatement = connection.prepareStatement(INSERT_CLIENT)) {
			try {
				connection.setAutoCommit(false);
				userStatement.setString(1, client.getLogin());
				userStatement.setString(2, encodedPassword);
				userStatement.setString(3, client.getRole().name());
				userStatement.setBoolean(4, client.isActive());
				userStatement.execute();
				resultSet = userStatement.getGeneratedKeys();
				if (resultSet.next()) {
					int clientId = resultSet.getInt(1);
					client.setId(clientId);
				}
				clientStatement.setInt(1, client.getId());
				clientStatement.setString(2, client.getName());
				clientStatement.setString(3, client.getSurname());
				clientStatement.setString(4, client.getPhone());
				clientStatement.setString(5, client.getEmail());
				clientStatement.execute();
				connection.commit();
				log.debug("A new client was created: {}.", client);
			} catch (SQLException e) {
				connection.rollback();
				throw new DaoException("Transaction failed; not committed.", e);
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while registration.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return client;
	}

	@Override
	public boolean checkLogin(String login) throws DaoException {
		boolean isLoginFree = false;
		ResultSet resultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement loginStatement = connection.prepareStatement(CHECK_LOGIN)) {
			loginStatement.setString(1, login);
			resultSet = loginStatement.executeQuery();
			if (!resultSet.first()) {
				isLoginFree = true;
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while checking login.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return isLoginFree;
	}
}

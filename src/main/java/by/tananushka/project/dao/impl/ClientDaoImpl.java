package by.tananushka.project.dao.impl;

import by.tananushka.project.bean.Client;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.dao.ClientDao;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.SqlColumnsName;
import by.tananushka.project.pool.ConnectionPool;
import by.tananushka.project.util.PasswordUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

/**
 * The type Client dao.
 */
public class ClientDaoImpl implements ClientDao {

	private static final String INSERT_USER =
					"INSERT INTO users (user_login, user_password, user_email, user_role, user_active)\n"
									+ "VALUES (?, ?, ?, ?, ?)";
	private static final String INSERT_CLIENT =
					"INSERT INTO clients (client_id, client_name, client_surname,\n"
									+ "client_phone) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_CLIENT_AS_USER =
					"UPDATE users SET user_email = ?, user_active = ? WHERE user_id = ?;";
	private static final String UPDATE_CLIENT =
					"UPDATE clients SET client_name = ?, client_surname = ?,\n"
									+ "client_phone = ? WHERE client_id = ?;";
	private static final String FIND_ACTIVE_CLIENTS =
					"SELECT client_id, user_login, client_name, client_surname, client_phone,\n"
									+ "user_email, user_verification, user_active, user_registration_date,\n"
									+ "user_role FROM clients INNER JOIN users ON client_id = user_id\n"
									+ "WHERE user_active = true ORDER BY user_login;";
	private static final String FIND_ALL_CLIENTS =
					"SELECT client_id, user_login, client_name, client_surname, client_phone,\n"
									+ "user_email, user_verification, user_active, user_registration_date,\n"
									+ "user_role FROM clients INNER JOIN users ON client_id = user_id\n"
									+ "ORDER BY user_login;";
	private static final String FIND_CLIENT_BY_ID =
					"SELECT client_id, user_login, client_name, client_surname, client_phone,\n"
									+ "user_email, user_verification, user_active, user_registration_date, \n"
									+ "user_role\n"
									+ "FROM clients INNER JOIN users ON client_id = user_id\n"
									+ "WHERE client_id = ?;";
	private static final String CHECK_LOGIN = "SELECT user_login FROM users\n"
					+ "WHERE user_login = ?";
	private static ClientDao clientDao = new ClientDaoImpl();
	private static Logger log = LogManager.getLogger();
	private final Calendar timezone = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"));
	private PasswordUtility passwordUtility = PasswordUtility.getInstance();

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
	public Optional<Client> createClient(Client client) throws DaoException {
		Optional<Client> clientOptional;
		String password = client.getPassword();
		String encodedPassword = passwordUtility.encodePassword(password);
		ResultSet resultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement userStatement = connection
						     .prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
		     PreparedStatement clientStatement = connection.prepareStatement(INSERT_CLIENT)) {
			try {
				connection.setAutoCommit(false);
				userStatement.setString(1, client.getLogin());
				userStatement.setString(2, encodedPassword);
				userStatement.setString(3, client.getEmail());
				userStatement.setString(4, client.getRole().name());
				userStatement.setBoolean(5, client.isActive());
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
		clientOptional = findClientById(client.getId());
		return clientOptional;
	}

	@Override
	public Optional<Client> updateClient(Client client) throws DaoException {
		Optional<Client> clientOptional;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement userStatement = connection
						     .prepareStatement(UPDATE_CLIENT_AS_USER);
		     PreparedStatement clientStatement = connection.prepareStatement(UPDATE_CLIENT)) {
			try {
				connection.setAutoCommit(false);
				userStatement.setString(1, client.getEmail());
				userStatement.setBoolean(2, client.isActive());
				userStatement.setInt(3, client.getId());
				userStatement.execute();
				clientStatement.setString(1, client.getName());
				clientStatement.setString(2, client.getSurname());
				clientStatement.setString(3, client.getPhone());
				clientStatement.setInt(4, client.getId());
				clientStatement.execute();
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				throw new DaoException("Transaction failed; not committed.", e);
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while client updating.", e);
		}
		clientOptional = findClientById(client.getId());
		return clientOptional;
	}

	@Override
	public List<Client> findActiveClients() throws DaoException {
		ResultSet resultSet = null;
		List<Client> clientList = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     Statement clientStatement = connection.createStatement()) {
			resultSet = clientStatement.executeQuery(FIND_ACTIVE_CLIENTS);
			while (resultSet.next()) {
				clientList.add(clientSetter(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("Exception while getting active clients.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return clientList;
	}

	@Override
	public List<Client> findAllClients() throws DaoException {
		ResultSet resultSet = null;
		List<Client> clientList = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     Statement clientStatement = connection.createStatement()) {
			resultSet = clientStatement.executeQuery(FIND_ALL_CLIENTS);
			while (resultSet.next()) {
				clientList.add(clientSetter(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("Exception while getting all clients.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return clientList;
	}

	@Override
	public Optional<Client> findClientById(int clientId) throws DaoException {
		ResultSet resultSet = null;
		Optional<Client> clientOptional = Optional.empty();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement clientStatement = connection.prepareStatement(FIND_CLIENT_BY_ID)) {
			clientStatement.setInt(1, clientId);
			resultSet = clientStatement.executeQuery();
			if (resultSet.first()) {
				clientOptional = Optional.of(clientSetter(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("Exception while getting client by Id.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return clientOptional;
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

	private Client clientSetter(ResultSet resultSet) throws SQLException {
		Client client = new Client();
		client.setId(resultSet.getInt(SqlColumnsName.CLIENT_ID));
		client.setLogin(resultSet.getString(SqlColumnsName.USER_LOGIN));
		client.setName(resultSet.getString(SqlColumnsName.CLIENT_NAME));
		client.setSurname(resultSet.getString(SqlColumnsName.CLIENT_SURNAME));
		client.setPhone(resultSet.getString(SqlColumnsName.CLIENT_PHONE));
		client.setEmail(resultSet.getString(SqlColumnsName.USER_EMAIL));
		client.setVerified(resultSet.getBoolean(SqlColumnsName.USER_VERIFIED));
		client.setActive(resultSet.getBoolean(SqlColumnsName.USER_ACTIVE));
		client.setRegistrationDate(
						resultSet.getTimestamp(SqlColumnsName.USER_REGISTRATION_DATE, timezone)
						         .toLocalDateTime());
		client.setRole(UserRole.valueOf(resultSet.getString(SqlColumnsName.USER_ROLE)));
		return client;
	}
}

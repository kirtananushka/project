package by.tananushka.project.dao.impl;

import by.tananushka.project.bean.Client;
import by.tananushka.project.bean.Manager;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.ManagerDao;
import by.tananushka.project.dao.SqlColumnsName;
import by.tananushka.project.pool.ConnectionPool;
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
 * The type Manager dao.
 */
public class ManagerDaoImpl implements ManagerDao {

	private static final String UPDATE_MANAGER_AS_USER =
					"UPDATE users SET user_email = ?, user_active = ? WHERE user_id = ?;";
	private static final String UPDATE_MANAGER =
					"UPDATE managers SET manager_name = ?, manager_surname = ?,\n"
									+ "manager_phone = ? WHERE manager_id = ?;";
	private static final String SET_MANAGER =
					"UPDATE users SET user_role = 'MANAGER' WHERE user_id = ?;";
	private static final String INSERT_MANAGER =
					"INSERT INTO managers (manager_id, manager_name, manager_surname,\n"
									+ "manager_phone) VALUES (?, ?, ?, ?);";
	private static final String DELETE_CLIENT = "DELETE FROM clients WHERE client_id = ?;";
	private static final String FIND_ACTIVE_MANAGERS =
					"SELECT manager_id, user_login, manager_name, manager_surname, manager_phone,\n"
									+ "user_email, user_verification, user_active, user_registration_date,\n"
									+ "user_role FROM managers INNER JOIN users ON manager_id = user_id\n"
									+ "WHERE user_active = true ORDER BY user_login;";
	private static final String FIND_ALL_MANAGERS =
					"SELECT manager_id, user_login, manager_name, manager_surname, manager_phone,\n"
									+ "user_email, user_verification, user_active, user_registration_date,\n"
									+ "user_role FROM managers INNER JOIN users ON manager_id = user_id\n"
									+ "ORDER BY user_login;";
	private static final String FIND_MANAGER_BY_ID =
					"SELECT manager_id, user_login, manager_name, manager_surname, manager_phone,\n"
									+ "user_email, user_verification, user_active, user_registration_date, \n"
									+ "user_role\n"
									+ "FROM managers INNER JOIN users ON manager_id = user_id\n"
									+ "WHERE manager_id = ?;";
	private static ManagerDao managerDao = new ManagerDaoImpl();
	private static Logger log = LogManager.getLogger();
	private final Calendar timezone = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"));

	private ManagerDaoImpl() {
	}

	public static ManagerDao getInstance() {
		return managerDao;
	}

	@Override
	public Optional<Manager> updateManager(Manager manager) throws DaoException {
		Optional<Manager> managerOptional;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement userStatement = connection
						     .prepareStatement(UPDATE_MANAGER_AS_USER);
		     PreparedStatement managerStatement = connection.prepareStatement(UPDATE_MANAGER)) {
			try {
				connection.setAutoCommit(false);
				userStatement.setString(1, manager.getEmail());
				userStatement.setBoolean(2, manager.isActive());
				userStatement.setInt(3, manager.getId());
				userStatement.execute();
				managerStatement.setString(1, manager.getName());
				managerStatement.setString(2, manager.getSurname());
				managerStatement.setString(3, manager.getPhone());
				managerStatement.setInt(4, manager.getId());
				managerStatement.execute();
				connection.commit();
			} catch (SQLException e) {
				connection.rollback();
				throw new DaoException("Transaction failed; not committed.", e);
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while manager updating.", e);
		}
		managerOptional = findManagerById(manager.getId());
		return managerOptional;
	}

	@Override
	public List<Manager> findActiveManagers() throws DaoException {
		ResultSet resultSet = null;
		List<Manager> managerList = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     Statement managerStatement = connection.createStatement()) {
			resultSet = managerStatement.executeQuery(FIND_ACTIVE_MANAGERS);
			while (resultSet.next()) {
				managerList.add(managerSetter(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("Exception while getting active managers.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return managerList;
	}

	@Override
	public List<Manager> findAllManagers() throws DaoException {
		ResultSet resultSet = null;
		List<Manager> managerList = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     Statement managerStatement = connection.createStatement()) {
			resultSet = managerStatement.executeQuery(FIND_ALL_MANAGERS);
			while (resultSet.next()) {
				managerList.add(managerSetter(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("Exception while getting all managers.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return managerList;
	}

	@Override
	public Optional<Manager> findManagerById(int managerId) throws DaoException {
		ResultSet resultSet = null;
		Optional<Manager> managerOptional = Optional.empty();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement managerStatement = connection.prepareStatement(FIND_MANAGER_BY_ID)) {
			managerStatement.setInt(1, managerId);
			resultSet = managerStatement.executeQuery();
			if (resultSet.first()) {
				managerOptional = Optional.of(managerSetter(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("Exception while getting manager by Id.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return managerOptional;
	}

	@Override
	public Optional<Manager> appointManager(int clientId) throws DaoException {
		Optional<Manager> managerOptional;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement insertStatement = connection.prepareStatement(INSERT_MANAGER);
		     PreparedStatement setRoleStatement = connection.prepareStatement(SET_MANAGER);
		     PreparedStatement deleteStatement = connection.prepareStatement(DELETE_CLIENT)) {
			try {
				connection.setAutoCommit(false);
				Optional<Client> clientOptional = ClientDaoImpl.getInstance().findClientById(clientId);
				if (clientOptional.isPresent()) {
					Client client = clientOptional.get();
					insertStatement.setInt(1, clientId);
					insertStatement.setString(2, client.getName());
					insertStatement.setString(3, client.getSurname());
					insertStatement.setString(4, client.getPhone());
					insertStatement.execute();
					setRoleStatement.setInt(1, clientId);
					setRoleStatement.execute();
					deleteStatement.setInt(1, clientId);
					deleteStatement.execute();
					connection.commit();
				}
			} catch (SQLException e) {
				connection.rollback();
				throw new DaoException("Transaction failed; not committed.", e);
			} finally {
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while manager appointment.", e);
		}
		managerOptional = findManagerById(clientId);
		return managerOptional;
	}

	private Manager managerSetter(ResultSet resultSet) throws SQLException {
		Manager manager = new Manager();
		manager.setId(resultSet.getInt(SqlColumnsName.MANAGER_ID));
		manager.setLogin(resultSet.getString(SqlColumnsName.USER_LOGIN));
		manager.setName(resultSet.getString(SqlColumnsName.MANAGER_NAME));
		manager.setSurname(resultSet.getString(SqlColumnsName.MANAGER_SURNAME));
		manager.setPhone(resultSet.getString(SqlColumnsName.MANAGER_PHONE));
		manager.setEmail(resultSet.getString(SqlColumnsName.USER_EMAIL));
		manager.setVerified(resultSet.getBoolean(SqlColumnsName.USER_VERIFIED));
		manager.setActive(resultSet.getBoolean(SqlColumnsName.USER_ACTIVE));
		manager.setRegistrationDate(
						resultSet.getTimestamp(SqlColumnsName.USER_REGISTRATION_DATE, timezone)
						         .toLocalDateTime());
		manager.setRole(UserRole.valueOf(resultSet.getString(SqlColumnsName.USER_ROLE)));
		return manager;
	}
}

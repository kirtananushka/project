package by.tananushka.project.dao.impl;

import by.tananushka.project.bean.Admin;
import by.tananushka.project.bean.User;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.SqlColumnsName;
import by.tananushka.project.dao.UserDao;
import by.tananushka.project.pool.ConnectionPool;
import by.tananushka.project.util.PasswordUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type User dao.
 */
public class UserDaoImpl implements UserDao {

	private static final String FIND_USER_BY_LOGIN =
					"SELECT user_id, user_login, user_password, user_role\n"
									+ "FROM users WHERE user_login = ? AND user_active = true;";
	private static final String FIND_ROLE_BY_ID =
					"SELECT user_role FROM users WHERE user_id = ?;";
	private static final String USER_VERIFICATION =
					"UPDATE users SET user_active = true, user_verification = true\n"
									+ "WHERE user_id = ? AND user_verification = false\n"
									+ "AND user_active =  false;";
	private static final String SET_NEW_PASS =
					"UPDATE users SET user_password = ? WHERE user_login = ?;";
	private static final String FIND_LOGIN_AND_EMAIL =
					"SELECT * FROM users WHERE user_login = ? AND user_email = ?\n"
									+ "AND user_active = true;";
	private static final String SET_USER_INACTIVE =
					"UPDATE users SET user_active = false WHERE user_id = ?;";
	private static Logger log = LogManager.getLogger();
	private static UserDao userDao = new UserDaoImpl();
	private PasswordUtility passwordUtility = PasswordUtility.getInstance();

	private UserDaoImpl() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static UserDao getInstance() {
		return userDao;
	}

	@Override
	public Optional<User> authentication(String login, String password) throws DaoException {
		Optional<User> userOptional = Optional.empty();
		ResultSet resultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LOGIN)) {
			statement.setString(1, login);
			resultSet = statement.executeQuery();
			if (resultSet.first()) {
				String encryptedPassword = resultSet.getString(SqlColumnsName.USER_PASSWORD);
				if (passwordUtility.comparePasswords(password, encryptedPassword)) {
					User user = new User();
					user.setId(resultSet.getInt(SqlColumnsName.USER_ID));
					user.setLogin(resultSet.getString(SqlColumnsName.USER_LOGIN));
					user.setRole(UserRole.valueOf(resultSet.getString(SqlColumnsName.USER_ROLE)));
					user.setActive(true);
					userOptional = Optional.of(user);
				}
			}
			log.debug("User '{}' was found by the login '{}' and pass '{}'", userOptional.orElse(null),
							login, password);
		} catch (SQLException e) {
			log.error("Exception while authentication.");
			throw new DaoException("Exception while authentication.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return userOptional;
	}

	@Override
	public List<List<? extends User>> findAllUsers() throws DaoException {
		List<List<? extends User>> usersList = new ArrayList<>();
		try {
			List<Admin> adminList = new ArrayList<>();
			Optional<Admin> adminOptional = AdminDaoImpl.getInstance().findAdmin();
			if (adminOptional.isPresent()) {
				adminList.add(adminOptional.get());
				usersList.add(adminList);
			}
			usersList.add(ManagerDaoImpl.getInstance().findAllManagers());
			usersList.add(ClientDaoImpl.getInstance().findAllClients());
		} catch (DaoException e) {
			log.error("Exception while getting all users.");
			throw new DaoException("Exception while getting all users.", e);
		}
		return usersList;
	}

	@Override
	public Optional<UserRole> findRoleById(int userId) throws DaoException {
		Optional<UserRole> userRole = Optional.empty();
		ResultSet resultSet = null;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement statement = connection.prepareStatement(FIND_ROLE_BY_ID)) {
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();
			if (resultSet.first()) {
				UserRole role = UserRole.valueOf(resultSet.getString(SqlColumnsName.USER_ROLE));
				userRole = Optional.of(role);
			}
		} catch (SQLException e) {
			log.error("Exception while finding user's role by ID: {}.", userId);
			throw new DaoException("Exception while finding user's role by ID.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return userRole;
	}

	@Override
	public boolean emailConfirmation(int userId) throws DaoException {
		boolean isConfirmationSuccessful;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement statement = connection.prepareStatement(USER_VERIFICATION)) {
			statement.setInt(1, userId);
			isConfirmationSuccessful = statement.executeUpdate() == 1;
			log.debug("User was verified by ID {}", userId);
		} catch (SQLException e) {
			log.error("Exception while email confirmation by ID: {}.", userId);
			throw new DaoException("Exception while email confirmation by ID.", e);
		}
		return isConfirmationSuccessful;
	}

	@Override
	public boolean checkLoginAndEmail(String login, String email) throws DaoException {
		ResultSet resultSet = null;
		boolean isConfirmed;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement statement = connection.prepareStatement(FIND_LOGIN_AND_EMAIL)) {
			statement.setString(1, login);
			statement.setString(2, email);
			resultSet = statement.executeQuery();
			isConfirmed = resultSet.first();
		} catch (SQLException e) {
			log.error("Exception while finding login and email: {}.", login);
			throw new DaoException("Exception while finding login and email.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return isConfirmed;
	}

	@Override
	public boolean setNewPassword(String login, String password) throws DaoException {
		boolean isSet;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement statement = connection.prepareStatement(SET_NEW_PASS)) {
			String encodedPassword = passwordUtility.encodePassword(password);
			statement.setString(1, encodedPassword);
			statement.setString(2, login);
			statement.execute();
			isSet = true;
		} catch (SQLException e) {
			log.error("Exception while updating password.");
			throw new DaoException("Exception while updating password.", e);
		}
		return isSet;
	}

	@Override
	public boolean deleteUser(int userId) throws DaoException {
		boolean isDeleted;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement statement = connection.prepareStatement(SET_USER_INACTIVE)) {
			statement.setInt(1, userId);
			statement.execute();
			isDeleted = true;
		} catch (SQLException e) {
			log.error("Exception while deleting user.");
			throw new DaoException("Exception while deleting user.", e);
		}
		return isDeleted;
	}
}

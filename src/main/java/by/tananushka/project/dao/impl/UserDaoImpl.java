package by.tananushka.project.dao.impl;

import by.tananushka.project.bean.User;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.SqlColumnsName;
import by.tananushka.project.dao.UserDao;
import by.tananushka.project.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

	private static final String FIND_USER_BY_LOGIN =
					"SELECT user_id, user_login, user_password, user_role"
									+ " FROM users WHERE user_login = ? AND user_active = true";
	private static final String USER_VERIFICATION =
					"UPDATE users SET user_active = true, user_verification = true"
									+ " WHERE user_id = ? AND user_verification = false" + " AND user_active = false";
	private static Logger log = LogManager.getLogger();
	private static UserDao userDao = new UserDaoImpl();

	private UserDaoImpl() {
	}

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
				if (BCrypt.checkpw(password, encryptedPassword)) {
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
			throw new DaoException("Exception while authentication.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return userOptional;
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
			throw new DaoException("Exception while email confirmation", e);
		}
		return isConfirmationSuccessful;
	}
}

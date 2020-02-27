package by.tananushka.project.dao.impl;

import by.tananushka.project.bean.Admin;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.dao.AdminDao;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.SqlColumnsName;
import by.tananushka.project.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

public class AdminDaoImpl implements AdminDao {

	private static final String UPDATE_ADMIN =
					"UPDATE admins SET admin_name = ?, admin_surname = ?,\n"
									+ "admin_email = ? WHERE admin_id = ?;";
	private static final String FIND_ADMIN =
					"SELECT admin_id, user_login, admin_name, admin_surname,\n"
									+ "admin_email, user_verification, user_active, user_registration_date,\n"
									+ "user_role FROM admins INNER JOIN users ON admin_id = user_id\n"
									+ "WHERE user_role = 'ADMIN';";
	private static AdminDao adminDao = new AdminDaoImpl();
	private static Logger log = LogManager.getLogger();
	private final Calendar timezone = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"));

	private AdminDaoImpl() {
	}

	public static AdminDao getInstance() {
		return adminDao;
	}

	@Override
	public Optional<Admin> updateAdmin(Admin admin) throws DaoException {
		Optional<Admin> adminOptional;
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     PreparedStatement adminStatement = connection.prepareStatement(UPDATE_ADMIN)) {
			try {
				adminStatement.setString(1, admin.getName());
				adminStatement.setString(2, admin.getSurname());
				adminStatement.setString(3, admin.getEmail());
				adminStatement.setInt(4, admin.getId());
				adminStatement.execute();
			} catch (SQLException e) {
				throw new DaoException("Admin updating failed.", e);
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while admin updating.", e);
		}
		adminOptional = findAdmin();
		return adminOptional;
	}

	@Override
	public Optional<Admin> findAdmin() throws DaoException {
		ResultSet resultSet = null;
		Optional<Admin> adminOptional = Optional.empty();
		try (Connection connection = ConnectionPool.getInstance().takeConnection();
		     Statement statement = connection.createStatement()) {
			resultSet = statement.executeQuery(FIND_ADMIN);
			if (resultSet.first()) {
				adminOptional = Optional.of(adminSetter(resultSet));
			}
		} catch (SQLException e) {
			throw new DaoException("SQL exception while admin finding.", e);
		} finally {
			closeResultSet(resultSet);
		}
		return adminOptional;
	}

	private Admin adminSetter(ResultSet resultSet) throws SQLException {
		Admin admin = new Admin();
		admin.setId(resultSet.getInt(SqlColumnsName.ADMIN_ID));
		admin.setLogin(resultSet.getString(SqlColumnsName.USER_LOGIN));
		admin.setName(resultSet.getString(SqlColumnsName.ADMIN_NAME));
		admin.setSurname(resultSet.getString(SqlColumnsName.ADMIN_SURNAME));
		admin.setEmail(resultSet.getString(SqlColumnsName.ADMIN_EMAIL));
		admin.setVerified(resultSet.getBoolean(SqlColumnsName.USER_VERIFIED));
		admin.setActive(resultSet.getBoolean(SqlColumnsName.USER_ACTIVE));
		admin.setRegistrationDate(
						resultSet.getTimestamp(SqlColumnsName.USER_REGISTRATION_DATE, timezone)
						         .toLocalDateTime());
		admin.setRole(UserRole.valueOf(resultSet.getString(SqlColumnsName.USER_ROLE)));
		return admin;
	}
}

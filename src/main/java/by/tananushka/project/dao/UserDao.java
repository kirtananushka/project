package by.tananushka.project.dao;

import by.tananushka.project.bean.User;
import by.tananushka.project.bean.UserRole;

import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 */
public interface UserDao extends AbstractDao {

	/**
	 * Authentication optional.
	 *
	 * @param login    the login
	 * @param password the password
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<User> authentication(String login, String password) throws DaoException;

	/**
	 * Find all users list.
	 *
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	List<List<? extends User>> findAllUsers() throws DaoException;

	/**
	 * Find role by id optional.
	 *
	 * @param userId the user id
	 * @return the optional
	 * @throws DaoException the dao exception
	 */
	Optional<UserRole> findRoleById(int userId) throws DaoException;

	/**
	 * Email confirmation boolean.
	 *
	 * @param userId the user id
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean emailConfirmation(int userId) throws DaoException;

	/**
	 * Check login and email boolean.
	 *
	 * @param login the login
	 * @param email the email
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean checkLoginAndEmail(String login, String email) throws DaoException;

	/**
	 * Sets new password.
	 *
	 * @param login    the login
	 * @param password the password
	 * @return the new password
	 * @throws DaoException the dao exception
	 */
	boolean setNewPassword(String login, String password) throws DaoException;

	/**
	 * Delete user boolean.
	 *
	 * @param userId the user id
	 * @return the boolean
	 * @throws DaoException the dao exception
	 */
	boolean deleteUser(int userId) throws DaoException;
}

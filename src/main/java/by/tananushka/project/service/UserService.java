package by.tananushka.project.service;

import by.tananushka.project.bean.User;
import by.tananushka.project.controller.SessionContent;

import java.util.List;
import java.util.Optional;

/**
 * The interface User service.
 */
public interface UserService {

	/**
	 * Authentication optional.
	 *
	 * @param login    the login
	 * @param password the password
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<User> authentication(String login, String password) throws ServiceException;

	/**
	 * Find all users list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	List<List<? extends User>> findAllUsers() throws ServiceException;

	/**
	 * Find user by id optional.
	 *
	 * @param strUserId the str user id
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<User> findUserById(String strUserId) throws ServiceException;

	/**
	 * Find user by id optional.
	 *
	 * @param userId the user id
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<User> findUserById(int userId) throws ServiceException;

	/**
	 * Update user optional.
	 *
	 * @param content the content
	 * @return the optional
	 * @throws ServiceException the service exception
	 */
	Optional<User> updateUser(SessionContent content) throws ServiceException;

	/**
	 * Delete user boolean.
	 *
	 * @param content the content
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean deleteUser(SessionContent content) throws ServiceException;

	/**
	 * Email confirmation boolean.
	 *
	 * @param userId the user id
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean emailConfirmation(int userId) throws ServiceException;

	/**
	 * Send message boolean.
	 *
	 * @param content the content
	 * @return the boolean
	 */
	boolean sendMessage(SessionContent content);

	/**
	 * Send password boolean.
	 *
	 * @param content the content
	 * @return the boolean
	 * @throws ServiceException the service exception
	 */
	boolean sendPassword(SessionContent content) throws ServiceException;
}

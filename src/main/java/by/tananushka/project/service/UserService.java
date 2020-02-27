package by.tananushka.project.service;

import by.tananushka.project.bean.User;
import by.tananushka.project.controller.SessionContent;

import java.util.List;
import java.util.Optional;

public interface UserService {

	Optional<User> authentication(String login, String password) throws ServiceException;

	List<List<? extends User>> findAllUsers() throws ServiceException;

	Optional<User> findUserById(String strUserId) throws ServiceException;

	Optional<User> findUserById(int userId) throws ServiceException;

	Optional<User> updateUser(SessionContent content) throws ServiceException;

	boolean emailConfirmation(int userId) throws ServiceException;

	boolean sendMessage(SessionContent content);
}

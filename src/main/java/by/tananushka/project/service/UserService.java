package by.tananushka.project.service;

import by.tananushka.project.bean.User;
import by.tananushka.project.controller.SessionContent;

import java.util.Optional;

public interface UserService {

	Optional<User> authentication(String login, String password) throws ServiceException;

	boolean emailConfirmation(int userId) throws ServiceException;

	boolean sendMessage(SessionContent content);
}

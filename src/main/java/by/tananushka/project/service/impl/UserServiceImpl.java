package by.tananushka.project.service.impl;

import by.tananushka.project.bean.User;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.DaoProvider;
import by.tananushka.project.dao.UserDao;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.UserService;
import by.tananushka.project.service.validation.UserDataValidator;
import by.tananushka.project.util.EmailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

	private static final UserDataValidator validator = UserDataValidator.getInstance();
	private static final UserService instance = new UserServiceImpl();
	private static Logger log = LogManager.getLogger();
	private UserDao userDao = DaoProvider.getInstance().getUserDao();

	private UserServiceImpl() {
	}

	public static UserService getInstance() {
		return instance;
	}

	@Override
	public Optional<User> authentication(String login, String password) throws ServiceException {
		if (!validator.checkLogin(login)) {
			log.info("Login '{}' is invalid", login);
			throw new ServiceException("Login or/and password are invalid");
		}
		if (!validator.checkLogin(password)) {
			log.info("Password '{}' is invalid", password);
			throw new ServiceException("Login or/and password are invalid");
		}
		Optional<User> userOptional;
		try {
			userOptional = userDao.authentication(login, password);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return userOptional;
	}

	@Override
	public boolean emailConfirmation(int userId) throws ServiceException {
		boolean isConfirmationSuccessful;
		if (userId <= 0) {
			throw new ServiceException("User ID is invalid");
		}
		try {
			isConfirmationSuccessful = userDao.emailConfirmation(userId);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return isConfirmationSuccessful;
	}

	@Override
	public boolean sendMessage(SessionContent content) {
		boolean isParameterValid = true;
		List<String> errorsList = new ArrayList<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_SEND_MESSAGE, null);
		String name = content.getRequestParameter(ParamName.PARAM_MSG_NAME).strip();
		content.assignSessionAttribute(ParamName.PARAM_NAME_DEFAULT, name);
		if (!validator.checkName(name)) {
			errorsList.add(ErrorMessageKey.INVALID_NAME);
			isParameterValid = false;
		}
		String surname = content.getRequestParameter(ParamName.PARAM_MSG_SURNAME).strip();
		content.assignSessionAttribute(ParamName.PARAM_SURNAME_DEFAULT, surname);
		if (!validator.checkSurame(surname)) {
			errorsList.add(ErrorMessageKey.INVALID_SURNAME);
			isParameterValid = false;
		}
		String phone = content.getRequestParameter(ParamName.PARAM_MSG_PHONE).strip();
		content.assignSessionAttribute(ParamName.PARAM_PHONE_DEFAULT, phone);
		if (!validator.checkPhone(phone)) {
			errorsList.add(ErrorMessageKey.INVALID_PHONE);
			isParameterValid = false;
		}
		String email = content.getRequestParameter(ParamName.PARAM_MSG_EMAIL).strip();
		content.assignSessionAttribute(ParamName.PARAM_EMAIL_DEFAULT, email);
		if (!validator.checkEmail(email)) {
			errorsList.add(ErrorMessageKey.INVALID_EMAIL);
			isParameterValid = false;
		}
		String message = content.getRequestParameter(ParamName.PARAM_MSG_MESSAGE).strip();
		content.assignSessionAttribute(ParamName.PARAM_MESSAGE_DEFAULT, message);
		if (isParameterValid) {
			EmailSender.sendMessage(content);
		} else {
			content.assignSessionAttribute(ParamName.PARAM_ERR_SEND_MESSAGE, errorsList);
		}
		return isParameterValid;
	}
}

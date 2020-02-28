package by.tananushka.project.service.impl;

import by.tananushka.project.bean.Admin;
import by.tananushka.project.bean.Client;
import by.tananushka.project.bean.Manager;
import by.tananushka.project.bean.User;
import by.tananushka.project.bean.UserRole;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.DaoProvider;
import by.tananushka.project.dao.UserDao;
import by.tananushka.project.dao.impl.AdminDaoImpl;
import by.tananushka.project.dao.impl.ClientDaoImpl;
import by.tananushka.project.dao.impl.ManagerDaoImpl;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.UserService;
import by.tananushka.project.service.validation.EscapeCharactersChanger;
import by.tananushka.project.service.validation.UserDataValidator;
import by.tananushka.project.util.EmailSender;
import by.tananushka.project.util.PasswordUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

	private static final UserDataValidator validator = UserDataValidator.getInstance();
	private static final EscapeCharactersChanger changer = EscapeCharactersChanger.getInstance();
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
			throw new ServiceException("Login or/and password are invalid.");
		}
		if (!validator.checkPassword(password)) {
			log.info("Password '{}' is invalid", password);
			throw new ServiceException("Login or/and password are invalid.");
		}
		Optional<User> userOptional;
		try {
			userOptional = userDao.authentication(login, password);
		} catch (DaoException e) {
			throw new ServiceException("Exception while authentication.", e);
		}
		return userOptional;
	}

	@Override
	public Optional<User> findUserById(String strUserId) throws ServiceException {
		int userId;
		Optional<User> userOptional = Optional.empty();
		if (validator.checkId(strUserId)) {
			userId = Integer.parseInt(strUserId);
			userOptional = findUserById(userId);
		}
		return userOptional;
	}

	@Override
	public Optional<User> findUserById(int userId) throws ServiceException {
		Optional<User> userOptional = Optional.empty();
		try {
			Optional<UserRole> userRoleOptional = userDao.findRoleById(userId);
			if (userRoleOptional.isPresent()) {
				UserRole userRole = userRoleOptional.get();
				switch (userRole) {
					case ADMIN:
						Optional<Admin> adminOptional = AdminDaoImpl.getInstance().findAdmin();
						if (adminOptional.isPresent()) {
							userOptional = Optional.of(adminOptional.get());
						}
						break;
					case MANAGER:
						Optional<Manager> managerOptional =
										ManagerDaoImpl.getInstance().findManagerById(userId);
						if (managerOptional.isPresent()) {
							userOptional = Optional.of(managerOptional.get());
						}
						break;
					case CLIENT:
						Optional<Client> clientOptional =
										ClientDaoImpl.getInstance().findClientById(userId);
						if (clientOptional.isPresent()) {
							userOptional = Optional.of(clientOptional.get());
						}
						break;
					default:
						userOptional = Optional.empty();
						break;
				}
			}
		} catch (DaoException e) {
			throw new ServiceException("Exception while finding user by ID.", e);
		}
		return userOptional;
	}

	@Override
	public List<List<? extends User>> findAllUsers() throws ServiceException {
		List<List<? extends User>> usersList;
		try {
			usersList = userDao.findAllUsers();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting all users", e);
		}
		return usersList;
	}

	@Override
	public Optional<User> updateUser(SessionContent content) throws ServiceException {
		boolean isParameterValid = true;
		Optional<User> userOptional = Optional.empty();
		List<String> errorsList = new ArrayList<>();
		List<String> errorsListFromContent = null;
		content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_USER_MESSAGE, null);
		String role = content.getRequestParameter(ParamName.PARAM_ROLE).strip();
		UserRole userRole = null;
		if (validator.checkRole(role)) {
			userRole = UserRole.valueOf(role);
		} else {
			errorsList.add(ErrorMessageKey.INVALID_ROLE);
			isParameterValid = false;
		}
		if (isParameterValid) {
			switch (userRole) {
				case ADMIN:
					Optional<Admin> adminOptional = AdminServiceImpl.getInstance().updateAdmin(content);
					if (adminOptional.isPresent()) {
						userOptional = Optional.of(adminOptional.get());
					}
					errorsListFromContent = (List<String>) content
									.getSessionAttribute(ParamName.PARAM_ERR_UPDATE_ADMIN_MESSAGE);
					if (errorsListFromContent != null) {
						errorsList.addAll(errorsListFromContent);
					}
					break;
				case MANAGER:
					Optional<Manager> managerOptional =
									ManagerServiceImpl.getInstance().updateManager(content);
					if (managerOptional.isPresent()) {
						userOptional = Optional.of(managerOptional.get());
					}
					errorsListFromContent = (List<String>) content
									.getSessionAttribute(ParamName.PARAM_ERR_UPDATE_MANAGER_MESSAGE);
					if (errorsListFromContent != null) {
						errorsList.addAll(errorsListFromContent);
					}
					break;
				case CLIENT:
					Optional<Client> clientOptional =
									ClientServiceImpl.getInstance().updateClient(content);
					if (clientOptional.isPresent()) {
						userOptional = Optional.of(clientOptional.get());
					}
					errorsListFromContent = (List<String>) content
									.getSessionAttribute(ParamName.PARAM_ERR_UPDATE_CLIENT_MESSAGE);
					if (errorsListFromContent != null) {
						errorsList.addAll(errorsListFromContent);
					}
					break;
				default:
					userOptional = Optional.empty();
					break;
			}
		} else {
			content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_USER_MESSAGE, errorsList);
			throw new ServiceException("Invalid parameter(s)");
		}
		content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_USER_MESSAGE, errorsList);
		return userOptional;
	}

	@Override
	public boolean emailConfirmation(int userId) throws ServiceException {
		boolean isConfirmationSuccessful;
		if (userId <= 0) {
			throw new ServiceException("User ID is invalid.");
		}
		try {
			isConfirmationSuccessful = userDao.emailConfirmation(userId);
		} catch (DaoException e) {
			throw new ServiceException("Exception while email confirmation.", e);
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
		message = changer.changeCharacters(message);
		log.debug(message);
		content.assignSessionAttribute(ParamName.PARAM_MESSAGE_DEFAULT, message);
		if (isParameterValid) {
			EmailSender.sendMessage(content);
		} else {
			content.assignSessionAttribute(ParamName.PARAM_ERR_SEND_MESSAGE, errorsList);
		}
		return isParameterValid;
	}

	@Override
	public boolean sendPassword(SessionContent content) throws ServiceException {
		boolean isOperationSuccessful;
		List<String> errorsList = new ArrayList<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_SEND_NEW_PASSWORD_MESSAGE, null);
		String login = content.getRequestParameter(ParamName.PARAM_LOGIN).strip();
		String email = content.getRequestParameter(ParamName.PARAM_EMAIL).strip();
		if (!validator.checkLogin(login)) {
			log.info("Login '{}' is invalid", login);
			throw new ServiceException("Login or/and email are invalid.");
		}
		if (!validator.checkEmail(email)) {
			log.info("Email '{}' is invalid", email);
			throw new ServiceException("Login or/and email are invalid.");
		}
		try {
			isOperationSuccessful = userDao.checkLoginAndEmail(login, email);
			if (isOperationSuccessful) {
				String newPassword = PasswordUtility.getInstance().generatePassword();
				userDao.setNewPassword(login, newPassword);
				EmailSender.sendNewPassword(email, newPassword);
				isOperationSuccessful = true;
			} else {
				content.assignSessionAttribute(ParamName.PARAM_ERR_SEND_NEW_PASSWORD_MESSAGE,
								ErrorMessageKey.INCORRECT_DATA);
			}
		} catch (DaoException e) {
			throw new ServiceException("Exception while setting new password.", e);
		}
		return isOperationSuccessful;
	}
}

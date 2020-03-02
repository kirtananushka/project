package by.tananushka.project.service.impl;

import by.tananushka.project.bean.Client;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.dao.ClientDao;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.DaoProvider;
import by.tananushka.project.service.ClientService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.validation.UserDataValidator;
import by.tananushka.project.util.EmailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Client service.
 */
public class ClientServiceImpl implements ClientService {

	private static final ClientService instance = new ClientServiceImpl();
	private static Logger log = LogManager.getLogger();
	private ClientDao clientDao = DaoProvider.getInstance().getClientDao();
	private UserDataValidator validator = UserDataValidator.getInstance();

	private ClientServiceImpl() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static ClientService getInstance() {
		return instance;
	}

	@Override
	public List<Client> findActiveClients() throws ServiceException {
		List<Client> clientsList;
		try {
			clientsList = clientDao.findActiveClients();
		} catch (DaoException e) {
			log.error("Exception while getting active clients");
			throw new ServiceException("Exception while getting active clients", e);
		}
		return clientsList;
	}

	@Override
	public List<Client> findAllClients() throws ServiceException {
		List<Client> clientsList;
		try {
			clientsList = clientDao.findAllClients();
		} catch (DaoException e) {
			log.error("Exception while getting all clients.");
			throw new ServiceException("Exception while getting all clients.", e);
		}
		return clientsList;
	}

	@Override
	public Optional<Client> findClientById(String strClientId) throws ServiceException {
		int clientId;
		Optional<Client> clientOptional = Optional.empty();
		if (validator.checkId(strClientId)) {
			clientId = Integer.parseInt(strClientId);
			clientOptional = findClientById(clientId);
		}
		return clientOptional;
	}

	@Override
	public Optional<Client> findClientById(int clientId) throws ServiceException {
		Optional<Client> clientOptional;
		try {
			clientOptional = clientDao.findClientById(clientId);
		} catch (DaoException e) {
			log.error("Exception while finding client by ID.");
			throw new ServiceException("Exception while finding client by ID.", e);
		}
		return clientOptional;
	}

	@Override
	public Optional<Client> createClient(SessionContent content) throws ServiceException {
		boolean isParameterValid = true;
		Optional<Client> clientOptional;
		List<String> errorsList = new ArrayList<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_REG_MESSAGE, null);
		String login = content.getRequestParameter(ParamName.PARAM_LOGIN).strip();
		content.assignSessionAttribute(ParamName.PARAM_LOGIN_DEFAULT, login);
		if (!validator.checkLogin(login)) {
			errorsList.add(ErrorMessageKey.INVALID_LOGIN);
			isParameterValid = false;
		}
		if (!checkLogin(login)) {
			errorsList.add(ErrorMessageKey.LOGIN_EXISTS);
		}
		String name = content.getRequestParameter(ParamName.PARAM_NAME).strip();
		content.assignSessionAttribute(ParamName.PARAM_NAME_DEFAULT, name);
		if (!validator.checkName(name)) {
			errorsList.add(ErrorMessageKey.INVALID_NAME);
			isParameterValid = false;
		}
		String surname = content.getRequestParameter(ParamName.PARAM_SURNAME).strip();
		content.assignSessionAttribute(ParamName.PARAM_SURNAME_DEFAULT, surname);
		if (!validator.checkSurame(surname)) {
			errorsList.add(ErrorMessageKey.INVALID_SURNAME);
			isParameterValid = false;
		}
		String phone = content.getRequestParameter(ParamName.PARAM_PHONE).strip();
		content.assignSessionAttribute(ParamName.PARAM_PHONE_DEFAULT, phone);
		if (!validator.checkPhone(phone)) {
			errorsList.add(ErrorMessageKey.INVALID_PHONE);
			isParameterValid = false;
		}
		String email = content.getRequestParameter(ParamName.PARAM_EMAIL).strip();
		content.assignSessionAttribute(ParamName.PARAM_EMAIL_DEFAULT, email);
		if (!validator.checkEmail(email)) {
			errorsList.add(ErrorMessageKey.INVALID_EMAIL);
			isParameterValid = false;
		}
		String password = content.getRequestParameter(ParamName.PARAM_PASS).strip();
		content.assignSessionAttribute(ParamName.PARAM_PASS_DEFAULT, password);
		if (!validator.checkPassword(password)) {
			errorsList.add(ErrorMessageKey.INVALID_PASS);
			isParameterValid = false;
		}
		String passwordRepeated = content.getRequestParameter(ParamName.PARAM_PASS_REPEATED).strip();
		content.assignSessionAttribute(ParamName.PARAM_PASS_REPEATED_DEFAULT, passwordRepeated);
		if (!password.equals(passwordRepeated)) {
			errorsList.add(ErrorMessageKey.PASS_NOT_EQUAL);
			isParameterValid = false;
		}
		Client client = null;
		if (isParameterValid) {
			client = new Client();
			client.setLogin(login);
			client.setPassword(password);
			client.setEmail(email);
			client.setName(name);
			client.setSurname(surname);
			client.setPhone(phone);
		} else {
			content.assignSessionAttribute(ParamName.PARAM_ERR_REG_MESSAGE, errorsList);
			log.info("Invalid parameter(s)");
			throw new ServiceException("Invalid parameter(s).");
		}
		try {
			clientOptional = clientDao.createClient(client);
			clientOptional.ifPresent(EmailSender::sendConfirmation);
		} catch (DaoException e) {
			log.error("Exception while creating client");
			throw new ServiceException(e);
		}
		return clientOptional;
	}

	@Override
	public Optional<Client> updateClient(SessionContent content) throws ServiceException {
		boolean isParameterValid = true;
		Optional<Client> clientOptional;
		List<String> errorsList = new ArrayList<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_CLIENT_MESSAGE, null);
		String strClientId = content.getRequestParameter(ParamName.PARAM_CLIENT_ID).strip();
		int clientId = 0;
		if (validator.checkId(strClientId)) {
			clientId = Integer.parseInt(strClientId);
		} else {
			errorsList.add(ErrorMessageKey.INVALID_ID);
			isParameterValid = false;
		}
		String name = content.getRequestParameter(ParamName.PARAM_NAME).strip();
		if (!validator.checkName(name)) {
			errorsList.add(ErrorMessageKey.INVALID_NAME);
			isParameterValid = false;
		}
		String surname = content.getRequestParameter(ParamName.PARAM_SURNAME).strip();
		if (!validator.checkSurame(surname)) {
			errorsList.add(ErrorMessageKey.INVALID_SURNAME);
			isParameterValid = false;
		}
		String phone = content.getRequestParameter(ParamName.PARAM_PHONE).strip();
		if (!validator.checkPhone(phone)) {
			errorsList.add(ErrorMessageKey.INVALID_PHONE);
			isParameterValid = false;
		}
		String email = content.getRequestParameter(ParamName.PARAM_EMAIL).strip();
		if (!validator.checkEmail(email)) {
			errorsList.add(ErrorMessageKey.INVALID_EMAIL);
			isParameterValid = false;
		}
		String strIsActive = content.getRequestParameter(ParamName.PARAM_ACTIVE);
		boolean isActive = strIsActive != null;
		if (isParameterValid) {
			Client client = new Client();
			client.setId(clientId);
			client.setEmail(email);
			client.setName(name);
			client.setSurname(surname);
			client.setPhone(phone);
			client.setActive(isActive);
			clientOptional = Optional.of(client);
		} else {
			content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_CLIENT_MESSAGE, errorsList);
			log.info("Invalid parameter(s).");
			throw new ServiceException("Invalid parameter(s).");
		}
		try {
			clientOptional = clientDao.updateClient(clientOptional.get());
		} catch (DaoException e) {
			log.error("Exception while updating client");
			throw new ServiceException(e);
		}
		return clientOptional;
	}

	@Override
	public boolean checkLogin(String login) throws ServiceException {
		boolean isLoginFree;
		try {
			isLoginFree = clientDao.checkLogin(login);
		} catch (DaoException e) {
			log.error("Exception while checking login.");
			throw new ServiceException("Exception while checking login.", e);
		}
		return isLoginFree;
	}
}

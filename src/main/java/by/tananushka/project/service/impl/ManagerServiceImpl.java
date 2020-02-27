package by.tananushka.project.service.impl;

import by.tananushka.project.bean.Manager;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.DaoProvider;
import by.tananushka.project.dao.ManagerDao;
import by.tananushka.project.service.ManagerService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.validation.UserDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManagerServiceImpl implements ManagerService {

	private static final ManagerService instance = new ManagerServiceImpl();
	private static Logger log = LogManager.getLogger();
	private ManagerDao managerDao = DaoProvider.getInstance().getManagerDao();
	private UserDataValidator validator = UserDataValidator.getInstance();

	private ManagerServiceImpl() {
	}

	public static ManagerService getInstance() {
		return instance;
	}

	@Override
	public List<Manager> findActiveManagers() throws ServiceException {
		List<Manager> managersList;
		try {
			managersList = managerDao.findActiveManagers();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting active managers", e);
		}
		return managersList;
	}

	@Override
	public List<Manager> findAllManagers() throws ServiceException {
		List<Manager> managersList;
		try {
			managersList = managerDao.findAllManagers();
		} catch (DaoException e) {
			throw new ServiceException("Exception while getting all managers", e);
		}
		return managersList;
	}

	@Override
	public Optional<Manager> findManagerById(String strManagerId) throws ServiceException {
		int managerId;
		Optional<Manager> managerOptional = Optional.empty();
		if (validator.checkId(strManagerId)) {
			managerId = Integer.parseInt(strManagerId);
			managerOptional = findManagerById(managerId);
		}
		return managerOptional;
	}

	@Override
	public Optional<Manager> findManagerById(int managerId) throws ServiceException {
		Optional<Manager> managerOptional;
		try {
			managerOptional = managerDao.findManagerById(managerId);
		} catch (DaoException e) {
			throw new ServiceException("Exception while finding manager by ID.", e);
		}
		return managerOptional;
	}

	@Override
	public Optional<Manager> appointManager(SessionContent content) throws ServiceException {
		Optional<Manager> managerOptional;
		String strManagerId = content.getRequestParameter(ParamName.PARAM_MANAGER_ID).strip();
		if (validator.checkId(strManagerId)) {
			int managerId = Integer.parseInt(strManagerId);
			try {
				managerOptional = managerDao.appointManager(managerId);
			} catch (DaoException e) {
				log.error("Invalid ID {}.", strManagerId);
				throw new ServiceException("Invalid id.", e);
			}
		} else {
			log.error("Exception while appointment of manager.");
			throw new ServiceException("Exception while appointment of manager.");
		}
		return managerOptional;
	}

	@Override
	public Optional<Manager> updateManager(SessionContent content) throws ServiceException {
		boolean isParameterValid = true;
		Optional<Manager> managerOptional;
		List<String> errorsList = new ArrayList<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_MANAGER_MESSAGE, null);
		String strManagerId = content.getRequestParameter(ParamName.PARAM_MANAGER_ID).strip();
		int managerId = 0;
		if (validator.checkId(strManagerId)) {
			managerId = Integer.parseInt(strManagerId);
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
			Manager manager = new Manager();
			manager.setId(managerId);
			manager.setName(name);
			manager.setSurname(surname);
			manager.setPhone(phone);
			manager.setEmail(email);
			manager.setActive(isActive);
			managerOptional = Optional.of(manager);
		} else {
			content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_MANAGER_MESSAGE, errorsList);
			throw new ServiceException("Invalid parameter(s).");
		}
		try {
			managerOptional = managerDao.updateManager(managerOptional.get());
		} catch (DaoException e) {
			log.error("Exception while updating manager.");
			throw new ServiceException("Exception while updating manager.", e);
		}
		return managerOptional;
	}
}

package by.tananushka.project.service.impl;

import by.tananushka.project.bean.Admin;
import by.tananushka.project.command.ErrorMessageKey;
import by.tananushka.project.controller.ParamName;
import by.tananushka.project.controller.SessionContent;
import by.tananushka.project.dao.AdminDao;
import by.tananushka.project.dao.DaoException;
import by.tananushka.project.dao.DaoProvider;
import by.tananushka.project.service.AdminService;
import by.tananushka.project.service.ServiceException;
import by.tananushka.project.service.validation.UserDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminServiceImpl implements AdminService {

	private static final AdminService instance = new AdminServiceImpl();
	private static Logger log = LogManager.getLogger();
	private AdminDao adminDao = DaoProvider.getInstance().getAdminDao();
	private UserDataValidator validator = UserDataValidator.getInstance();

	private AdminServiceImpl() {
	}

	public static AdminService getInstance() {
		return instance;
	}

	@Override
	public Optional<Admin> updateAdmin(SessionContent content) throws ServiceException {
		boolean isParameterValid = true;
		Optional<Admin> adminOptional;
		List<String> errorsList = new ArrayList<>();
		content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_ADMIN_MESSAGE, null);
		String strAdminId = content.getRequestParameter(ParamName.PARAM_ADMIN_ID).strip();
		int adminId = 0;
		if (!validator.checkId(strAdminId)) {
			errorsList.add(ErrorMessageKey.INVALID_ID);
			isParameterValid = false;
		} else {
			adminId = Integer.parseInt(strAdminId);
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
		String email = content.getRequestParameter(ParamName.PARAM_EMAIL).strip();
		if (!validator.checkEmail(email)) {
			errorsList.add(ErrorMessageKey.INVALID_EMAIL);
			isParameterValid = false;
		}
		if (isParameterValid) {
			Admin admin = new Admin();
			admin.setId(adminId);
			admin.setName(name);
			admin.setSurname(surname);
			admin.setEmail(email);
			adminOptional = Optional.of(admin);
		} else {
			content.assignSessionAttribute(ParamName.PARAM_ERR_UPDATE_ADMIN_MESSAGE, errorsList);
			throw new ServiceException("Invalid parameter(s)");
		}
		try {
			adminOptional = adminDao.updateAdmin(adminOptional.get());
		} catch (DaoException e) {
			log.error("Exception while updating admin");
			throw new ServiceException(e);
		}
		return adminOptional;
	}
}

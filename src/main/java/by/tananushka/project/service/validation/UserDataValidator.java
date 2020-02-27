package by.tananushka.project.service.validation;

import by.tananushka.project.bean.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDataValidator {

	private static final UserDataValidator instance = new UserDataValidator();
	private static final String LOGIN_PATTERN = "^[A-z][\\w]{4,14}$";
	private static final String NAME_PATTERN = "^[\\p{L}- ]{1,255}$";
	private static final String PHONE_PATTERN = "^375\\d{9}$";
	private static final String EMAIL_PATTERN =
					"^([\\w-]+\\.)*[\\w-]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,6}$";
	private static final String PASS_PATTERN = "^[\\w]{8,20}$";
	private static final String ID_PATTERN = "^\\d{1,10}$";
	private static Logger log = LogManager.getLogger();

	private UserDataValidator() {
	}

	public static UserDataValidator getInstance() {
		return instance;
	}

	public boolean checkLogin(String login) {
		if (login == null) {
			return false;
		}
		log.debug("Login checked: {}.", login);
		return login.matches(LOGIN_PATTERN);
	}

	public boolean checkName(String name) {
		if (name == null) {
			return false;
		}
		log.debug("Name checked: {}.", name);
		return name.matches(NAME_PATTERN);
	}

	public boolean checkSurame(String surname) {
		if (surname == null) {
			return false;
		}
		log.debug("Surname checked: {}.", surname);
		return surname.matches(NAME_PATTERN);
	}

	public boolean checkPhone(String phone) {
		if (phone == null) {
			return false;
		}
		log.debug("Phone checked: {}.", phone);
		return phone.matches(PHONE_PATTERN);
	}

	public boolean checkEmail(String email) {
		if (email == null) {
			return false;
		}
		log.debug("Email checked: {}.", email);
		return email.matches(EMAIL_PATTERN);
	}

	public boolean checkPassword(String password) {
		if (password == null) {
			return false;
		}
		log.debug("Password checked: {}.", password);
		return password.matches(PASS_PATTERN);
	}

	public boolean checkId(String strId) {
		if (strId == null || !strId.matches(ID_PATTERN)) {
			log.error("ID is not valid: {}.", strId);
			return false;
		}
		try {
			Integer.parseInt(strId);
			log.debug("ID is valid: {}.", strId);
			return true;
		} catch (NumberFormatException e) {
			log.error("ID is not valid: {}.", strId);
			return false;
		}
	}

	public boolean checkRole(String role) {
		if (role == null) {
			return false;
		}
		for (UserRole el : UserRole.values()) {
			if (el.name().equals(role)) {
				return true;
			}
		}
		return false;
	}
}

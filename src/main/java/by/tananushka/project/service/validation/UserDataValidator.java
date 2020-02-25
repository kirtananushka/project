package by.tananushka.project.service.validation;

public class UserDataValidator {

	private static final UserDataValidator instance = new UserDataValidator();
	private static final String LOGIN_PATTERN = "^[A-z][\\w]{4,14}$";
	private static final String NAME_PATTERN = "^[\\p{L}- ]{1,255}$";
	private static final String PHONE_PATTERN = "^375\\d{9}$";
	private static final String EMAIL_PATTERN =
					"^([\\w-]+\\.)*[\\w-]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,6}$";
	private static final String PASS_PATTERN = "^[\\w]{8,20}$";

	private UserDataValidator() {
	}

	public static UserDataValidator getInstance() {
		return instance;
	}

	public boolean checkLogin(String login) {
		if (login == null) {
			return false;
		}
		return login.matches(LOGIN_PATTERN);
	}

	public boolean checkName(String name) {
		if (name == null) {
			return false;
		}
		return name.matches(NAME_PATTERN);
	}

	public boolean checkSurame(String surname) {
		if (surname == null) {
			return false;
		}
		return surname.matches(NAME_PATTERN);
	}

	public boolean checkPhone(String phone) {
		if (phone == null) {
			return false;
		}
		return phone.matches(PHONE_PATTERN);
	}

	public boolean checkEmail(String email) {
		if (email == null) {
			return false;
		}
		return email.matches(EMAIL_PATTERN);
	}

	public boolean checkPassword(String password) {
		if (password == null) {
			return false;
		}
		return password.matches(PASS_PATTERN);
	}
}

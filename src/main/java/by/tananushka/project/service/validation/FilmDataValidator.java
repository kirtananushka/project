package by.tananushka.project.service.validation;

public class FilmDataValidator {

	private static final FilmDataValidator instance = new FilmDataValidator();
	private static final String STRING_PATTERN = "^[А-Я][А-я ]+$";
	private static final String NUMBER_PATTERN = "^\\d+$";
	private static final String NAME_PATTERN = "^[\\p{L}- ]{1,255}$";
	private static final String PHONE_PATTERN = "^375\\d{9}$";
	private static final String EMAIL_PATTERN =
					"^([\\w-]+\\.)*[\\w-]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,6}$";
	private static final String PASS_PATTERN = "^[\\w]{8,20}$";

	private FilmDataValidator() {
	}

	public static FilmDataValidator getInstance() {
		return instance;
	}

	public boolean checkString(String string) {
		return string.matches(STRING_PATTERN);
	}

	public boolean checkNumber(String number) {
		return number.matches(NUMBER_PATTERN);
	}

	public boolean checkAge(int age) {
//		return age >= 0 && age <= 18;
		return true;
	}

	public boolean checkYear(int year) {
//		return year >= 1895 && year <= 2020;
		return true;
	}
}

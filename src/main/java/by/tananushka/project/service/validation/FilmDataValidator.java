package by.tananushka.project.service.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class FilmDataValidator {

	private static final FilmDataValidator instance = new FilmDataValidator();
	private static final String CYR_STRING_PATTERN = "^[А-Я][А-я ]{1,255}$";
	private static final String TITLE_GENRE_PATTERN = "^.{1,255}$";
	private static final String NUMBER_PATTERN = "^\\d{1,4}$";
	private static final String INTEGER_PATTERN = "^\\d{1,10}$";
	private static final String YEAR_PATTERN = "^\\d{4}$";
	private static final String TWO_DIGITS_NUMBER_PATTERN = "^\\d{1,2}$";
	private static Logger log = LogManager.getLogger();

	private FilmDataValidator() {
	}

	public static FilmDataValidator getInstance() {
		return instance;
	}

	public boolean checkString(String string) {
		if (string == null) {
			return false;
		}
		log.debug("String checked: {}.", string);
		return string.matches(CYR_STRING_PATTERN);
	}

	public boolean checkTitle(String title) {
		if (title == null) {
			return false;
		}
		log.debug("String checked: {}.", title);
		return title.matches(TITLE_GENRE_PATTERN);
	}

	public boolean checkNumber(String number) {
		if (number == null) {
			return false;
		}
		log.debug("Number checked: {}.", number);
		return number.matches(NUMBER_PATTERN);
	}

	public boolean checkIdString(String id) {
		if (id == null) {
			return false;
		}
		log.debug("String ID checked: {}.", id);
		return id.matches(INTEGER_PATTERN);
	}

	public boolean checkId(String id) {
		if (id == null) {
			return false;
		}
		try {
			Integer.parseInt(id);
			log.debug("ID is valid: {}.", id);
			return true;
		} catch (NumberFormatException e) {
			log.error("ID is not valid: {}.", id);
			return false;
		}
	}

	public boolean checkTwoDigitsString(String strNumber) {
		if (strNumber == null) {
			return false;
		}
		log.debug("String number checked: {}.", strNumber);
		return strNumber.matches(TWO_DIGITS_NUMBER_PATTERN);
	}

	public boolean checkAge(int age) {
		log.debug("Age checked: {}.", age);
		return age >= 0 && age <= 18;
	}

	public boolean checkYearString(String strYear) {
		if (strYear == null) {
			return false;
		}
		log.debug("String year checked: {}.", strYear);
		return strYear.matches(YEAR_PATTERN);
	}

	public boolean checkYear(int year) {
		int currentYear = LocalDate.now().getYear();
		log.debug("Year checked: {}. The current year: {}.", year, currentYear);
		return year >= 1895 && year <= currentYear;
	}

	public boolean checkStringArray(String[] arr) {
		if (arr == null) {
			return false;
		}
		boolean isValid = false;
		for (String string : arr) {
			isValid = checkString(string);
		}
		return isValid;
	}

	public boolean checkStringNumberArray(String[] arr) {
		if (arr == null) {
			return false;
		}
		boolean isValid = false;
		for (String string : arr) {
			isValid = checkNumber(string);
		}
		return isValid;
	}

	public boolean checkGenre(String genreName) {
		if (genreName == null) {
			return false;
		}
		log.debug("Genre checked: {}.", genreName);
		return genreName.matches(TITLE_GENRE_PATTERN);
	}

}

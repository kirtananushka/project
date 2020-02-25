package by.tananushka.project.service.validation;

import by.tananushka.project.parsing.DateParser;
import by.tananushka.project.parsing.ParsingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;

public class ShowDataValidator {

	private static final ShowDataValidator instance = new ShowDataValidator();
	private static final String STRING_PATTERN = "^.{1,255}$";
	private static final String DATE_PATTERN = "dd.MM.yyyy";
	private static final String TWO_PLACE_NUMBER_PATTERN = "^\\d{1,2}$";
	private static Logger log = LogManager.getLogger();

	private ShowDataValidator() {
	}

	public static ShowDataValidator getInstance() {
		return instance;
	}

	public boolean checkCinema(String string) {
		if (string == null) {
			return false;
		}
		log.debug("String checked: {}.", string);
		return string.matches(STRING_PATTERN);
	}

	public boolean checkDate(String strDate) {
		if (strDate == null) {
			return false;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
		dateFormat.setLenient(false);
		try {
			DateParser.getInstance().parseDate(strDate);
		} catch (ParsingException e) {
			log.error("Invalid date: {}.", strDate);
			return false;
		}
		log.debug("Checked date: {}.", strDate);
		return true;
	}

	public boolean checkTwoDigits(String strNumber) {
		if (strNumber == null) {
			return false;
		}
		log.debug("Number id checked: {}.", strNumber);
		return strNumber.matches(TWO_PLACE_NUMBER_PATTERN);
	}
}
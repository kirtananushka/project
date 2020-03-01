package by.tananushka.project.parsing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Date parser.
 */
public class DateParser {

	private static final DateParser instance = new DateParser();
	private static final String DATE_PATTERN = "dd.MM.yyyy";
	private static final String DATE_TIME_PATTERN = "dd.MM.yyyyHHmm";
	private static Logger log = LogManager.getLogger();

	private DateParser() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static DateParser getInstance() {
		return instance;
	}

	/**
	 * Parse date optional.
	 *
	 * @param strDate the str date
	 * @return the optional
	 * @throws ParsingException the parsing exception
	 */
	public Optional<LocalDate> parseDate(String strDate) throws ParsingException {
		LocalDate date;
		Optional<LocalDate> dateOptional = Optional.empty();
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
			date = LocalDate.parse(strDate, formatter);
			dateOptional = Optional.of(date);
		} catch (DateTimeParseException e) {
			log.error("Exception while parsing date: {}.", strDate);
			throw new ParsingException("DateTimeParseException while date parsing.", e);
		}
		return dateOptional;
	}

	/**
	 * Parse date time optional.
	 *
	 * @param strDate    the str date
	 * @param strHour    the str hour
	 * @param strMinutes the str minutes
	 * @return the optional
	 * @throws ParsingException the parsing exception
	 */
	public Optional<LocalDateTime> parseDateTime(String strDate, String strHour, String strMinutes)
					throws ParsingException {
		LocalDateTime dateTime;
		Optional<LocalDateTime> dateTimeOptional = Optional.empty();
		String strDateTime = strDate + strHour + strMinutes;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
			dateTime = LocalDateTime.parse(strDateTime, formatter);
			dateTimeOptional = Optional.of(dateTime);
		} catch (DateTimeParseException e) {
			log.error("Exception while parsing date and time: {}.", strDateTime);
			throw new ParsingException("DateTimeParseException while date and time parsing.", e);
		}
		return dateTimeOptional;
	}

	/**
	 * Format date string.
	 *
	 * @param date the date
	 * @return the string
	 */
	public String formatDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
		String strDate = date.format(formatter);
		return strDate;
	}

	/**
	 * Gets list of dates.
	 *
	 * @param monthsNumber the months number
	 * @return the list of dates
	 */
	public List<String> getListOfDates(int monthsNumber) {
		List<String> dates = new ArrayList<>();
		LocalDate start = LocalDate.now();
		LocalDate end = start.plusMonths(monthsNumber);
		start.datesUntil(end).map(this::formatDate).forEach(dates::add);
		return dates;
	}
}
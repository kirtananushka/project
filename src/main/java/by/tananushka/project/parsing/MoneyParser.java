package by.tananushka.project.parsing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Optional;

/**
 * The type Money parser.
 */
public class MoneyParser {

	private static final String MONEY_PATTERN = "#0.00";
	private static final char DECIMAL_SEPARATOR = '.';
	private static final String SEPARATOR_PATTERN = "\\.";
	private static final String ZERO = "0";
	private static final MoneyParser instance = new MoneyParser();
	private static Logger log = LogManager.getLogger();

	private MoneyParser() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static MoneyParser getInstance() {
		return instance;
	}

	/**
	 * Parse to big decimal optional.
	 *
	 * @param ruble  the ruble
	 * @param copeck the copeck
	 * @return the optional
	 * @throws ParsingException the parsing exception
	 */
	public Optional<BigDecimal> parseToBigDecimal(String ruble, String copeck)
					throws ParsingException {
		String money = ruble + DECIMAL_SEPARATOR + copeck;
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(DECIMAL_SEPARATOR);
		DecimalFormat decimalFormat = new DecimalFormat(MONEY_PATTERN, symbols);
		decimalFormat.setParseBigDecimal(true);
		BigDecimal bigDecimal;
		Optional<BigDecimal> bigDecimalOptional = Optional.empty();
		try {
			bigDecimal = (BigDecimal) decimalFormat.parse(money);
			bigDecimalOptional = Optional.of(bigDecimal);
		} catch (ParseException e) {
			log.error("Exception while parsing String to BigDecimal: {}.", money);
			throw new ParsingException("Exception while parsing String to BigDecimal.", e);
		}
		return bigDecimalOptional;
	}

	/**
	 * Parse big decimal to string optional.
	 *
	 * @param cost    the cost
	 * @param element the element
	 * @return the optional
	 */
	public Optional<String> parseBigDecimalToString(BigDecimal cost, int element) {
		Optional<String> stringOptional;
		String string = cost.toString().split(SEPARATOR_PATTERN)[element];
		if (string.length() == 1) {
			string = ZERO + string;
		}
		stringOptional = Optional.of(string);
		return stringOptional;
	}
}

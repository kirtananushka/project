package by.tananushka.project.service.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Order data validator.
 */
public class OrderDataValidator {

	private static final OrderDataValidator instance = new OrderDataValidator();
	private static final String CREDIT_CARD_PATTERN = "^\\d{16}$";
	private static Logger log = LogManager.getLogger();

	private OrderDataValidator() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static OrderDataValidator getInstance() {
		return instance;
	}

	/**
	 * Check credit card boolean.
	 *
	 * @param string the string
	 * @return the boolean
	 */
	public boolean checkCreditCard(String string) {
		if (string == null) {
			return false;
		}
		log.debug("Credit card checked: {}.", string);
		return string.matches(CREDIT_CARD_PATTERN);
	}
}
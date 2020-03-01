package by.tananushka.project.util;

import by.tananushka.project.service.validation.UserDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * The type Password utility.
 */
public class PasswordUtility {

	private static final PasswordUtility instance = new PasswordUtility();
	private static final int MIN_LENGTH = 8;
	private static final int MAX_LENGTH = 20;
	private static final int FACTOR = 5;
	private static final int RADIX = 36;
	private static Logger log = LogManager.getLogger();

	private PasswordUtility() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static PasswordUtility getInstance() {
		return instance;
	}

	/**
	 * Compare passwords boolean.
	 *
	 * @param password          the password
	 * @param encryptedPassword the encrypted password
	 * @return the boolean
	 */
	public boolean comparePasswords(String password, String encryptedPassword) {
		return BCrypt.checkpw(password, encryptedPassword);
	}

	/**
	 * Encode password string.
	 *
	 * @param password the password
	 * @return the string
	 */
	public String encodePassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	/**
	 * Generate password string.
	 *
	 * @return the string
	 */
	public String generatePassword() {
		Random random = new Random();
		UserDataValidator userDataValidator = UserDataValidator.getInstance();
		String password = "";
		do {
			int passwordLength = random.nextInt(MAX_LENGTH - MIN_LENGTH + 1) + MIN_LENGTH;
			password = new BigInteger(passwordLength * FACTOR, new SecureRandom())
							.toString(RADIX);
			log.debug(password);
		}
		while (!userDataValidator.checkPassword(password));
		return password;
	}
}

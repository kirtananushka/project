package by.tananushka.project.util;

import by.tananushka.project.service.validation.UserDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class PasswordUtility {

	private static final PasswordUtility instance = new PasswordUtility();
	private static final int MIN_LENGTH = 8;
	private static final int MAX_LENGTH = 20;
	private static final int FACTOR = 5;
	private static final int RADIX = 36;
	private static Logger log = LogManager.getLogger();

	private PasswordUtility() {
	}

	public static PasswordUtility getInstance() {
		return instance;
	}

	public boolean comparePasswords(String password, String encryptedPassword) {
		return BCrypt.checkpw(password, encryptedPassword);
	}

	public String encodePassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

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

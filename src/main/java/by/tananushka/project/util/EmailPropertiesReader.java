package by.tananushka.project.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * The type Email properties reader.
 */
public class EmailPropertiesReader {

	private static final EmailPropertiesReader instance = new EmailPropertiesReader();
	private static final String PATH = "email/email.properties";
	private static Logger log = LogManager.getLogger();

	private EmailPropertiesReader() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static EmailPropertiesReader getInstance() {
		return instance;
	}

	/**
	 * Read properties properties.
	 *
	 * @return the properties
	 */
	Properties readProperties() {
		Properties properties = new Properties();
		try {
			properties.load(Objects.requireNonNull
							(EmailPropertiesReader.class.getClassLoader()
							                            .getResourceAsStream(PATH)));
		} catch (IOException e) {
			log.fatal("Can't read email properties.");
			throw new RuntimeException("Can't read email properties.", e);
		}
		return properties;
	}
}

package by.tananushka.project.util;

import org.testng.annotations.Test;

import java.util.Properties;

import static org.testng.Assert.assertNotNull;

public class EmailPropertiesReaderTest {

	@Test(description = "Check reading from properties file")
	public void testReadProperties() {
		EmailPropertiesReader reader = EmailPropertiesReader.getInstance();
		Properties properties = reader.readProperties();
		assertNotNull(properties);
	}
}
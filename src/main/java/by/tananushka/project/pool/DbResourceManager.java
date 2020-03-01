package by.tananushka.project.pool;

import java.util.ResourceBundle;

/**
 * The type Db resource manager.
 */
class DbResourceManager {

	private static final String PATH = "database/database";
	private static final DbResourceManager instance = new DbResourceManager();
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(PATH);

	private DbResourceManager() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static DbResourceManager getInstance() {
		return instance;
	}

	/**
	 * Gets value.
	 *
	 * @param key the key
	 * @return the value
	 */
	String getValue(String key) {
		return resourceBundle.getString(key);
	}
}

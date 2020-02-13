package by.tananushka.project.pool;

import java.util.ResourceBundle;

class DbResourceManager {

	private static final String PATH = "database/database";
	private static final DbResourceManager instance = new DbResourceManager();
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(PATH);

	private DbResourceManager() {
	}

	public static DbResourceManager getInstance() {
		return instance;
	}

	String getValue(String key) {
		return resourceBundle.getString(key);
	}
}

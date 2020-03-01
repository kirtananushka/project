package by.tananushka.project.service.validation;

/**
 * The type Escape characters changer.
 */
public class EscapeCharactersChanger {

	private static final EscapeCharactersChanger instance = new EscapeCharactersChanger();
	private static final String AMPERSAND = "&";
	private static final String LESS_THAN = "<";
	private static final String GREATER_THAN = ">";
	private static final String QUOTATION = "\"";
	private static final String APOSTROPHE = "'";
	private static final String AMPERSAND_ESCAPE = "&amp;";
	private static final String LESS_THAN_ESCAPE = "&lt;";
	private static final String GREATER_THAN_ESCAPE = "&gt;";
	private static final String QUOTATION_ESCAPE = "&quot;";
	private static final String APOSTROPHE_ESCAPE = "&apos;";

	private EscapeCharactersChanger() {
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static EscapeCharactersChanger getInstance() {
		return instance;
	}

	/**
	 * Change characters string.
	 *
	 * @param text the text
	 * @return the string
	 */
	public String changeCharacters(String text) {
		text = text.replace(AMPERSAND, AMPERSAND_ESCAPE);
		text = text.replace(LESS_THAN, LESS_THAN_ESCAPE);
		text = text.replace(GREATER_THAN, GREATER_THAN_ESCAPE);
		text = text.replace(QUOTATION, QUOTATION_ESCAPE);
		text = text.replace(APOSTROPHE, APOSTROPHE_ESCAPE);
		return text;
	}

	/**
	 * Delete characters string.
	 *
	 * @param text the text
	 * @return the string
	 */
	public String deleteCharacters(String text) {
		text = text.replace(AMPERSAND, "");
		text = text.replace(LESS_THAN, "");
		text = text.replace(GREATER_THAN, "");
		text = text.replace(QUOTATION, "");
		text = text.replace(APOSTROPHE, "");
		return text;
	}
}

package by.tananushka.project.parsing;

/**
 * The type Parsing exception.
 */
public class ParsingException extends Exception {

	/**
	 * Instantiates a new Parsing exception.
	 */
	public ParsingException() {
	}

	/**
	 * Instantiates a new Parsing exception.
	 *
	 * @param message the message
	 */
	public ParsingException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new Parsing exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public ParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new Parsing exception.
	 *
	 * @param cause the cause
	 */
	public ParsingException(Throwable cause) {
		super(cause);
	}
}

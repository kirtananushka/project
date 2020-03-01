package by.tananushka.project.service;

/**
 * The type Service exception.
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new Service exception.
	 */
	public ServiceException() {
	}

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param message the message
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param cause the cause
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}
}

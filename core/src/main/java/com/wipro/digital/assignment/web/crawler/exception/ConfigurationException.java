
package com.wipro.digital.assignment.web.crawler.exception;

/**
 * The Class ConfigurationException.
 */
public class ConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 3058923492961936559L;

	/**
	 * Constructor accepts exception message.
	 *
	 * @param message
	 *            exception message
	 */
	public ConfigurationException(final String message) {
		super(message);
	}

	/**
	 * Constructor accepts exception message and Throwable cause.
	 *
	 * @param message
	 *            exception message
	 * @param cause
	 *            Throwable cause
	 */
	public ConfigurationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor accepts Throwable cause.
	 *
	 * @param cause
	 *            Throwable cause
	 */
	public ConfigurationException(final Throwable cause) {
		super(cause);
	}
}

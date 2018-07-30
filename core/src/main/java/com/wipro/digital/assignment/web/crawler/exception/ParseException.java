package com.wipro.digital.assignment.web.crawler.exception;

/**
 * The Class ParseException.
 */
public class ParseException extends RuntimeException {

	private static final long serialVersionUID = 3853807408887046090L;

	/**
	 * Constructor accepts exception message.
	 *
	 * @param message
	 *            exception message
	 */
	public ParseException(final String message) {
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
	public ParseException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor accepts Throwable cause.
	 *
	 * @param cause
	 *            Throwable cause
	 */
	public ParseException(final Throwable cause) {
		super(cause);
	}
}

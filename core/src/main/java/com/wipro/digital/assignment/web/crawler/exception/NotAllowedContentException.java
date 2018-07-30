package com.wipro.digital.assignment.web.crawler.exception;

/**
 * The Class NotAllowedContentException.
 */
public class NotAllowedContentException extends RuntimeException {

	private static final long serialVersionUID = -2914141239190583682L;

	/**
	 * Constructor accepts exception message.
	 *
	 * @param message
	 *            exception message
	 */
	public NotAllowedContentException(final String message) {
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
	public NotAllowedContentException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor accepts Throwable cause.
	 *
	 * @param cause
	 *            Throwable cause
	 */
	public NotAllowedContentException(final Throwable cause) {
		super(cause);
	}
}
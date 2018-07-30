package com.wipro.digital.assignment.web.crawler.exception;

public class CrawlerException extends RuntimeException {

	private static final long serialVersionUID = 943922651299458668L;

	/**
	 * Constructor accepts exception message.
	 *
	 * @param message
	 *            exception message
	 */
	public CrawlerException(final String message) {
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
	public CrawlerException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor accepts Throwable cause.
	 *
	 * @param cause
	 *            Throwable cause
	 */
	public CrawlerException(final Throwable cause) {
		super(cause);
	}
}
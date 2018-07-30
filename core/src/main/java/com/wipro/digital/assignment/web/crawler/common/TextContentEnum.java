package com.wipro.digital.assignment.web.crawler.common;

/**
 * The Enum TextContentEnum.
 */
public enum TextContentEnum {

	/** The text. */
	TEXT("text"),
	/** The html. */
	HTML("html");

	/** The content type. */
	String contentType;

	/**
	 * Instantiates a new text content enum.
	 *
	 * @param contentType
	 *            the content type
	 */
	private TextContentEnum(String contentType) {
		this.contentType = contentType;
	}
}

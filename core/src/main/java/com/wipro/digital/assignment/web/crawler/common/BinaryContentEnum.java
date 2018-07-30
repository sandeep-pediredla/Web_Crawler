package com.wipro.digital.assignment.web.crawler.common;

/**
 * The Enum BinaryContentEnum.
 */
public enum BinaryContentEnum {

	/** The image. */
	IMAGE("image"),
	/** The audio. */
	AUDIO("audio"),
	/** The video. */
	VIDEO("video"),
	/** The application. */
	APPLICATION("application");

	/** The content type. */
	String contentType;

	/**
	 * Instantiates a new binary content enum.
	 *
	 * @param contentType
	 *            the content type
	 */
	private BinaryContentEnum(String contentType) {
		this.contentType = contentType;
	}
}

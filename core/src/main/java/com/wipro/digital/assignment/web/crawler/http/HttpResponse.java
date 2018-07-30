package com.wipro.digital.assignment.web.crawler.http;

import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;

/**
 * The Class HttpResponse.
 */
public class HttpResponse {
	/**
	 * Obtains the status line of this response. The status line can be set
	 * using one of the {@link #setStatusLine setStatusLine} methods, or it can
	 * be initialized in a constructor.
	 *
	 * @return the status line, or {@code null} if not yet set
	 */
	private StatusLine statusLine;
	/**
	 * Obtains the message entity of this response, if any. The entity is
	 * provided by calling {@link #setEntity setEntity}.
	 *
	 * @return the response entity, or {@code null} if there is none
	 */
	private HttpEntity entity;

	/**
	 * Obtains the locale of this response. The locale is used to determine the
	 * reason phrase for the {@link #setStatusCode status code}. It can be
	 * changed using {@link #setLocale setLocale}.
	 *
	 * @return the locale of this response, never {@code null}
	 */
	private Locale locale;
	/**
	 * Returns the protocol version this message is compatible with.
	 */
	private ProtocolVersion protocolVersion;
	/**
	 * Returns all the headers of this message. Headers are orderd in the
	 * sequence they will be sent over a connection.
	 *
	 * @return all the headers of this message
	 */
	private Header[] allHeaders;

	/**
	 * The content of this page in binary format.
	 */
	private byte[] contentData;

	/**
	 * Returns an iterator of all the headers.
	 *
	 * @return Iterator that returns Header objects in the sequence they are
	 *         sent over a connection.
	 */
	private HeaderIterator headerIterator;

	/** The content type. */
	private String contentType;

	/** The content encoding. */
	private String contentEncoding;

	/** The content charset. */
	private String contentCharset;

	/**
	 * Gets the status line.
	 *
	 * @return the status line
	 */
	public StatusLine getStatusLine() {
		return statusLine;
	}

	/**
	 * Sets the status line.
	 *
	 * @param statusLine
	 *            the new status line
	 */
	public void setStatusLine(final StatusLine statusLine) {
		this.statusLine = statusLine;
	}

	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	public HttpEntity getEntity() {
		return entity;
	}

	/**
	 * Sets the entity.
	 *
	 * @param entity
	 *            the new entity
	 */
	public void setEntity(final HttpEntity entity) {
		this.entity = entity;
	}

	/**
	 * Gets the locale.
	 *
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Sets the locale.
	 *
	 * @param locale
	 *            the new locale
	 */
	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	/**
	 * Gets the protocol version.
	 *
	 * @return the protocol version
	 */
	public ProtocolVersion getProtocolVersion() {
		return protocolVersion;
	}

	/**
	 * Sets the protocol version.
	 *
	 * @param protocolVersion
	 *            the new protocol version
	 */
	public void setProtocolVersion(final ProtocolVersion protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	/**
	 * Gets the all headers.
	 *
	 * @return the all headers
	 */
	public Header[] getAllHeaders() {
		return allHeaders;
	}

	/**
	 * Sets the all headers.
	 *
	 * @param allHeaders
	 *            the new all headers
	 */
	public void setAllHeaders(final Header[] allHeaders) {
		this.allHeaders = allHeaders;
	}

	/**
	 * Gets the header iterator.
	 *
	 * @return the header iterator
	 */
	public HeaderIterator getHeaderIterator() {
		return headerIterator;
	}

	/**
	 * Sets the header iterator.
	 *
	 * @param headerIterator
	 *            the new header iterator
	 */
	public void setHeaderIterator(final HeaderIterator headerIterator) {
		this.headerIterator = headerIterator;
	}

	/**
	 * Gets the content data.
	 *
	 * @return the content data
	 */
	public byte[] getContentData() {
		return contentData;
	}

	/**
	 * Sets the content data.
	 *
	 * @param contentData
	 *            the new content data
	 */
	public void setContentData(final byte[] contentData) {
		this.contentData = contentData;
	}

	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the content type.
	 *
	 * @param contentType
	 *            the new content type
	 */
	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Gets the content encoding.
	 *
	 * @return the content encoding
	 */
	public String getContentEncoding() {
		return contentEncoding;
	}

	/**
	 * Sets the content encoding.
	 *
	 * @param contentEncoding
	 *            the new content encoding
	 */
	public void setContentEncoding(final String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	/**
	 * Gets the content charset.
	 *
	 * @return the content charset
	 */
	public String getContentCharset() {
		return contentCharset;
	}

	/**
	 * Sets the content charset.
	 *
	 * @param contentCharset
	 *            the new content charset
	 */
	public void setContentCharset(final String contentCharset) {
		this.contentCharset = contentCharset;
	}
}

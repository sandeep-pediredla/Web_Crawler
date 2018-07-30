
package com.wipro.digital.assignment.web.crawler.job;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * RequestUrl contains information to crawl.
 */
public class RequestUrl implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2927420424147642079L;

	/** The url. */
	private String url;

	/** The method. */
	private String method;

	/** The host. */
	private String host;

	/** The path. */
	private String path;

	/**
	 * Store additional information in extras.
	 */
	private Map<String, String> extras = new HashMap<String, String>();;

	/** cookies for current url, if not set use Site's cookies. */
	private Map<String, String> cookies = new HashMap<String, String>();

	/** The headers. */
	private Map<String, String> headers = new HashMap<String, String>();

	/** The binary content. */
	private boolean binaryContent = false;

	/** The charset. */
	private String charset;

	/**
	 * Instantiates a new request url.
	 */
	public RequestUrl() {
	}

	/**
	 * Instantiates a new request url.
	 *
	 * @param url
	 *            the url
	 */
	public RequestUrl(final String url) {
		this.url = url;
	}

	/**
	 * Gets the extra.
	 *
	 * @param key
	 *            the key
	 * @return the extra
	 */
	public Object getExtra(final String key) {
		if (extras == null) {
			return null;
		}
		return extras.get(key);
	}

	/**
	 * Put extra.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the request url
	 */
	public RequestUrl putExtra(final String key, final String value) {
		if (extras == null) {
			extras = new HashMap<String, String>();
		}
		extras.put(key, value);
		return this;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Gets the extras.
	 *
	 * @return the extras
	 */
	public Map<String, String> getExtras() {
		return extras;
	}

	/**
	 * Sets the extras.
	 *
	 * @param extras
	 *            the extras
	 * @return the request url
	 */
	public RequestUrl setExtras(final Map<String, String> extras) {
		this.extras = extras;
		return this;
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the url
	 * @return the request url
	 */
	public RequestUrl setUrl(final String url) {
		this.url = url;
		return this;
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Sets the method.
	 *
	 * @param method
	 *            the method
	 * @return the request url
	 */
	public RequestUrl setMethod(final String method) {
		this.method = method;
		return this;
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		int result = url != null ? url.hashCode() : 0;
		result = 31 * result + (method != null ? method.hashCode() : 0);
		return result;
	}

	/**
	 * Equals.
	 *
	 * @param o
	 *            the o
	 * @return true, if successful
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final RequestUrl requestUrl = (RequestUrl) o;

		if (url != null ? !url.equals(requestUrl.url) : requestUrl.url != null) {
			return false;
		}
		return method != null ? method.equals(requestUrl.method) : requestUrl.method == null;
	}

	/**
	 * Adds the cookie.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the request url
	 */
	public RequestUrl addCookie(final String name, final String value) {
		cookies.put(name, value);
		return this;
	}

	/**
	 * Adds the header.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the request url
	 */
	public RequestUrl addHeader(final String name, final String value) {
		headers.put(name, value);
		return this;
	}

	/**
	 * Gets the cookies.
	 *
	 * @return the cookies
	 */
	public Map<String, String> getCookies() {
		return cookies;
	}

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * Checks if is binary content.
	 *
	 * @return true, if is binary content
	 */
	public boolean isBinaryContent() {
		return binaryContent;
	}

	/**
	 * Sets the binary content.
	 *
	 * @param binaryContent
	 *            the binary content
	 * @return the request url
	 */
	public RequestUrl setBinaryContent(final boolean binaryContent) {
		this.binaryContent = binaryContent;
		return this;
	}

	/**
	 * Gets the charset.
	 *
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * Sets the charset.
	 *
	 * @param charset
	 *            the charset
	 * @return the request url
	 */
	public RequestUrl setCharset(final String charset) {
		this.charset = charset;
		return this;
	}

	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the host.
	 *
	 * @param host
	 *            the host to set
	 */
	public void setHost(final String host) {
		this.host = host;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path.
	 *
	 * @param path
	 *            the path to set
	 */
	public void setPath(final String path) {
		this.path = path;
	}
}

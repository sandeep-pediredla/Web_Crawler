package com.wipro.digital.assignment.web.crawler.html.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.ByteArrayBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wipro.digital.assignment.web.crawler.bean.JobInformation;
import com.wipro.digital.assignment.web.crawler.html.PageInformation;
import com.wipro.digital.assignment.web.crawler.http.HttpResponse;
import com.wipro.digital.assignment.web.crawler.job.RequestUrl;

/**
 * The Class HtmPageContent.
 */
public class HtmPageContent {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(HtmPageContent.class);

	/** The request url. */
	private RequestUrl requestUrl;

	/** The http response. */
	private HttpResponse httpResponse;

	/** The page info. */
	private PageInformation pageInfo;

	/** The html. */
	private String html;

	/** The text. */
	private String text;

	/** The title. */
	private String title;

	/** The meta tags. */
	private Map<String, String> metaTags;

	/** The outgoing urls. */
	private Set<PageInformation> outgoingUrls;

	/** The text content. */
	private String textContent;

	/** The filter set. */
	private Set<String> filterSet;

	/** The job details. */
	private JobInformation jobDetails;

	/** Redirection flag. */
	protected boolean redirect;

	/** The URL to which this page will be redirected to. */
	protected String redirectedToUrl;

	/** Status of the page. */
	protected int statusCode;

	/**
	 * Whether the content was truncated because the received data exceeded the
	 * imposed maximum.
	 */
	protected boolean truncated = false;

	/**
	 * Instantiates a new htm page content.
	 *
	 * @param requestUrl
	 *            the request url
	 */
	public HtmPageContent(final RequestUrl requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * Read contents from an entity, with a specified maximum. This is a
	 * replacement of EntityUtils.toByteArray because that function does not
	 * impose a maximum size.
	 *
	 * @param entity
	 *            The entity from which to read
	 * @param maxBytes
	 *            The maximum number of bytes to read
	 * @return A byte array containing maxBytes or fewer bytes read from the
	 *         entity
	 *
	 * @throws IOException
	 *             Thrown when reading fails for any reason
	 */
	protected byte[] toByteArray(final HttpEntity entity, final int maxBytes) throws IOException {
		if (entity == null) {
			return new byte[0];
		}
		try (InputStream is = entity.getContent()) {
			final int size = (int) entity.getContentLength();
			int readBufferLength = size;

			if (readBufferLength <= 0) {
				readBufferLength = 4096;
			}
			// in case when the maxBytes is less than the actual page size
			readBufferLength = Math.min(readBufferLength, maxBytes);

			// We allocate the buffer with either the actual size of the entity
			// (if
			// available)
			// or with the default 4KiB if the server did not return a value to
			// avoid
			// allocating
			// the full maxBytes (for the cases when the actual size will be
			// smaller than
			// maxBytes).
			final ByteArrayBuffer buffer = new ByteArrayBuffer(readBufferLength);

			final byte[] tmpBuff = new byte[4096];
			int dataLength;

			while ((dataLength = is.read(tmpBuff)) != -1) {
				if (maxBytes > 0 && (buffer.length() + dataLength) > maxBytes) {
					truncated = true;
					dataLength = maxBytes - buffer.length();
				}
				buffer.append(tmpBuff, 0, dataLength);
				if (truncated) {
					break;
				}
			}
			return buffer.toByteArray();
		}
	}

	/**
	 * Read contents from an entity .
	 *
	 * @param entity
	 *            The entity from which to read
	 * @return A byte array containing maxBytes or fewer bytes read from the
	 *         entity
	 * @throws UnsupportedOperationException
	 *             the unsupported operation exception
	 * @throws IOException
	 *             Thrown when reading fails for any reason
	 */
	protected byte[] toByteArray(final HttpEntity entity) throws UnsupportedOperationException, IOException {
		if (entity == null) {
			return new byte[0];
		}
		try (InputStream is = entity.getContent()) {
			final int size = (int) entity.getContentLength();
			int readBufferLength = size;

			if (readBufferLength <= 0) {
				readBufferLength = 4096;
			}

			// We allocate the buffer with either the actual size of the entity
			// (if
			// available)
			// or with the default 4KiB if the server did not return a value to
			// avoid
			// allocating
			// the full maxBytes (for the cases when the actual size will be
			// smaller than
			// maxBytes).
			final ByteArrayBuffer buffer = new ByteArrayBuffer(readBufferLength);

			final byte[] tmpBuff = new byte[4096];
			int dataLength;

			while ((dataLength = is.read(tmpBuff)) != -1) {
				buffer.append(tmpBuff, 0, dataLength);
				if (truncated) {
					break;
				}
			}
			return buffer.toByteArray();
		}
	}

	/**
	 * Sets the http response.
	 *
	 * @param httpResponse
	 *            the new http response
	 */
	public void setHttpResponse(final HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
		load(httpResponse);
	}

	/**
	 * Loads the content of this page from a fetched HttpEntity.
	 *
	 * @param httpResponse
	 *            the http response
	 */
	public void load(final HttpResponse httpResponse) {
		final HttpEntity entity = httpResponse.getEntity();

		final Header type = entity.getContentType();
		if (type != null) {
			httpResponse.setContentType(type.getValue());
		}

		final Header encoding = entity.getContentEncoding();
		if (encoding != null) {
			httpResponse.setContentEncoding(encoding.getValue());
		}

		Charset charset;
		try {
			charset = ContentType.getOrDefault(entity).getCharset();
		} catch (final Exception e) {
			logger.warn("parse charset failed: {}", e.getMessage());
			charset = Charset.forName("UTF-8");
		}

		if (charset != null) {
			httpResponse.setContentCharset(charset.displayName());
		}

		byte[] contentData = null;
		try {
			contentData = toByteArray(entity);
		} catch (final UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpResponse.setContentData(contentData);
	}

	/**
	 * Checks if is redirect.
	 *
	 * @return true, if is redirect
	 */
	public boolean isRedirect() {
		return redirect;
	}

	/**
	 * Sets the redirect.
	 *
	 * @param redirect
	 *            the new redirect
	 */
	public void setRedirect(final boolean redirect) {
		this.redirect = redirect;
	}

	/**
	 * Gets the redirected to url.
	 *
	 * @return the redirected to url
	 */
	public String getRedirectedToUrl() {
		return redirectedToUrl;
	}

	/**
	 * Sets the redirected to url.
	 *
	 * @param redirectedToUrl
	 *            the new redirected to url
	 */
	public void setRedirectedToUrl(final String redirectedToUrl) {
		this.redirectedToUrl = redirectedToUrl;
	}

	/**
	 * Gets the status code.
	 *
	 * @return the status code
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets the status code.
	 *
	 * @param statusCode
	 *            the new status code
	 */
	public void setStatusCode(final int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Checks if is truncated.
	 *
	 * @return true, if is truncated
	 */
	public boolean isTruncated() {
		return truncated;
	}

	/**
	 * Gets the http response.
	 *
	 * @return the http response
	 */
	public HttpResponse getHttpResponse() {
		if (httpResponse == null) {
			httpResponse = new HttpResponse();
		}
		return httpResponse;
	}

	/**
	 * Gets the request url.
	 *
	 * @return the request url
	 */
	public RequestUrl getRequestUrl() {
		return requestUrl;
	}

	/**
	 * Sets the request url.
	 *
	 * @param requestUrl
	 *            the new request url
	 */
	public void setRequestUrl(final RequestUrl requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * Gets the page info.
	 *
	 * @return the page info
	 */
	public PageInformation getPageInfo() {
		return pageInfo;
	}

	/**
	 * Sets the page info.
	 *
	 * @param pageInfo
	 *            the new page info
	 */
	public void setPageInfo(final PageInformation pageInfo) {
		this.pageInfo = pageInfo;
	}

	/**
	 * Gets the html.
	 *
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * Sets the html.
	 *
	 * @param html
	 *            the new html
	 */
	public void setHtml(final String html) {
		this.html = html;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * Gets the meta tags.
	 *
	 * @return the meta tags
	 */
	public Map<String, String> getMetaTags() {
		return metaTags;
	}

	/**
	 * Sets the meta tags.
	 *
	 * @param metaTags
	 *            the meta tags
	 */
	public void setMetaTags(final Map<String, String> metaTags) {
		this.metaTags = metaTags;
	}

	/**
	 * Gets the outgoing urls.
	 *
	 * @return the outgoing urls
	 */
	public Set<PageInformation> getOutgoingUrls() {
		if (outgoingUrls == null) {
			outgoingUrls = new HashSet<>();
		}
		return outgoingUrls;
	}

	/**
	 * Sets the outgoing urls.
	 *
	 * @param outgoingUrls
	 *            the new outgoing urls
	 */
	public void setOutgoingUrls(final Set<PageInformation> outgoingUrls) {
		this.outgoingUrls = outgoingUrls;
	}

	/**
	 * Sets the truncated.
	 *
	 * @param truncated
	 *            the new truncated
	 */
	public void setTruncated(final boolean truncated) {
		this.truncated = truncated;
	}

	/**
	 * Gets the text content.
	 *
	 * @return the text content
	 */
	public String getTextContent() {
		return textContent;
	}

	/**
	 * Sets the text content.
	 *
	 * @param textContent
	 *            the new text content
	 */
	public void setTextContent(final String textContent) {
		this.textContent = textContent;
	}

	/**
	 * Gets the filter set.
	 *
	 * @return the filterSet
	 */
	public Set<String> getFilterSet() {
		return filterSet;
	}

	/**
	 * Sets the filter set.
	 *
	 * @param filterSet
	 *            the filterSet to set
	 */
	public void setFilterSet(final Set<String> filterSet) {
		this.filterSet = filterSet;
	}

	/**
	 * Gets the job details.
	 *
	 * @return the jobDetails
	 */
	public JobInformation getJobDetails() {
		return jobDetails;
	}

	/**
	 * Sets the job details.
	 *
	 * @param jobDetails
	 *            the jobDetails to set
	 */
	public void setJobDetails(final JobInformation jobDetails) {
		this.jobDetails = jobDetails;
	}
}

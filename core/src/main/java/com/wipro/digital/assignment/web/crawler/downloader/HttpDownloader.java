
package com.wipro.digital.assignment.web.crawler.downloader;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map.Entry;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wipro.digital.assignment.web.crawler.html.model.HtmPageContent;
import com.wipro.digital.assignment.web.crawler.job.RequestUrl;

/**
 * The Class HttpDownloader.
 */
public class HttpDownloader implements Downloader {

	/** The logger. */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/** The Constant CONNECTION_TIMEOUT_MS. */
	private static final int CONNECTION_TIMEOUT_MS = 5000;

	/**
	 * Gets the URI from request.
	 *
	 * @param requestUrl
	 *            the request url
	 * @return the URI from request
	 */
	private URI getURIFromRequest(final RequestUrl requestUrl) {
		URI uri = null;
		try {
			final URIBuilder uriBuilder = new URIBuilder().setScheme(requestUrl.getMethod())
					.setHost(requestUrl.getHost()).setPath(requestUrl.getPath());

			for (final Entry<String, String> entry : requestUrl.getExtras().entrySet()) {
				uriBuilder.setParameter(entry.getKey(), entry.getValue());
			}

			uri = uriBuilder.build();
		} catch (final URISyntaxException e) {
			logger.error("URISyntaxException", e);
			e.printStackTrace();
		}
		return uri;
	}

	/**
	 * Download.
	 *
	 * @param requestUrl
	 *            the request url
	 * @return the htm page content
	 */
	@Override
	public HtmPageContent download(final RequestUrl requestUrl) {

		logger.error("Requested url is " + requestUrl);
		final HtmPageContent pageContent = new HtmPageContent(requestUrl);
		final CloseableHttpClient httpclient = HttpClients.createDefault();
		// final HttpClientRequestContext requestContext =
		// httpUriRequestConverter.convert(request, task.getSite(), proxy);
		final URI uri = getURIFromRequest(requestUrl);
		final RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT_MS)
				.setConnectionRequestTimeout(CONNECTION_TIMEOUT_MS).setSocketTimeout(CONNECTION_TIMEOUT_MS).build();
		final HttpGet request = new HttpGet(uri);
		request.setConfig(config);

		try {
			final CloseableHttpResponse response = httpclient.execute(request);
			pageContent.getHttpResponse().setAllHeaders(response.getAllHeaders());
			pageContent.getHttpResponse().setEntity(response.getEntity());
			pageContent.getHttpResponse().setLocale(response.getLocale());
			pageContent.getHttpResponse().setProtocolVersion(response.getProtocolVersion());
			pageContent.getHttpResponse().setStatusLine(response.getStatusLine());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return pageContent;
	}

}

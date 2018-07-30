package com.wipro.digital.assignment.web.crawler.parser;

import static com.wipro.digital.assignment.web.crawler.common.Constants.CONTENT_ENCODING;
import static com.wipro.digital.assignment.web.crawler.common.Constants.EMPTY;
import static com.wipro.digital.assignment.web.crawler.common.Constants.HTML;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.tika.metadata.Metadata;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wipro.digital.assignment.web.crawler.common.BinaryContentEnum;
import com.wipro.digital.assignment.web.crawler.common.TextContentEnum;
import com.wipro.digital.assignment.web.crawler.conf.Configuration;
import com.wipro.digital.assignment.web.crawler.exception.NotAllowedContentException;
import com.wipro.digital.assignment.web.crawler.exception.ParseException;
import com.wipro.digital.assignment.web.crawler.html.model.HtmPageContent;

/**
 * The Class ContentParser.
 */
public class ContentParser {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(ContentParser.class);

	/**
	 * Parses the.
	 *
	 * @param pageContent
	 *            the page content
	 */
	public void parse(final HtmPageContent pageContent) {
		final Configuration conf = Configuration.getInstance();
		if (hasBinaryContent(pageContent.getHttpResponse().getContentType())) { // BINARY
			parseBinaryContent(pageContent, conf);
		} else if (hasPlainTextContent(pageContent.getHttpResponse().getContentType())) { // plain
																							// Text
			parseTextContent(pageContent);
		} else { // isHTML detecting the file type
			parseHtmlContent(pageContent);
		}
	}

	private void parseBinaryContent(final HtmPageContent pageContent, final Configuration conf) {
		final byte[] contentData = pageContent.getHttpResponse().getContentData();
		if (conf.isIncludeBinaryContentInCrawling()) {
			if (conf.isProcessBinaryContentInCrawling()) {
				pageContent.getHttpResponse().setContentData(contentData);
			} else {
				pageContent.setHtml(HTML);
			}
			if (pageContent.getHtml() == null) {
				throw new ParseException("pageContent.getHtml() == null");
			}
			// TODO
			// pageContent.setOutgoingUrls(extractUrls(pageContent.getHtml()));
		} else {
			throw new NotAllowedContentException("contains BinaryContent");
		}
	}

	private void parseHtmlContent(final HtmPageContent pageContent) {
		final String htmlStr = convertToHtml(pageContent.getHttpResponse().getEntity());
		pageContent.setHtml(htmlStr);
		final Document doc = Jsoup.parse(htmlStr);
		pageContent.setTitle(doc.title());
		pageContent.setText(doc.wholeText());
		pageContent.setHtml(htmlStr);
	}

	private void parseTextContent(final HtmPageContent pageContent) {
		try {
			final byte[] contentData = pageContent.getHttpResponse().getContentData();
			final String contentCharset = pageContent.getHttpResponse().getContentCharset();
			if (contentCharset == null) {
				pageContent.setTextContent(new String(contentData));
			} else {
				pageContent.setTextContent(new String(contentData, contentCharset));
			}
		} catch (final Exception e) {
			logger.error("Exception occured while parsing text data & exception is  {}", e);
			throw new ParseException(e);
		}
	}

	/**
	 * Gets the encoding type.
	 *
	 * @param pageContent
	 *            the page content
	 * @param metadata
	 *            the metadata
	 * @return the encoding type
	 */
	public String getEncodingType(final HtmPageContent pageContent, final Metadata metadata) {
		final String pageCharset = pageContent.getHttpResponse().getContentCharset();
		if (pageCharset == null || pageCharset.isEmpty()) {
			return metadata.get(CONTENT_ENCODING);
		}
		return pageCharset;
	}

	/**
	 * Checks for binary content.
	 *
	 * @param contentType
	 *            the content type
	 * @return true, if successful
	 */
	private static boolean hasBinaryContent(final String contentType) {
		final String typeStr = (contentType != null) ? contentType.toLowerCase() : EMPTY;

		for (final BinaryContentEnum contentEnum : BinaryContentEnum.values()) {
			if (typeStr.equalsIgnoreCase(contentEnum.name())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks for plain text content.
	 *
	 * @param contentType
	 *            the content type
	 * @return true, if successful
	 */
	private static boolean hasPlainTextContent(final String contentType) {
		final String typeStr = (contentType != null) ? contentType.toLowerCase() : EMPTY;
		if (typeStr.equalsIgnoreCase(TextContentEnum.TEXT.name())) {
			return true;
		}
		return false;
	}

	/**
	 * Convert to html.
	 *
	 * @param entity
	 *            the entity
	 * @return the string
	 */
	private String convertToHtml(final HttpEntity entity) {
		final StringBuffer result = new StringBuffer();
		try {
			if (entity != null) {
				final BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));

				String line = EMPTY;
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
			}
		} catch (final Exception e) {
			logger.error("unable to convert download html & exception is ", e);
		}
		return result.toString();
	}
}

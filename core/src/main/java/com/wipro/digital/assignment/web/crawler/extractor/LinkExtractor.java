package com.wipro.digital.assignment.web.crawler.extractor;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wipro.digital.assignment.web.crawler.html.PageInformation;
import com.wipro.digital.assignment.web.crawler.html.model.HtmPageContent;

/**
 * The Class LinkExtractor.
 */
public class LinkExtractor {

	/**
	 * Sets the out going urls.
	 *
	 * @param pageContent
	 *            the new out going urls
	 */
	public void setOutGoingUrls(final HtmPageContent pageContent) {
		final Set<PageInformation> pageInformations = new HashSet<>();
		final Document doc = Jsoup.parse(pageContent.getHtml());
		final Elements links = doc.select("a");

		for (final Element link : links) {
			final PageInformation pageInfo = new PageInformation();
			pageInfo.setUrl(link.attr("abs:href"));
			pageContent.getOutgoingUrls().add(pageInfo);
		}

		pageContent.setOutgoingUrls(pageInformations);
	}
}


package com.wipro.digital.assignment.web.crawler.filter;

import java.util.Set;
import java.util.stream.Collectors;

import com.wipro.digital.assignment.web.crawler.html.PageInformation;
import com.wipro.digital.assignment.web.crawler.html.model.HtmPageContent;

/**
 * The Class UrlFilter.
 */
public class UrlFilter {

	/**
	 * Filter records.
	 *
	 * @param pageContent
	 *            the page content
	 */
	public void filterRecords(final HtmPageContent pageContent) {
		Set<PageInformation> outgoingUrls = pageContent.getOutgoingUrls();

		for (final String filterStr : pageContent.getFilterSet()) {
			outgoingUrls = outgoingUrls.parallelStream().filter(x -> !filterStr.equals(x)).collect(Collectors.toSet());
		}

		pageContent.setOutgoingUrls(outgoingUrls);
	}
}

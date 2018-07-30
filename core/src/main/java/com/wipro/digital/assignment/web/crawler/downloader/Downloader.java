
package com.wipro.digital.assignment.web.crawler.downloader;

import com.wipro.digital.assignment.web.crawler.html.model.HtmPageContent;
import com.wipro.digital.assignment.web.crawler.job.RequestUrl;

/**
 * Downloader is the part that downloads web pages and store in Page object.
 * <br>
 * Downloader has {@link #setThread(int)} method because downloader is always
 * the bottleneck of a crawler, there are always some mechanisms such as pooling
 * in downloader, and pool size is related to thread numbers.
 *
 */
public interface Downloader {

	/**
	 * Download.
	 *
	 * @param requestUrl
	 *            the request url
	 * @return the htm page content
	 */
	// Downloads web pages and store in Page object.
	public HtmPageContent download(RequestUrl requestUrl);

}

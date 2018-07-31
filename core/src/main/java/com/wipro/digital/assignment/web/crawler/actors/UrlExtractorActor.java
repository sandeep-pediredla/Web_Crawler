
package com.wipro.digital.assignment.web.crawler.actors;

import com.wipro.digital.assignment.web.crawler.extractor.LinkExtractor;
import com.wipro.digital.assignment.web.crawler.html.model.HtmPageContent;

import akka.actor.UntypedAbstractActor;

public class URLExtractorActor extends UntypedAbstractActor {

	/**
	 * On receive.
	 *
	 * @param message
	 *            the message
	 */
	@Override
	public void onReceive(final Object message) {
		if (message instanceof HtmPageContent) {
			final HtmPageContent pageContent = (HtmPageContent) message;
			final LinkExtractor linkExtractor = new LinkExtractor();
			linkExtractor.setOutGoingUrls(pageContent);
			getSender().tell(pageContent, getSelf());
		}
	}
}

package com.wipro.digital.assignment.web.crawler.actors;

import com.wipro.digital.assignment.web.crawler.html.model.HtmPageContent;

import akka.actor.UntypedAbstractActor;

/**
 * The Class UrlFilterActor.
 */
public class UrlFilterActor extends UntypedAbstractActor {

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
			// final UrlFilter filterRecords = new UrlFilter();
			// / filterRecords.filterRecords(pageContent);
			getSender().tell(pageContent, getSelf());
		}
	}
}
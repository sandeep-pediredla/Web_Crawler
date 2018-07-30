
package com.wipro.digital.assignment.web.crawler.actors;

import com.wipro.digital.assignment.web.crawler.html.model.HtmPageContent;
import com.wipro.digital.assignment.web.crawler.job.RequestUrl;

import akka.actor.UntypedAbstractActor;

/**
 * The Class DocumentIndexActor.
 */
public class DocumentIndexActor extends UntypedAbstractActor {

	/**
	 * On receive.
	 *
	 * @param message
	 *            the message
	 */
	@Override
	public void onReceive(final Object message) {
		if (message instanceof RequestUrl) {
			final HtmPageContent pageContent = (HtmPageContent) message;
			getSender().tell(pageContent, getSelf());
		}
	}
}
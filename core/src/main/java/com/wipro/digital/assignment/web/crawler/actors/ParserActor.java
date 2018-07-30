
package com.wipro.digital.assignment.web.crawler.actors;

import com.wipro.digital.assignment.web.crawler.html.model.HtmPageContent;
import com.wipro.digital.assignment.web.crawler.parser.ContentParser;

import akka.actor.UntypedAbstractActor;

/**
 * The Class ParserActor.
 */
public class ParserActor extends UntypedAbstractActor {

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
			final ContentParser contentParser = new ContentParser();
			contentParser.parse(pageContent);
			getSender().tell(pageContent, getSelf());
		}
	}
}
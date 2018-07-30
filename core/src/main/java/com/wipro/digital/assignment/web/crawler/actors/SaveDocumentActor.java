
package com.wipro.digital.assignment.web.crawler.actors;

import com.wipro.digital.assignment.web.crawler.conf.Configuration;
import com.wipro.digital.assignment.web.crawler.document.SaveDocument;
import com.wipro.digital.assignment.web.crawler.html.model.HtmPageContent;

import akka.actor.UntypedAbstractActor;

/**
 * The Class SaveDocumentActor.
 */
public class SaveDocumentActor extends UntypedAbstractActor {

	/** The conf. */
	private Configuration conf = Configuration.getInstance();

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
			final SaveDocument docToSave = new SaveDocument();
			docToSave.saveDocumentToFolder(pageContent, conf.getDocumentStorageFolder());
			getSender().tell(pageContent, getSelf());
		}
	}
}

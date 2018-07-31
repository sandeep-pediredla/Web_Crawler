
package com.wipro.digital.assignment.web.crawler.actors;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.LockObtainFailedException;

import com.wipro.digital.assignment.web.crawler.document.DocumentIndex;

import akka.actor.UntypedAbstractActor;
import scala.Tuple2;

public class DocumentIndexActor extends UntypedAbstractActor {

	/**
	 * On receive.
	 *
	 * @param message
	 *            the message
	 */
	@Override
	public void onReceive(final Object message) {

		if (message instanceof Tuple2) {
			Tuple2<String, String> actionEvent = (Tuple2<String, String>) message;

			DocumentIndex docIndex = new DocumentIndex();
			try {
				docIndex.createIndex(actionEvent._1, actionEvent._2);
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LockObtainFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
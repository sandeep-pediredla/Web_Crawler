
package com.wipro.digital.assignment.web.crawler.actors;

import com.wipro.digital.assignment.web.crawler.downloader.Downloader;
import com.wipro.digital.assignment.web.crawler.downloader.HttpDownloader;
import com.wipro.digital.assignment.web.crawler.html.model.HtmPageContent;
import com.wipro.digital.assignment.web.crawler.job.RequestUrl;

import akka.actor.UntypedAbstractActor;

public class DownloaderActor extends UntypedAbstractActor {

	@Override
	public void onReceive(final Object message) {
		if (message instanceof RequestUrl) {
			final RequestUrl requestUrl = (RequestUrl) message;
			final Downloader downloader = new HttpDownloader();
			final HtmPageContent pageContent = downloader.download(requestUrl);
			getSender().tell(pageContent, getSelf());
		}
	}
}
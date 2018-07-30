
package com.wipro.digital.assignment.web.crawler;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wipro.digital.assignment.web.crawler.actors.DocumentIndexActor;
import com.wipro.digital.assignment.web.crawler.actors.DownloaderActor;
import com.wipro.digital.assignment.web.crawler.actors.ParserActor;
import com.wipro.digital.assignment.web.crawler.actors.SaveDocumentActor;
import com.wipro.digital.assignment.web.crawler.actors.UrlExtractorActor;
import com.wipro.digital.assignment.web.crawler.actors.UrlFilterActor;
import com.wipro.digital.assignment.web.crawler.bean.JobInformation;
import com.wipro.digital.assignment.web.crawler.conf.Configuration;
import com.wipro.digital.assignment.web.crawler.html.PageInformation;
import com.wipro.digital.assignment.web.crawler.html.model.HtmPageContent;
import com.wipro.digital.assignment.web.crawler.job.RequestUrl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;

public class JobManager {

	private static final String SAVE_DOCUMENT_ACTOR = "SaveDocumentActor";
	private static final String DOCUMENT_INDEX_ACTOR = "DocumentIndexActor";
	private static final String URL_FILTER_ACTOR = "UrlFilterActor";
	private static final String URL_EXTRACTOR_ACTOR = "UrlExtractorActor";
	private static final String PARSER_ACTOR = "ParserActor";
	private static final String DOWNLOADER_ACTOR = "DownloaderActor";
	private static final String PATH = "/";
	private static final String CRAWLER_SYSTEM = "Crawler-System";

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(JobManager.class);

	/** The filter set. */
	private Set<String> filterSet;

	/** The job details. */
	private JobInformation jobDetails;

	private String method;

	/**
	 * Instantiates a new job manager.
	 *
	 * @param domainSet
	 *            the domain set
	 * @param filterSet
	 *            the filter set
	 * @param jobDetails
	 *            the job details
	 */
	public JobManager(final Set<String> filterSet, final JobInformation jobDetails) {
		super();
		this.filterSet = filterSet;
		this.jobDetails = jobDetails;
	}

	/**
	 * Crawl pages.
	 */
	public void crawlPages(Set<String> urlCrawlSet) {

		final Configuration conf = Configuration.getInstance();
		method = conf.getMethod();
		final Timeout timeout = new Timeout(conf.getSocketTimeout(), TimeUnit.SECONDS);
		// Create an Akka system
		final ActorSystem system = ActorSystem.create(CRAWLER_SYSTEM);

		for (final String domain : urlCrawlSet) {
			final RequestUrl requestUrl = new RequestUrl();
			requestUrl.setMethod(method);
			requestUrl.setHost(domain);
			requestUrl.setPath(PATH);
			crawlUrlData(timeout, system, requestUrl);
		}
	}

	/**
	 * Crawl url data.
	 *
	 * @param timeout
	 *            the timeout
	 * @param system
	 *            the system
	 * @param requestUrl
	 *            the request url
	 */
	private void crawlUrlData(final Timeout timeout, final ActorSystem system, final RequestUrl requestUrl) {

		final ActorRef parserRef = system.actorOf(Props.create(ParserActor.class), PARSER_ACTOR);
		final ActorRef urlExtractorRef = system.actorOf(Props.create(UrlExtractorActor.class), URL_EXTRACTOR_ACTOR);
		final ActorRef urlFilterRef = system.actorOf(Props.create(UrlFilterActor.class), URL_FILTER_ACTOR);

		final HtmPageContent downloadEvent = downloadPage(timeout, requestUrl, system);
		final Object parserEvent = sendEvent(parserRef, downloadEvent, timeout);

		final HtmPageContent pageUrlContent = (HtmPageContent) sendEvent(urlExtractorRef, parserEvent, timeout);

		final HtmPageContent urlFilterEvent = (HtmPageContent) sendEvent(urlFilterRef, pageUrlContent, timeout);

		crawlUrlLinks(timeout, system, requestUrl, urlFilterRef);

		for (final PageInformation pageInfo : urlFilterEvent.getOutgoingUrls()) {

			final RequestUrl urlLink = new RequestUrl();
			urlLink.setMethod(method);
			urlLink.setHost(pageInfo.getUrl());
			urlLink.setPath(PATH);
			crawlUrlData(timeout, system, urlLink);
		}
	}

	private HtmPageContent downloadPage(final Timeout timeout, final RequestUrl requestUrl, final ActorSystem system) {
		final ActorRef downloaderRef = system.actorOf(Props.create(DownloaderActor.class), DOWNLOADER_ACTOR);
		final HtmPageContent downloadEvent = (HtmPageContent) sendEvent(downloaderRef, requestUrl, timeout);
		downloadEvent.setFilterSet(filterSet);
		downloadEvent.setJobDetails(jobDetails);
		return downloadEvent;
	}

	/**
	 * Crawl url links.
	 *
	 * @param timeout
	 *            the timeout
	 * @param system
	 *            the system
	 * @param requestUrl
	 *            the request url
	 * @param urlFilterRef
	 *            the url filter ref
	 */
	private void crawlUrlLinks(final Timeout timeout, final ActorSystem system, final RequestUrl requestUrl,
			final ActorRef urlFilterRef) {

		final ActorRef documentIndexRef = system.actorOf(Props.create(DocumentIndexActor.class), DOCUMENT_INDEX_ACTOR);
		final ActorRef saveDocumentRef = system.actorOf(Props.create(SaveDocumentActor.class), SAVE_DOCUMENT_ACTOR);

		final Object documentIndexEvent = sendEvent(documentIndexRef, urlFilterRef, timeout);
		final Object saveDocumentEvent = sendEvent(saveDocumentRef, urlFilterRef, timeout);
	}

	/**
	 * Send event.
	 *
	 * @param actorRef
	 *            the actor ref
	 * @param passObjectRef
	 *            the pass object ref
	 * @param timeout
	 *            the timeout
	 * @return the object
	 */
	private Object sendEvent(final ActorRef actorRef, final Object passObjectRef, final Timeout timeout) {
		final Future<Object> future = Patterns.ask(actorRef, passObjectRef, timeout);
		try {
			return Await.result(future, timeout.duration());
		} catch (final Exception e) {
			logger.error("exception occurred while delegating message to actor name {} object {} & exception is {}",
					actorRef, passObjectRef, e);
		}
		return null;
	}
}

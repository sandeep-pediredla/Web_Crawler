
package com.wipro.digital.assignment.web.crawler;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.wipro.digital.assignment.web.crawler.actors.DocumentIndexActor;
import com.wipro.digital.assignment.web.crawler.actors.DownloaderActor;
import com.wipro.digital.assignment.web.crawler.actors.JobActor;
import com.wipro.digital.assignment.web.crawler.actors.ParserActor;
import com.wipro.digital.assignment.web.crawler.actors.SaveDocumentActor;
import com.wipro.digital.assignment.web.crawler.actors.URLExtractorActor;
import com.wipro.digital.assignment.web.crawler.actors.URLFilterActor;
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
import scala.Tuple2;
import scala.concurrent.Await;
import scala.concurrent.Future;

public class JobManager {

	private static final String JOB_ACTOR = "JobActor";
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

	private String method;
	private Timeout timeout;

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
	public JobManager(final Set<String> filterSet, Timeout timeout) {
		super();
		this.filterSet = filterSet;
		this.timeout = timeout;
	}

	/**
	 * Crawl pages.
	 */
	public void crawlPages(Set<String> urlCrawlSet, final JobInformation jobInfo) {

		final Configuration conf = Configuration.getInstance();
		method = conf.getMethod();

		Config sysConf = ConfigFactory.parseProperties(conf.getProperties());

		// Create an Akka system
		final ActorSystem system = ActorSystem.create(CRAWLER_SYSTEM, sysConf);

		for (final String domain : urlCrawlSet) {
			final RequestUrl requestUrl = new RequestUrl();
			requestUrl.setMethod(method);
			requestUrl.setHost(domain);
			requestUrl.setPath(PATH);
			crawlUrlData(system, requestUrl, jobInfo);
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
	private void crawlUrlData(final ActorSystem system, final RequestUrl requestUrl, final JobInformation jobInfo) {

		final ActorRef parserRef = system.actorOf(Props.create(ParserActor.class), PARSER_ACTOR);
		final ActorRef urlExtractorRef = system.actorOf(Props.create(URLExtractorActor.class), URL_EXTRACTOR_ACTOR);
		final ActorRef urlFilterRef = system.actorOf(Props.create(URLFilterActor.class), URL_FILTER_ACTOR);
		final ActorRef jobRef = system.actorOf(Props.create(JobActor.class), JOB_ACTOR);
		final ActorRef saveDocumentRef = system.actorOf(Props.create(SaveDocumentActor.class), SAVE_DOCUMENT_ACTOR);
		final ActorRef documentIndexRef = system.actorOf(Props.create(DocumentIndexActor.class), DOCUMENT_INDEX_ACTOR);

		final HtmPageContent downloadEvent = downloadPage(requestUrl, system, jobInfo);
		final Object parserEvent = sendEvent(parserRef, downloadEvent);

		saveDocument(saveDocumentRef, parserEvent);
		indexDocument(documentIndexRef);

		final HtmPageContent pageUrlContent = (HtmPageContent) sendEvent(urlExtractorRef, parserEvent);
		final HtmPageContent urlFilterEvent = (HtmPageContent) sendEvent(urlFilterRef, pageUrlContent);

		jobRef.tell(jobInfo, ActorRef.noSender());

		for (final PageInformation pageInfo : urlFilterEvent.getOutgoingUrls()) {
			JobInformation childJobInfo = new JobInformation();
			childJobInfo.setParentId(jobInfo.getParentId());
			final RequestUrl urlLink = new RequestUrl();
			urlLink.setMethod(method);
			urlLink.setHost(pageInfo.getUrl());
			urlLink.setPath(PATH);
			crawlUrlData(system, urlLink, childJobInfo);
		}
	}

	private void saveDocument(final ActorRef saveDocumentRef, final Object parserEvent) {
		saveDocumentRef.tell(parserEvent, ActorRef.noSender());
	}

	private HtmPageContent downloadPage(final RequestUrl requestUrl, final ActorSystem system,
			final JobInformation jobInfo) {
		final ActorRef downloaderRef = system.actorOf(Props.create(DownloaderActor.class), DOWNLOADER_ACTOR);
		final HtmPageContent downloadEvent = (HtmPageContent) sendEvent(downloaderRef, requestUrl);
		downloadEvent.setFilterSet(filterSet);
		downloadEvent.setJobDetails(jobInfo);
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
	private void indexDocument(ActorRef documentIndexRef) {
		final Configuration conf = Configuration.getInstance();
		documentIndexRef.tell(new Tuple2<String, String>(conf.getDocumentStorageFolder(), conf.getIndexDirectory()),
				ActorRef.noSender());
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
	private Object sendEvent(final ActorRef actorRef, final Object passObjectRef) {
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

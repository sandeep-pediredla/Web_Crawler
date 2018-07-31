package com.wipro.digital.assignment.web.crawler;

import static com.wipro.digital.assignment.web.crawler.common.Constants.CONFIGURATION_FILE;
import static com.wipro.digital.assignment.web.crawler.common.Constants.CONFIGURATION_FILE2;
import static com.wipro.digital.assignment.web.crawler.common.Constants.CONFIGURATION_FILE_ALIAS;
import static com.wipro.digital.assignment.web.crawler.common.Constants.DOMAIN_LIST;
import static com.wipro.digital.assignment.web.crawler.common.Constants.DOMAIN_LIST_ALIAS;
import static com.wipro.digital.assignment.web.crawler.common.Constants.FILTER_LIST;
import static com.wipro.digital.assignment.web.crawler.common.Constants.FILTER_LIST_ALIAS;
import static com.wipro.digital.assignment.web.crawler.common.Constants.JOB_NAME;
import static com.wipro.digital.assignment.web.crawler.common.Constants.JOB_NAME2;
import static com.wipro.digital.assignment.web.crawler.common.Constants.JOB_NAME_ALIAS;
import static com.wipro.digital.assignment.web.crawler.common.Constants.LIST_OF_DOMAIN_URLS;
import static com.wipro.digital.assignment.web.crawler.common.Constants.LIST_OF_FILTER_URLS;
import static com.wipro.digital.assignment.web.crawler.common.Constants.USER;

import java.io.File;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wipro.digital.assignment.web.crawler.bean.JobInformation;
import com.wipro.digital.assignment.web.crawler.conf.Configuration;
import com.wipro.digital.assignment.web.crawler.jdbc.JobStore;
import com.wipro.digital.assignment.web.crawler.utils.PropertiesUtils;

import akka.util.Timeout;

public class CrawlManager {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(CrawlManager.class);

	/** The options. */
	private static Options options = new Options();
	static {
		options.addOption(DOMAIN_LIST_ALIAS, DOMAIN_LIST, true, LIST_OF_DOMAIN_URLS)
				.addOption(FILTER_LIST_ALIAS, FILTER_LIST, true, LIST_OF_FILTER_URLS)
				.addOption(CONFIGURATION_FILE_ALIAS, CONFIGURATION_FILE, true, CONFIGURATION_FILE2)
				.addOption(JOB_NAME_ALIAS, JOB_NAME, true, JOB_NAME2);
	}

	public static void main(final String[] args) {
		final JobParameterParser jobParameterParser = new JobParameterParser(options);
		final CommandLine cmd = jobParameterParser.parse(args);

		logger.debug("Passed parameters are ", cmd.getArgList());

		final Set<String> domainSet = JobParameterParser.getFileContent(cmd.getOptionValue(DOMAIN_LIST));
		final Set<String> filterSet = JobParameterParser.getFileContent(cmd.getOptionValue(FILTER_LIST));
		final String propertiesPath = cmd.getOptionValue(CONFIGURATION_FILE);
		final String jobName = cmd.getOptionValue(JOB_NAME);
		final Properties properties = PropertiesUtils.loadPropertiesFile(propertiesPath);
		final Configuration conf = Configuration.getInstance(properties);
		startCrawJob(domainSet, filterSet, jobName, properties, conf);
	}

	private static void startCrawJob(final Set<String> domainSet, final Set<String> filterSet, final String jobName,
			final Properties properties, final Configuration conf) {

		logger.debug("Starting crawl job {} & user {}", jobName, properties.getProperty(USER));
		final JobInformation jobDetails = JobStore.createOrRestartJob(jobName, properties.getProperty(USER));
		createORDeleteJobDirs(jobName, conf);
		crawlData(domainSet, filterSet, jobDetails, conf);
	}

	private static void crawlData(final Set<String> domainSet, final Set<String> filterSet,
			final JobInformation jobInfo, final Configuration conf) {

		final Timeout timeout = new Timeout(conf.getSocketTimeout(), TimeUnit.SECONDS);
		final JobManager jobManager = new JobManager(filterSet, timeout);
		jobManager.crawlPages(domainSet, jobInfo);
	}

	private static void createORDeleteJobDirs(final String jobName, final Configuration conf) {
		final String filePath = conf.getDocumentStorageFolder() + File.separator + jobName;
		if (!new File(filePath).exists()) {
			new File(filePath).mkdirs();
		}
		conf.setDocumentStorageFolder(filePath);
	}
}

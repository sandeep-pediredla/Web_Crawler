package com.wipro.digital.assignment.web.crawler.conf;

import java.lang.reflect.Field;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Configuration.
 */
public class Configuration {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

	/** The configuration. */
	private volatile static Configuration configuration;

	/** The db location. */
	private String dbLocation = System.getProperty("java.io.tmpdir") + "LocalMockDatabase";;

	/**
	 * The folder which will be used by crawler for storing the intermediate
	 * crawl data. The content of this folder should not be modified manually.
	 */
	private String tmpStorageFolder;

	/**
	 * If this feature is enabled, you would be able to resume a previously
	 * stopped/crashed crawl. However, it makes crawling slightly slower
	 */
	private boolean resumableCrawling = false;

	/** The lock timeout for the underlying sleepycat DB, in milliseconds. */
	private long dbLockTimeout = 500;

	/**
	 * Maximum depth of crawling For unlimited depth this parameter should be
	 * set to -1.
	 */
	private int maxDepthOfCrawling = -1;

	/**
	 * Maximum number of pages to fetch For unlimited number of pages, this
	 * parameter should be set to -1.
	 */
	private int maxPagesToFetch = -1;

	/**
	 * user-agent string that is used for representing your crawler to web
	 * servers.
	 */
	private String userAgentString = "sandeep-crawler";

	/**
	 * request delay in milliseconds (delay between sending two requests to the
	 * same host).
	 */
	private int requestDelay = 200;

	/** Should we also crawl https pages?. */
	private boolean includeHttpsPages = true;

	/**
	 * Should we fetch binary content such as images, audio, ...?
	 */
	private boolean includeBinaryContentInCrawling = false;

	/**
	 * Should we process binary content such as image, audio, ... using TIKA?
	 */
	private boolean processBinaryContentInCrawling = false;

	/** Maximum Connections per host. */
	private int maxConnectionsPerHost = 100;

	/** Maximum total connections. */
	private int maxTotalConnections = 100;

	/** Socket timeout in milliseconds. */
	private int socketTimeout = 20000;

	/** Connection timeout in milliseconds. */
	private int connectionTimeout = 30000;

	/** Max number of outgoing links which are processed from a page. */
	private int maxOutgoingLinksToFollow = 5000;

	/**
	 * Max allowed size of a page. Pages larger than this size will not be
	 * fetched.
	 */
	private int maxDownloadSize = 1048576;

	/** Should we follow redirects?. */
	private boolean followRedirects = true;

	/** Should the crawler stop running when the queue is empty?. */
	private boolean shutdownOnEmptyQueue = true;

	/**
	 * Wait this long before checking the status of the worker threads.
	 */
	private int threadMonitoringDelaySeconds = 10;

	/**
	 * Wait this long to verify the craweler threads are finished working.
	 */
	private int threadShutdownDelaySeconds = 10;

	/**
	 * Wait this long in seconds before launching cleanup.
	 */
	private int cleanupDelaySeconds = 10;

	/** The method. */
	private String method = "http";

	/** The document storage folder. */
	private String documentStorageFolder;

	/** The user. */
	private String user;

	/**
	 * Instantiates a new configuration.
	 */
	// preventing Singleton object instantiation from outside
	private Configuration() {
	}

	/**
	 * Gets the instance ref.
	 *
	 * @return the instance ref
	 */
	public static Configuration getInstanceRef() {
		return configuration;
	}

	/**
	 * Gets the single instance of Configuration.
	 *
	 * @param props
	 *            the props
	 * @return single instance of Configuration
	 */
	public static Configuration getInstance(final Properties props) {
		if (configuration == null) {
			synchronized (Configuration.class) {
				if (configuration == null) {
					configuration = new Configuration();

					try {
						setConfigurationValues(props);
					} catch (final Exception e) {
						logger.error("exception occurred while parsing field, exception is ", e);
					}
				}
			}
		}
		return configuration;
	}

	/**
	 * Sets the configuration values.
	 *
	 * @param props
	 *            the new configuration values
	 * @throws NoSuchFieldException
	 *             the no such field exception
	 * @throws SecurityException
	 *             the security exception
	 * @throws NumberFormatException
	 *             the number format exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	private static void setConfigurationValues(final Properties props) throws NoSuchFieldException, SecurityException,
			NumberFormatException, IllegalArgumentException, IllegalAccessException {
		final Class<Configuration> clazz = Configuration.class;
		final Set<String> keys = props.stringPropertyNames();

		for (final String key : keys) {

			final Field fieldID = clazz.getDeclaredField(key);

			if (fieldID.getType() == Integer.class) {
				fieldID.setInt(configuration, Integer.parseInt(props.getProperty(key)));
			} else if (fieldID.getType() == Float.class) {
				fieldID.setFloat(configuration, Float.parseFloat(props.getProperty(key)));
			} else if (fieldID.getType() == Long.class) {
				fieldID.setLong(configuration, Long.parseLong(props.getProperty(key)));
			} else if (fieldID.getType() == Boolean.class) {
				fieldID.setBoolean(configuration, Boolean.parseBoolean(props.getProperty(key)));
			} else if (fieldID.getType() == String.class) {
				fieldID.set(configuration, props.getProperty(key));
			}
		}
	}

	/**
	 * Gets the single instance of Configuration.
	 *
	 * @return single instance of Configuration
	 */
	public static Configuration getInstance() {
		if (configuration == null) {
			synchronized (Configuration.class) {
				if (configuration == null) {
					configuration = new Configuration();
				}
			}
		}
		return configuration;
	}

	/**
	 * Validates the configs specified by this instance.
	 *
	 * @throws Exception
	 *             on Validation fail
	 */
	public void validate() throws Exception {
		if (tmpStorageFolder == null) {
			throw new Exception("Crawl storage folder is not set in the CrawlConfig.");
		}
		if (requestDelay < 0) {
			throw new Exception("Invalid value for request delay: " + requestDelay);
		}
		if (maxDepthOfCrawling < -1) {
			throw new Exception(
					"Maximum crawl depth should be either a positive number or -1 for unlimited depth" + ".");
		}
		if (maxDepthOfCrawling > Short.MAX_VALUE) {
			throw new Exception("Maximum value for crawl depth is " + Short.MAX_VALUE);
		}
	}

	/**
	 * Gets the crawl storage folder.
	 *
	 * @return the crawl storage folder
	 */
	public String getCrawlStorageFolder() {
		return tmpStorageFolder;
	}

	/**
	 * The folder which will be used by crawler for storing the intermediate
	 * crawl data. The content of this folder should not be modified manually.
	 *
	 * @param crawlStorageFolder
	 *            The folder for the crawler's storage
	 */
	public void setCrawlStorageFolder(final String crawlStorageFolder) {
		this.tmpStorageFolder = crawlStorageFolder;
	}

	/**
	 * Checks if is resumable crawling.
	 *
	 * @return true, if is resumable crawling
	 */
	public boolean isResumableCrawling() {
		return resumableCrawling;
	}

	/**
	 * If this feature is enabled, you would be able to resume a previously
	 * stopped/crashed crawl. However, it makes crawling slightly slower
	 *
	 * @param resumableCrawling
	 *            Should crawling be resumable between runs ?
	 */
	public void setResumableCrawling(final boolean resumableCrawling) {
		this.resumableCrawling = resumableCrawling;
	}

	/**
	 * Set the lock timeout for the underlying sleepycat DB, in milliseconds.
	 * Default is 500.
	 *
	 * @param dbLockTimeout
	 *            the new db lock timeout
	 * @see com.sleepycat.je.EnvironmentConfig#setLockTimeout(long,
	 *      java.util.concurrent.TimeUnit)
	 */
	public void setDbLockTimeout(final long dbLockTimeout) {
		this.dbLockTimeout = dbLockTimeout;
	}

	/**
	 * Gets the db lock timeout.
	 *
	 * @return the db lock timeout
	 */
	public long getDbLockTimeout() {
		return this.dbLockTimeout;
	}

	/**
	 * Gets the max depth of crawling.
	 *
	 * @return the max depth of crawling
	 */
	public int getMaxDepthOfCrawling() {
		return maxDepthOfCrawling;
	}

	/**
	 * Maximum depth of crawling For unlimited depth this parameter should be
	 * set to -1.
	 *
	 * @param maxDepthOfCrawling
	 *            Depth of crawling (all links on current page = depth of 1)
	 */
	public void setMaxDepthOfCrawling(final int maxDepthOfCrawling) {
		this.maxDepthOfCrawling = maxDepthOfCrawling;
	}

	/**
	 * Gets the max pages to fetch.
	 *
	 * @return the max pages to fetch
	 */
	public int getMaxPagesToFetch() {
		return maxPagesToFetch;
	}

	/**
	 * Maximum number of pages to fetch For unlimited number of pages, this
	 * parameter should be set to -1.
	 *
	 * @param maxPagesToFetch
	 *            How many pages to fetch from all threads together ?
	 */
	public void setMaxPagesToFetch(final int maxPagesToFetch) {
		this.maxPagesToFetch = maxPagesToFetch;
	}

	/**
	 * Gets the user agent string.
	 *
	 * @return userAgentString
	 */
	public String getUserAgentString() {
		return userAgentString;
	}

	/**
	 * user-agent string that is used for representing your crawler to web
	 * servers.
	 *
	 * @param userAgentString
	 *            Custom userAgent string to use as your crawler's identifier
	 */
	public void setUserAgentString(final String userAgentString) {
		this.userAgentString = userAgentString;
	}

	/**
	 * Gets the request delay.
	 *
	 * @return the request delay
	 */
	public int getrequestDelay() {
		return requestDelay;
	}

	/**
	 * request delay in milliseconds (delay between sending two requests to the
	 * same host).
	 *
	 * @param requestDelay
	 *            the delay in milliseconds.
	 */
	public void setrequestDelay(final int requestDelay) {
		this.requestDelay = requestDelay;
	}

	/**
	 * Checks if is include https pages.
	 *
	 * @return true, if is include https pages
	 */
	public boolean isIncludeHttpsPages() {
		return includeHttpsPages;
	}

	/**
	 * Sets the include https pages.
	 *
	 * @param includeHttpsPages
	 *            Should we crawl https pages?
	 */
	public void setIncludeHttpsPages(final boolean includeHttpsPages) {
		this.includeHttpsPages = includeHttpsPages;
	}

	/**
	 * Checks if is include binary content in crawling.
	 *
	 * @return true, if is include binary content in crawling
	 */
	public boolean isIncludeBinaryContentInCrawling() {
		return includeBinaryContentInCrawling;
	}

	/**
	 * Sets the include binary content in crawling.
	 *
	 * @param includeBinaryContentInCrawling
	 *            Should we fetch binary content such as images, audio, ...?
	 */
	public void setIncludeBinaryContentInCrawling(final boolean includeBinaryContentInCrawling) {
		this.includeBinaryContentInCrawling = includeBinaryContentInCrawling;
	}

	/**
	 * Checks if is process binary content in crawling.
	 *
	 * @return true, if is process binary content in crawling
	 */
	public boolean isProcessBinaryContentInCrawling() {
		return processBinaryContentInCrawling;
	}

	/**
	 * Should we process binary content such as images, audio, ... using TIKA?
	 *
	 * @param processBinaryContentInCrawling
	 *            the new process binary content in crawling
	 */
	public void setProcessBinaryContentInCrawling(final boolean processBinaryContentInCrawling) {
		this.processBinaryContentInCrawling = processBinaryContentInCrawling;
	}

	/**
	 * Gets the max connections per host.
	 *
	 * @return the max connections per host
	 */
	public int getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}

	/**
	 * Sets the max connections per host.
	 *
	 * @param maxConnectionsPerHost
	 *            Maximum Connections per host
	 */
	public void setMaxConnectionsPerHost(final int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	/**
	 * Gets the max total connections.
	 *
	 * @return the max total connections
	 */
	public int getMaxTotalConnections() {
		return maxTotalConnections;
	}

	/**
	 * Sets the max total connections.
	 *
	 * @param maxTotalConnections
	 *            Maximum total connections
	 */
	public void setMaxTotalConnections(final int maxTotalConnections) {
		this.maxTotalConnections = maxTotalConnections;
	}

	/**
	 * Gets the socket timeout.
	 *
	 * @return the socket timeout
	 */
	public int getSocketTimeout() {
		return socketTimeout;
	}

	/**
	 * Sets the socket timeout.
	 *
	 * @param socketTimeout
	 *            Socket timeout in milliseconds
	 */
	public void setSocketTimeout(final int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	/**
	 * Gets the connection timeout.
	 *
	 * @return the connection timeout
	 */
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * Sets the connection timeout.
	 *
	 * @param connectionTimeout
	 *            Connection timeout in milliseconds
	 */
	public void setConnectionTimeout(final int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * Gets the max outgoing links to follow.
	 *
	 * @return the max outgoing links to follow
	 */
	public int getMaxOutgoingLinksToFollow() {
		return maxOutgoingLinksToFollow;
	}

	/**
	 * Sets the max outgoing links to follow.
	 *
	 * @param maxOutgoingLinksToFollow
	 *            Max number of outgoing links which are processed from a page
	 */
	public void setMaxOutgoingLinksToFollow(final int maxOutgoingLinksToFollow) {
		this.maxOutgoingLinksToFollow = maxOutgoingLinksToFollow;
	}

	/**
	 * Gets the max download size.
	 *
	 * @return the max download size
	 */
	public int getMaxDownloadSize() {
		return maxDownloadSize;
	}

	/**
	 * Sets the max download size.
	 *
	 * @param maxDownloadSize
	 *            Max allowed size of a page. Pages larger than this size will
	 *            not be fetched.
	 */
	public void setMaxDownloadSize(final int maxDownloadSize) {
		this.maxDownloadSize = maxDownloadSize;
	}

	/**
	 * Checks if is follow redirects.
	 *
	 * @return true, if is follow redirects
	 */
	public boolean isFollowRedirects() {
		return followRedirects;
	}

	/**
	 * Sets the follow redirects.
	 *
	 * @param followRedirects
	 *            Should we follow redirects?
	 */
	public void setFollowRedirects(final boolean followRedirects) {
		this.followRedirects = followRedirects;
	}

	/**
	 * Checks if is shutdown on empty queue.
	 *
	 * @return true, if is shutdown on empty queue
	 */
	public boolean isShutdownOnEmptyQueue() {
		return shutdownOnEmptyQueue;
	}

	/**
	 * Should the crawler stop running when the queue is empty?.
	 *
	 * @param shutdown
	 *            the new shutdown on empty queue
	 */
	public void setShutdownOnEmptyQueue(final boolean shutdown) {
		shutdownOnEmptyQueue = shutdown;
	}

	/**
	 * Gets the thread monitoring delay seconds.
	 *
	 * @return the thread monitoring delay seconds
	 */
	public int getThreadMonitoringDelaySeconds() {
		return threadMonitoringDelaySeconds;
	}

	/**
	 * Sets the thread monitoring delay seconds.
	 *
	 * @param delay
	 *            the new thread monitoring delay seconds
	 */
	public void setThreadMonitoringDelaySeconds(final int delay) {
		this.threadMonitoringDelaySeconds = delay;
	}

	/**
	 * Gets the thread shutdown delay seconds.
	 *
	 * @return the thread shutdown delay seconds
	 */
	public int getThreadShutdownDelaySeconds() {
		return threadShutdownDelaySeconds;
	}

	/**
	 * Sets the thread shutdown delay seconds.
	 *
	 * @param delay
	 *            the new thread shutdown delay seconds
	 */
	public void setThreadShutdownDelaySeconds(final int delay) {
		this.threadShutdownDelaySeconds = delay;
	}

	/**
	 * Gets the cleanup delay seconds.
	 *
	 * @return the cleanup delay seconds
	 */
	public int getCleanupDelaySeconds() {
		return cleanupDelaySeconds;
	}

	/**
	 * Sets the cleanup delay seconds.
	 *
	 * @param delay
	 *            the new cleanup delay seconds
	 */
	public void setCleanupDelaySeconds(final int delay) {
		this.cleanupDelaySeconds = delay;
	}

	/**
	 * Gets the db location.
	 *
	 * @return the dbLocation
	 */
	public String getDbLocation() {
		return dbLocation;
	}

	/**
	 * Sets the db location.
	 *
	 * @param dbLocation
	 *            the dbLocation to set
	 */
	public void setDbLocation(final String dbLocation) {
		this.dbLocation = dbLocation;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Crawl storage folder: " + getCrawlStorageFolder() + "\n");
		sb.append("Resumable crawling: " + isResumableCrawling() + "\n");
		sb.append("Max depth of crawl: " + getMaxDepthOfCrawling() + "\n");
		sb.append("Max pages to fetch: " + getMaxPagesToFetch() + "\n");
		sb.append("User agent string: " + getUserAgentString() + "\n");
		sb.append("Include https pages: " + isIncludeHttpsPages() + "\n");
		sb.append("Include binary content: " + isIncludeBinaryContentInCrawling() + "\n");
		sb.append("Max connections per host: " + getMaxConnectionsPerHost() + "\n");
		sb.append("Max total connections: " + getMaxTotalConnections() + "\n");
		sb.append("Socket timeout: " + getSocketTimeout() + "\n");
		sb.append("Max total connections: " + getMaxTotalConnections() + "\n");
		sb.append("Max outgoing links to follow: " + getMaxOutgoingLinksToFollow() + "\n");
		sb.append("Max download size: " + getMaxDownloadSize() + "\n");
		sb.append("Should follow redirects?: " + isFollowRedirects() + "\n");
		sb.append("Thread monitoring delay: " + getThreadMonitoringDelaySeconds() + "\n");
		sb.append("Thread shutdown delay: " + getThreadShutdownDelaySeconds() + "\n");
		sb.append("Cleanup delay: " + getCleanupDelaySeconds() + "\n");
		return sb.toString();
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Sets the method.
	 *
	 * @param method
	 *            the method to set
	 */
	public void setMethod(final String method) {
		this.method = method;
	}

	/**
	 * Gets the document storage folder.
	 *
	 * @return the documentStorageFolder
	 */
	public String getDocumentStorageFolder() {
		return documentStorageFolder;
	}

	/**
	 * Sets the document storage folder.
	 *
	 * @param documentStorageFolder
	 *            the documentStorageFolder to set
	 */
	public void setDocumentStorageFolder(final String documentStorageFolder) {
		this.documentStorageFolder = documentStorageFolder;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the user to set
	 */
	public void setUser(final String user) {
		this.user = user;
	}
}
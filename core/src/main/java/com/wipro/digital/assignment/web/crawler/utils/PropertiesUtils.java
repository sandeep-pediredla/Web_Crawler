
package com.wipro.digital.assignment.web.crawler.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wipro.digital.assignment.web.crawler.exception.ConfigurationException;

/**
 * The Class PropertiesUtils.
 */
public class PropertiesUtils {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

	/**
	 * Load properties file.
	 *
	 * @param propertiesPath
	 *            the properties path
	 * @return the properties
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static Properties loadPropertiesFile(final String propertiesPath) {
		final Properties prop = new Properties();

		try (InputStream input = new FileInputStream(new File(propertiesPath))) {
			prop.load(input);
		} catch (IOException e) {
			logger.error("unable to read properties file & exception is ", e);
			throw new ConfigurationException(e);
		}
		return prop;
	}
}

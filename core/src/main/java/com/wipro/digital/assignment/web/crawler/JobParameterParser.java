
package com.wipro.digital.assignment.web.crawler;

import static com.wipro.digital.assignment.web.crawler.common.Constants.CRAWLERS_MANAGER;
import static com.wipro.digital.assignment.web.crawler.common.Constants.DOMAIN_LIST;
import static com.wipro.digital.assignment.web.crawler.common.Constants.DOMAIN_STR;
import static com.wipro.digital.assignment.web.crawler.common.Constants.FILTER_LIST;
import static com.wipro.digital.assignment.web.crawler.common.Constants.FILTER_STR;
import static com.wipro.digital.assignment.web.crawler.common.Constants.HELP;
import static com.wipro.digital.assignment.web.crawler.common.Constants.JOB_NAME;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.cxf.helpers.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class JobParameterParser.
 */
public class JobParameterParser {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(JobParameterParser.class);

	/** The options. */
	private Options options;

	/**
	 * Instantiates a new job parameter parser.
	 *
	 * @param options
	 *            the options
	 */
	public JobParameterParser(final Options options) {
		this.options = options;
	}

	/**
	 * Parses the.
	 *
	 * @param args
	 *            the args
	 * @return the command line
	 */
	public CommandLine parse(final String[] args) {

		final CommandLineParser parser = new DefaultParser();

		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);

			if (cmd.hasOption(HELP)) {
				help();
			}

			if (!cmd.hasOption(DOMAIN_LIST)) {
				logger.error("Missing domain file path option");
				help();
			}

			if (!cmd.hasOption(FILTER_LIST)) {
				logger.error("Missing filter file path option");
				help();
			}

			if (!cmd.hasOption(JOB_NAME)) {
				logger.error("Missing Job name");
				help();
			}

			final String domainFilePath = cmd.getOptionValue(DOMAIN_LIST);
			validateFilePath(domainFilePath, DOMAIN_STR, options);
			validateForEmptyFile(domainFilePath, DOMAIN_STR, options);
			validateFilePath(cmd.getOptionValue(FILTER_LIST), FILTER_STR, options);

		} catch (final ParseException e) {
			logger.error("Failed to parse comand line properties", e);
			help();
		}
		return cmd;
	}

	/**
	 * Validate file path.
	 *
	 * @param filePath
	 *            the file path
	 * @param fileName
	 *            the file name
	 * @param options
	 *            the options
	 */
	private void validateFilePath(final String filePath, final String fileName, final Options options) {

		if (!new File(filePath).exists()) {
			logger.error(fileName + " file was missing");
			help();
		}
	}

	/**
	 * Validate for empty file.
	 *
	 * @param filePath
	 *            the file path
	 * @param fileName
	 *            the file name
	 * @param options
	 *            the options
	 */
	private void validateForEmptyFile(final String filePath, final String fileName, final Options options) {

		if (getFileContent(filePath).isEmpty()) {
			logger.error(fileName + " file was missing");
			help();
		}
	}

	/**
	 * Gets the file content.
	 *
	 * @param filePath
	 *            the file path
	 * @return the file content
	 */
	public static Set<String> getFileContent(final String filePath) {
		List<String> readLines = null;
		try {
			readLines = FileUtils.readLines(new File(filePath));
		} catch (final Exception e) {
			logger.error("Exception occurred while trying to process {} and exception is {}", filePath, e);
			e.printStackTrace();
		}
		return new HashSet<>(readLines);
	}

	/**
	 * Help.
	 */
	private void help() {
		final HelpFormatter formater = new HelpFormatter();
		formater.printHelp(CRAWLERS_MANAGER, options);
		System.exit(1);
	}
}

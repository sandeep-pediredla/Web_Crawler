
package com.wipro.digital.assignment.web.crawler.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wipro.digital.assignment.web.crawler.bean.JobInformation;
import com.wipro.digital.assignment.web.crawler.common.CrawlerStatus;
import com.wipro.digital.assignment.web.crawler.conf.Configuration;
import com.wipro.digital.assignment.web.crawler.exception.ConfigurationException;
import com.wipro.digital.assignment.web.crawler.exception.CrawlerException;

public class JobStore {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(JobStore.class);

	/** The Constant selectTableSQL. */
	private static final String JOB_DETAILS_QUERY = "SELECT * from Job_Details where JOB_NAME = '";

	/** The insert table SQL. */
	static String insertTableSQL = "INSERT INTO Job_Details" + "(CREATED_BY, CREATED_DATE, JOB_NAME, JOB_STATUS) VALUES"
			+ "(?,?,?,?)";

	/** The connection. */
	private static volatile Connection connection;

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	private static Connection getConnection() {

		if (connection == null) {

			final String jdbcURl = Configuration.getInstance().getJdbcUrl();
			final String driver = Configuration.getInstance().getJdbcDriver();
			final String user = Configuration.getInstance().getJdbcUser();
			final String password = Configuration.getInstance().getJdbcPassword();

			try {
				Class.forName(driver);
				connection = DriverManager.getConnection(jdbcURl, user, password);
				logger.debug("Connection created succesfully");
			} catch (final Exception e) {
				logger.error("exception occured while connecting to database", e);
				throw new ConfigurationException(e);
			}
		}
		return connection;
	}

	public static void updateJobInfo(JobInformation jobInformation) {

	}

	/**
	 * Creates the or restart job.
	 *
	 * @param jobName
	 *            the job name
	 * @param userName
	 *            the user name
	 * @return the job details
	 */
	public static JobInformation createOrRestartJob(final String jobName, final String userName) {

		JobInformation jobDetails = null;
		try {
			jobDetails = retrieveRecentJobDetails(jobName);
		} catch (final SQLException e) {
			logger.info("Could not able to find a job {} exists in system", jobName);
		}

		if (jobDetails == null) {
			jobDetails = createNewJobInSystem(jobName, userName, jobDetails);
		} else {
			// todo
		}

		return jobDetails;
	}

	private static JobInformation createNewJobInSystem(final String jobName, final String userName,
			JobInformation jobDetails) {
		try {
			insertJobIntoDb(jobName, userName);
			jobDetails = retrieveRecentJobDetails(jobName);
		} catch (final SQLException e) {
			logger.error("Unable create a job in the system", e);
			throw new CrawlerException(e);
		}
		return jobDetails;
	}

	/**
	 * Insert job into db.
	 *
	 * @param jobName
	 *            the job name
	 * @param userName
	 *            the user name
	 * @throws SQLException
	 *             the SQL exception
	 */
	private static void insertJobIntoDb(final String jobName, final String userName) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		try {
			dbConnection = getConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, getCurrentTimeStamp().toString());
			preparedStatement.setString(3, jobName);
			preparedStatement.setString(4, CrawlerStatus.CREATED.name());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();
			logger.debug("Record is inserted into system table!");
		} catch (final SQLException e) {
			logger.error("exception occurred while doing insert command", e);
			throw new CrawlerException(e);
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}

	/**
	 * Retrieve recent job details.
	 *
	 * @param jobName
	 *            the job name
	 * @return the job details
	 * @throws SQLException
	 *             the SQL exception
	 */
	private static JobInformation retrieveRecentJobDetails(final String jobName) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		JobInformation jobDetail = null;
		try {
			dbConnection = getConnection();
			statement = dbConnection.createStatement();

			System.out.println(JOB_DETAILS_QUERY + jobName + "' order by ID desc LIMIT 1");

			// execute select SQL stetement
			final ResultSet rs = statement.executeQuery(JOB_DETAILS_QUERY + jobName + "' order by ID desc LIMIT 1");

			while (rs.next()) {
				jobDetail = new JobInformation();
				jobDetail.setCreatedBy(rs.getString("CREATED_BY"));
				jobDetail.setCreatedOn(rs.getString("CREATED_DATE"));
				jobDetail.setJobId(rs.getInt("ID"));
				jobDetail.setJobName(rs.getString("JOB_NAME"));
				jobDetail.setJobStatus(rs.getString("JOB_STATUS"));
			}
		} catch (final SQLException e) {
			logger.error("exception occurred while doing retrieve job details", e);
			throw new CrawlerException(e);
		} finally {

			if (statement != null) {
				statement.close();
			}

		}
		return jobDetail;
	}

	/**
	 * Gets the current time stamp.
	 *
	 * @return the current time stamp
	 */
	private static java.sql.Timestamp getCurrentTimeStamp() {
		final java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
}

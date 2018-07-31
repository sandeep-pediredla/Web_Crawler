
package com.wipro.digital.assignment.web.crawler.bean;

public class JobInformation {

	/** The job id. */
	private int jobId;

	/** The job name. */
	private String jobName;

	/** The job status. */
	private String jobStatus;

	/** The created by. */
	private String createdBy;

	/** The created on. */
	private String createdOn;

	/** The url. */
	private String url;

	private int parentId;

	/**
	 * Gets the job id.
	 *
	 * @return the jobId
	 */
	public int getJobId() {
		return jobId;
	}

	/**
	 * Sets the job id.
	 *
	 * @param jobId
	 *            the jobId to set
	 */
	public void setJobId(final int jobId) {
		this.jobId = jobId;
	}

	/**
	 * Gets the job name.
	 *
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * Sets the job name.
	 *
	 * @param jobName
	 *            the jobName to set
	 */
	public void setJobName(final String jobName) {
		this.jobName = jobName;
	}

	/**
	 * Gets the job status.
	 *
	 * @return the jobStatus
	 */
	public String getJobStatus() {
		return jobStatus;
	}

	/**
	 * Sets the job status.
	 *
	 * @param jobStatus
	 *            the jobStatus to set
	 */
	public void setJobStatus(final String jobStatus) {
		this.jobStatus = jobStatus;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn
	 *            the createdOn to set
	 */
	public void setCreatedOn(final String createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the url to set
	 */
	public void setUrl(final String url) {
		this.url = url;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

}

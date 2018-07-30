package com.wipro.digital.assignment.web.crawler.html;

import java.util.Map;

/**
 * The Class PageInformation.
 */
public class PageInformation {

	/** The url. */
	private String url;

	/** The docid. */
	private int docid;

	/** The parent docid. */
	private int parentDocid;

	/** The parent url. */
	private String parentUrl;

	/** The depth. */
	private short depth;

	/** The domain. */
	private String domain;

	/** The sub domain. */
	private String subDomain;

	/** The path. */
	private String path;

	/** The anchor. */
	private String anchor;

	/** The priority. */
	private byte priority;

	/** The tag. */
	private String tag;

	/** The attributes. */
	private Map<String, String> attributes;

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
	 *            the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the docid.
	 *
	 * @return the docid
	 */
	public int getDocid() {
		return docid;
	}

	/**
	 * Sets the docid.
	 *
	 * @param docid
	 *            the new docid
	 */
	public void setDocid(int docid) {
		this.docid = docid;
	}

	/**
	 * Gets the parent docid.
	 *
	 * @return the parent docid
	 */
	public int getParentDocid() {
		return parentDocid;
	}

	/**
	 * Sets the parent docid.
	 *
	 * @param parentDocid
	 *            the new parent docid
	 */
	public void setParentDocid(int parentDocid) {
		this.parentDocid = parentDocid;
	}

	/**
	 * Gets the parent url.
	 *
	 * @return the parent url
	 */
	public String getParentUrl() {
		return parentUrl;
	}

	/**
	 * Sets the parent url.
	 *
	 * @param parentUrl
	 *            the new parent url
	 */
	public void setParentUrl(String parentUrl) {
		this.parentUrl = parentUrl;
	}

	/**
	 * Gets the depth.
	 *
	 * @return the depth
	 */
	public short getDepth() {
		return depth;
	}

	/**
	 * Sets the depth.
	 *
	 * @param depth
	 *            the new depth
	 */
	public void setDepth(short depth) {
		this.depth = depth;
	}

	/**
	 * Gets the domain.
	 *
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Sets the domain.
	 *
	 * @param domain
	 *            the new domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * Gets the sub domain.
	 *
	 * @return the sub domain
	 */
	public String getSubDomain() {
		return subDomain;
	}

	/**
	 * Sets the sub domain.
	 *
	 * @param subDomain
	 *            the new sub domain
	 */
	public void setSubDomain(String subDomain) {
		this.subDomain = subDomain;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path.
	 *
	 * @param path
	 *            the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Gets the anchor.
	 *
	 * @return the anchor
	 */
	public String getAnchor() {
		return anchor;
	}

	/**
	 * Sets the anchor.
	 *
	 * @param anchor
	 *            the new anchor
	 */
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public byte getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority
	 *            the new priority
	 */
	public void setPriority(byte priority) {
		this.priority = priority;
	}

	/**
	 * Gets the tag.
	 *
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Sets the tag.
	 *
	 * @param tag
	 *            the new tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * Gets the attributes.
	 *
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * Sets the attributes.
	 *
	 * @param attributes
	 *            the attributes
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}

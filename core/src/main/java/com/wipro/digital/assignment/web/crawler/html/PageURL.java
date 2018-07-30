package com.wipro.digital.assignment.web.crawler.html;

import java.util.Map;

/**
 * The Class PageURL.
 */
public class PageURL {

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getDocid() {
		return docid;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}

	public int getParentDocid() {
		return parentDocid;
	}

	public void setParentDocid(int parentDocid) {
		this.parentDocid = parentDocid;
	}

	public String getParentUrl() {
		return parentUrl;
	}

	public void setParentUrl(String parentUrl) {
		this.parentUrl = parentUrl;
	}

	public short getDepth() {
		return depth;
	}

	public void setDepth(short depth) {
		this.depth = depth;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSubDomain() {
		return subDomain;
	}

	public void setSubDomain(String subDomain) {
		this.subDomain = subDomain;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAnchor() {
		return anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public byte getPriority() {
		return priority;
	}

	public void setPriority(byte priority) {
		this.priority = priority;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}

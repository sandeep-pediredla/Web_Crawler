package com.wipro.digital.assignment.web.crawler.security;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.text.html.FormSubmitEvent.MethodType;

/**
 * Abstract class containing authentication information needed to login into a
 * user/password protected site<br>
 * This class should be extended by specific authentication types like form
 * authentication and basic authentication etc<br>
 * .
 */
public abstract class AuthInfo {

	/**
	 * The Enum AuthenticationType.
	 */
	public enum AuthenticationType {

		/** The basic authentication. */
		BASIC_AUTHENTICATION,
		/** The form authentication. */
		FORM_AUTHENTICATION,
		/** The nt authentication. */
		NT_AUTHENTICATION
	}

	/** The authentication type. */
	protected AuthenticationType authenticationType;

	/** The http method. */
	protected MethodType httpMethod;

	/** The protocol. */
	protected String protocol;

	/** The host. */
	protected String host;

	/** The login target. */
	protected String loginTarget;

	/** The port. */
	protected int port;

	/** The username. */
	protected String username;

	/** The password. */
	protected String password;

	/**
	 * This constructor should only be used by extending classes.
	 *
	 * @param authenticationType
	 *            Pick the one which matches your authentication
	 * @param httpMethod
	 *            Choose POST / GET
	 * @param loginUrl
	 *            Full URL of the login page
	 * @param username
	 *            Username for Authentication
	 * @param password
	 *            Password for Authentication
	 * @throws MalformedURLException
	 *             Make sure your URL is valid
	 */
	protected AuthInfo(AuthenticationType authenticationType, MethodType httpMethod, String loginUrl, String username,
			String password) throws MalformedURLException {
		this.authenticationType = authenticationType;
		this.httpMethod = httpMethod;
		URL url = new URL(loginUrl);
		this.protocol = url.getProtocol();
		this.host = url.getHost();
		this.port = url.getPort() == -1 ? url.getDefaultPort() : url.getPort();
		this.loginTarget = url.getFile();

		this.username = username;
		this.password = password;
	}

	/**
	 * Gets the authentication type.
	 *
	 * @return Authentication type (BASIC, FORM)
	 */
	public AuthenticationType getAuthenticationType() {
		return authenticationType;
	}

	/**
	 * Sets the authentication type.
	 *
	 * @param authenticationType
	 *            Should be set only by extending classes (BASICAuthInfo,
	 *            FORMAuthInfo)
	 */
	public void setAuthenticationType(AuthenticationType authenticationType) {
		this.authenticationType = authenticationType;
	}

	/**
	 * Gets the http method.
	 *
	 * @return httpMethod (POST, GET)
	 */
	public MethodType getHttpMethod() {
		return httpMethod;
	}

	/**
	 * Sets the http method.
	 *
	 * @param httpMethod
	 *            Should be set by extending classes (POST, GET)
	 */
	public void setHttpMethod(MethodType httpMethod) {
		this.httpMethod = httpMethod;
	}

	/**
	 * Gets the protocol.
	 *
	 * @return protocol type (http, https)
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * Sets the protocol.
	 *
	 * @param protocol
	 *            Don't set this one unless you know what you are doing
	 *            (protocol: http, https)
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * Gets the host.
	 *
	 * @return host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the host.
	 *
	 * @param host
	 *            Don't set this one unless you know what you are doing (sets
	 *            the domain name)
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the login target.
	 *
	 * @return file/path which is the rest of the url after the domain name (eg:
	 *         /login.php)
	 */
	public String getLoginTarget() {
		return loginTarget;
	}

	/**
	 * Sets the login target.
	 *
	 * @param loginTarget
	 *            Don't set this one unless you know what you are doing (eg:
	 *            /login.php)
	 */
	public void setLoginTarget(String loginTarget) {
		this.loginTarget = loginTarget;
	}

	/**
	 * Gets the port.
	 *
	 * @return port number (eg: 80, 443)
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port.
	 *
	 * @param port
	 *            Don't set this one unless you know what you are doing (eg: 80,
	 *            443)
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Gets the username.
	 *
	 * @return username used for Authentication
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username
	 *            username used for Authentication
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return password used for Authentication
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            password used for Authentication
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
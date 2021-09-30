package com.softicar.platform.ajax.testing.selenium.grid.configuration.hub;

import java.util.Objects;

/**
 * Provides hub configuration parameters for a Selenium grid.
 *
 * @author Alexander Schmidt
 */
public interface ISeleniumHubConfiguration {

	/**
	 * Returns the number of seconds until a browser timeout occurs on a
	 * Selenium node.
	 * <p>
	 * This value is propagated from the hub to the nodes (unless it gets
	 * overridden on node level).
	 * <p>
	 * The minimum value is <i>60</i> (as per
	 * "<code>java -jar selenium-server-standalone-3.141.59.jar -help</code>").
	 *
	 * @return the Selenium node browser timeout value, in seconds
	 */
	Integer getBrowserTimeout();

	/**
	 * Returns the number of seconds until a session timeout occurs on a
	 * Selenium node.
	 * <p>
	 * This value is propagated from the hub to the nodes (unless it gets
	 * overridden on node level).
	 * <p>
	 * Should be lower than {@link #getBrowserTimeout()}.
	 *
	 * @return the Selenium node session timeout value, in seconds
	 */
	Integer getSessionTimeout();

	/**
	 * Returns the maximum number of sessions which may exist on each node at a
	 * given point in time, regardless of the employed browser.
	 * <p>
	 * This value is propagated from the hub to the nodes (unless it gets
	 * overridden on node level).
	 *
	 * @return the maximum number of concurrent sessions, per node
	 */
	Integer getMaximumSessionCount();

	/**
	 * Returns the IP address of the hub.
	 *
	 * @return the IP address of the hub (never <i>null</i>)
	 */
	String getIp();

	/**
	 * Returns the port on which the hub listens to connections.
	 *
	 * @return the port on which the hub listens to connections
	 */
	Integer getPort();

	/**
	 * Ensures that this {@link ISeleniumHubConfiguration} is valid.
	 * <p>
	 * Throws an {@link Exception} if the validation fails.
	 *
	 * @return this {@link ISeleniumHubConfiguration}
	 */
	default ISeleniumHubConfiguration validate() {

		Objects.requireNonNull(getBrowserTimeout());
		Objects.requireNonNull(getSessionTimeout());
		Objects.requireNonNull(getMaximumSessionCount());
		Objects.requireNonNull(getIp());
		Objects.requireNonNull(getPort());
		return this;
	}
}

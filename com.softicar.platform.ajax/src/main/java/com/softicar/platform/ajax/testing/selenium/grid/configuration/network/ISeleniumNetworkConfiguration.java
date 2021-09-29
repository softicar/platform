package com.softicar.platform.ajax.testing.selenium.grid.configuration.network;

import java.util.Objects;

/**
 * Provides network configuration parameters for a Selenium grid.
 *
 * @author Alexander Schmidt
 */
public interface ISeleniumNetworkConfiguration {

	/**
	 * Returns the subnetwork in which the IP addresses of the nodes of the
	 * Selenium grid reside.
	 *
	 * @return the subnetwork of the Selenium grid (never <i>null</i>)
	 */
	String getSubnetwork();

	/**
	 * Ensures that this {@link ISeleniumNetworkConfiguration} is valid.
	 * <p>
	 * Throws an {@link Exception} if the validation fails.
	 *
	 * @return this {@link ISeleniumNetworkConfiguration}
	 */
	default ISeleniumNetworkConfiguration validate() {

		Objects.requireNonNull(getSubnetwork());
		return this;
	}
}

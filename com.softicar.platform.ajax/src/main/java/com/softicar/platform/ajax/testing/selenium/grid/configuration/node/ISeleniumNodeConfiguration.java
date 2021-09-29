package com.softicar.platform.ajax.testing.selenium.grid.configuration.node;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Provides node configuration parameters for a Selenium grid.
 *
 * @author Alexander Schmidt
 */
public interface ISeleniumNodeConfiguration {

	/**
	 * Returns the node name prefix for nodes which are created with this
	 * {@link ISeleniumNodeConfiguration}.
	 * <p>
	 * Recommended prefixes include "<tt>selenium-node-chrome</tt>" and
	 * "<tt>selenium-node-firefox</tt>".
	 *
	 * @return the name prefix for created nodes (never <i>null</i>)
	 */
	String getNamePrefix();

	/**
	 * Returns the name of the desired <a href=
	 * "https://github.com/SeleniumHQ/docker-selenium">docker-selenium</a> node
	 * container image for this {@link ISeleniumNodeConfiguration}.
	 * <p>
	 * Typical names include "<tt>node-chrome</tt>" and "<tt>node-firefox</tt>".
	 *
	 * @return the name of the node container image (never <i>null</i>)
	 */
	String getImageName();

	/**
	 * Returns the factor to determine the number of nodes to spawn with this
	 * {@link ISeleniumNodeConfiguration}.
	 *
	 * @return the factor to determine the number of nodes to spawn (never
	 *         <i>null</i>)
	 */
	BigDecimal getFactor();

	/**
	 * Ensures that this {@link ISeleniumNodeConfiguration} is valid.
	 * <p>
	 * Throws an {@link Exception} if the validation fails.
	 *
	 * @return this {@link ISeleniumNodeConfiguration}
	 */
	default ISeleniumNodeConfiguration validate() {

		Objects.requireNonNull(getNamePrefix());
		Objects.requireNonNull(getImageName());
		Objects.requireNonNull(getFactor());
		return this;
	}
}

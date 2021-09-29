package com.softicar.platform.ajax.testing.selenium.grid.configuration.grid;

import com.softicar.platform.ajax.testing.selenium.grid.configuration.hub.ISeleniumHubConfiguration;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.network.ISeleniumNetworkConfiguration;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.node.ISeleniumNodeConfiguration;
import java.util.Collection;
import java.util.Objects;

/**
 * Provides general configuration parameters for a Selenium grid.
 *
 * @author Alexander Schmidt
 */
public interface ISeleniumGridConfiguration {

	/**
	 * Returns the version number of the desired <a href=
	 * "https://github.com/SeleniumHQ/docker-selenium">docker-selenium</a>
	 * container release.
	 * <p>
	 * The returned version number is used for hub and nodes alike.
	 *
	 * @see <a href=
	 *      "https://github.com/SeleniumHQ/docker-selenium/releases">docker-selenium:
	 *      Releases (GitHub)</a>
	 * @return the desired <tt>docker-selenium</tt> container version number
	 *         (never <i>null</i>)
	 */
	String getContainerVersion();

	/**
	 * Returns an ID which uniquely identifies the Selenium grid.
	 *
	 * @return the unique ID of the Selenium grid (never <i>null</i>)
	 */
	String getUuid();

	/**
	 * Returns the number of worker threads which are used to execute Selenium
	 * based unit tests.
	 *
	 * @return the number of worker threads (never <i>null</i>)
	 */
	Integer getWorkerThreadCount();

	/**
	 * Returns the absolute path to the temporary directory in which lock files
	 * and other helper files to handle the disposable Selenium grid instance
	 * shall be stored.
	 * <p>
	 * The path should begin with the path of the of the system's main temporary
	 * directory.
	 *
	 * @return the absolute path to the temporary directory (never <i>null</i>)
	 */
	String getTemporaryDirectory();

	/**
	 * Determines whether the Selenium grid shall persist until after the
	 * shutdown of the {@link Runtime} which executes the current unit test run.
	 * That is, if the shutdown of the Selenium grid shall be "deferred".
	 * <p>
	 * "Deferred shutdown" <i>is</i> required when a Selenium unit test run is
	 * invoked as part of a Gradle build. In that case, several {@link Runtime}
	 * instances are employed as workers, in parallel.
	 * <p>
	 * "Deferred shutdown" is <i>not</i> required when a Selenium unit test run
	 * is invoked from the IDE. In that case, there is only a single
	 * {@link Runtime} instances which serves as a worker.
	 *
	 * @return <i>true</i> if the Selenium grid shall persist until after the
	 *         shutdown of the unit test {@link Runtime}; <i>false otherwise</i>
	 */
	Boolean isDeferredGridShutdown();

	/**
	 * Returns the {@link ISeleniumHubConfiguration} for the Selenium grid.
	 *
	 * @return the {@link ISeleniumHubConfiguration} (never <i>null</i>)
	 */
	ISeleniumHubConfiguration getHubConfiguration();

	/**
	 * Returns the {@link ISeleniumNodeConfiguration} instances for the Selenium
	 * grid.
	 *
	 * @return the {@link ISeleniumNodeConfiguration} instances (never
	 *         <i>null</i>)
	 */
	Collection<ISeleniumNodeConfiguration> getNodeConfigurations();

	/**
	 * Returns the {@link ISeleniumNetworkConfiguration} for the Selenium grid.
	 *
	 * @return the {@link ISeleniumNetworkConfiguration} (never <i>null</i>)
	 */
	ISeleniumNetworkConfiguration getNetworkConfiguration();

	/**
	 * Ensures that this {@link ISeleniumGridConfiguration} is valid.
	 * <p>
	 * Throws an {@link Exception} if the validation fails.
	 *
	 * @return this {@link ISeleniumGridConfiguration}
	 */
	default ISeleniumGridConfiguration validate() {

		Objects.requireNonNull(getContainerVersion());
		Objects.requireNonNull(getUuid());
		Objects.requireNonNull(getWorkerThreadCount());
		Objects.requireNonNull(getTemporaryDirectory());
		Objects.requireNonNull(isDeferredGridShutdown());
		Objects.requireNonNull(getHubConfiguration());
		Objects.requireNonNull(getNodeConfigurations());
		Objects.requireNonNull(getNetworkConfiguration());

		getHubConfiguration().validate();
		getNodeConfigurations().forEach(ISeleniumNodeConfiguration::validate);
		getNetworkConfiguration().validate();

		return this;
	}
}

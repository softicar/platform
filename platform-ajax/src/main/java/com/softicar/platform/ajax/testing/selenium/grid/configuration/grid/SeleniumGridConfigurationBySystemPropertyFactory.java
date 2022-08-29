package com.softicar.platform.ajax.testing.selenium.grid.configuration.grid;

import com.softicar.platform.ajax.testing.selenium.AjaxSeleniumTestProperties;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.hub.SeleniumHubConfiguration;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.network.SeleniumNetworkConfiguration;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.node.SeleniumNodeConfiguration;

/**
 * Creates {@link ISeleniumGridConfiguration} instances, based upon values from
 * system properties.
 *
 * @author Alexander Schmidt
 */
public class SeleniumGridConfigurationBySystemPropertyFactory {

	/**
	 * Creates an {@link ISeleniumGridConfiguration}, based upon values from
	 * system properties.
	 *
	 * @return the {@link ISeleniumGridConfiguration} (never <i>null</i>)
	 */
	public ISeleniumGridConfiguration create() {

		return createGridConfiguration()
			.setHubConfiguration(createHubConfiguration())
			.addNodeConfiguration(createChromeNodeConfiguration())
			.addNodeConfiguration(createFirefoxNodeConfiguration())
			.setNetworkConfiguration(createNetworkConfiguration())
			.validate();
	}

	private SeleniumGridConfiguration createGridConfiguration() {

		return new SeleniumGridConfiguration()//
			.setContainerVersion(AjaxSeleniumTestProperties.GRID_CONTAINER_VERSION)
			.setUuid(AjaxSeleniumTestProperties.GRID_UUID)
			.setWorkerThreadCount(AjaxSeleniumTestProperties.GRID_WORKER_THREAD_COUNT)
			.setTemporaryDirectory(AjaxSeleniumTestProperties.GRID_TEMPORARY_DIRECTORY)
			.setDeferredGridShutdown(AjaxSeleniumTestProperties.GRID_DEFERRED_SHUTDOWN);
	}

	private SeleniumHubConfiguration createHubConfiguration() {

		return new SeleniumHubConfiguration()//
			.setBrowserTimeout(AjaxSeleniumTestProperties.HUB_BROWSER_TIMEOUT)
			.setIp(AjaxSeleniumTestProperties.HUB_IP)
			.setMaximumSessionCount(AjaxSeleniumTestProperties.HUB_MAXIMUM_SESSION_COUNT)
			.setPortExternal(AjaxSeleniumTestProperties.HUB_PORT_EXTERNAL)
			.setPortEventBusPublish(AjaxSeleniumTestProperties.HUB_PORT_EVENT_BUS_PUBLISH)
			.setPortEventBusSubscribe(AjaxSeleniumTestProperties.HUB_PORT_EVENT_BUS_SUBSCRIBE)
			.setSessionTimeout(AjaxSeleniumTestProperties.HUB_SESSION_TIMEOUT);
	}

	private SeleniumNodeConfiguration createChromeNodeConfiguration() {

		return new SeleniumNodeConfiguration()//
			.setImageName(AjaxSeleniumTestProperties.NODE_CHROME_IMAGE_NAME)
			.setNamePrefix(AjaxSeleniumTestProperties.NODE_CHROME_NAME_PREFIX)
			.setFactor(AjaxSeleniumTestProperties.NODE_CHROME_FACTOR);
	}

	private SeleniumNodeConfiguration createFirefoxNodeConfiguration() {

		return new SeleniumNodeConfiguration()//
			.setImageName(AjaxSeleniumTestProperties.NODE_FIREFOX_IMAGE_NAME)
			.setNamePrefix(AjaxSeleniumTestProperties.NODE_FIREFOX_NAME_PREFIX)
			.setFactor(AjaxSeleniumTestProperties.NODE_FIREFOX_FACTOR);
	}

	private SeleniumNetworkConfiguration createNetworkConfiguration() {

		return new SeleniumNetworkConfiguration()//
			.setSubnetwork(AjaxSeleniumTestProperties.NETWORK_SUBNETWORK);
	}
}

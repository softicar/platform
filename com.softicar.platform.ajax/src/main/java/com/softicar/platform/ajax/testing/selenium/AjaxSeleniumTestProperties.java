package com.softicar.platform.ajax.testing.selenium;

import com.softicar.platform.common.core.properties.IProperty;
import com.softicar.platform.common.core.properties.SystemPropertyFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Defines system properties related to Selenium based unit test execution.
 *
 * @author Alexander Schmidt
 */
public class AjaxSeleniumTestProperties {

	private static final SystemPropertyFactory FACTORY = new SystemPropertyFactory("com.softicar.testing.selenium");
	private static final String DEFAULT_GRID_UUID = "default-" + UUID.randomUUID().toString();

	// Driver
	public static final IProperty<Integer> DRIVER_CREATION_RETRY_COUNT = FACTORY.createIntegerProperty("driver.creation.retry.count", 5);
	public static final IProperty<Integer> DRIVER_CREATION_RETRY_DELAY = FACTORY.createIntegerProperty("driver.creation.retry.delay", 1000);
	public static final IProperty<Boolean> DRIVER_REUSE = FACTORY.createBooleanProperty("driver.reuse", true);
	public static final IProperty<Integer> DRIVER_IMPLICIT_WAIT_TIMEOUT = FACTORY.createIntegerProperty("driver.implicit.wait.timeout", null);
	public static final IProperty<Integer> DRIVER_PAGE_LOAD_TIMEOUT = FACTORY.createIntegerProperty("driver.page.load.timeout", null);
	public static final IProperty<Integer> DRIVER_SCRIPT_TIMEOUT = FACTORY.createIntegerProperty("driver.script.timeout", null);

	// Execution
	public static final IProperty<Boolean> EXECUTION_SCREENSHOT_ON_FAILURE = FACTORY.createBooleanProperty("execution.screenshot.on.failure", false);
	public static final IProperty<Boolean> EXECUTION_SCREENSHOT_ON_FINISHED = FACTORY.createBooleanProperty("execution.screenshot.on.finished", false);

	// Selenium Grid
	public static final IProperty<String> GRID_CONTAINER_VERSION = FACTORY.createStringProperty("grid.container.version", "3.141.59-20210713");
	public static final IProperty<Boolean> GRID_DEFERRED_SHUTDOWN = FACTORY.createBooleanProperty("grid.deferred.shutdown", false);
	public static final IProperty<String> GRID_SHUTDOWN_SCRIPT = FACTORY.createStringProperty("grid.shutdown.script", "shutdown-grid.sh");
	public static final IProperty<String> GRID_TEMPORARY_DIRECTORY = FACTORY
		.createStringProperty(
			"grid.temporary.directory",
			new File(System.getProperty("java.io.tmpdir"), "selenium-grid-" + DEFAULT_GRID_UUID).getAbsolutePath());
	public static final IProperty<String> GRID_UUID = FACTORY.createStringProperty("grid.uuid", DEFAULT_GRID_UUID);
	public static final IProperty<Integer> GRID_WORKER_THREAD_COUNT = FACTORY.createIntegerProperty("grid.worker.thread.count", 1);

	// Selenium Hub
	public static final IProperty<Integer> HUB_BROWSER_TIMEOUT = FACTORY.createIntegerProperty("hub.browser.timeout", 60);
	public static final IProperty<String> HUB_IP = FACTORY.createStringProperty("hub.ip", "172.0.0.222");
	public static final IProperty<Integer> HUB_MAXIMUM_SESSION_COUNT = FACTORY.createIntegerProperty("hub.maximum.session.count", 1);
	public static final IProperty<Integer> HUB_PORT = FACTORY.createIntegerProperty("hub.port", 4444);
	public static final IProperty<Integer> HUB_SESSION_TIMEOUT = FACTORY.createIntegerProperty("hub.session.timeout", 20);

	// Selenium Network
	public static final IProperty<String> NETWORK_SUBNETWORK = FACTORY.createStringProperty("network.subnetwork", "172.0.0.0/24");

	// Selenium Node (Chrome)
	public static final IProperty<BigDecimal> NODE_CHROME_FACTOR = FACTORY.createBigDecimalProperty("node.chrome.factor", BigDecimal.ZERO);
	public static final IProperty<String> NODE_CHROME_IMAGE_NAME = FACTORY.createStringProperty("node.chrome.image.name", "node-chrome");
	public static final IProperty<String> NODE_CHROME_NAME_PREFIX = FACTORY.createStringProperty("node.chrome.name.prefix", "selenium-node-chrome");

	// Selenium Node (Firefox)
	public static final IProperty<BigDecimal> NODE_FIREFOX_FACTOR = FACTORY.createBigDecimalProperty("node.firefox.factor", new BigDecimal("1.5"));
	public static final IProperty<String> NODE_FIREFOX_IMAGE_NAME = FACTORY.createStringProperty("node.firefox.image.name", "node-firefox");
	public static final IProperty<String> NODE_FIREFOX_NAME_PREFIX = FACTORY.createStringProperty("node.firefox.name.prefix", "selenium-node-firefox");

	// Server
	public static final IProperty<String> SERVER_IP = FACTORY.createStringProperty("server.ip", "172.0.0.1");
}

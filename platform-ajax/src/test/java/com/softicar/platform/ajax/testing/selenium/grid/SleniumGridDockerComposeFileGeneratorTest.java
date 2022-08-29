package com.softicar.platform.ajax.testing.selenium.grid;

import com.softicar.platform.ajax.testing.selenium.AjaxSeleniumTestProperties;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.grid.SeleniumGridConfiguration;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.hub.SeleniumHubConfiguration;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.network.SeleniumNetworkConfiguration;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.node.SeleniumNodeConfiguration;
import com.softicar.platform.common.testing.AbstractTest;
import java.math.BigDecimal;
import org.junit.Test;

public class SleniumGridDockerComposeFileGeneratorTest extends AbstractTest {

	private final SleniumGridDockerComposeFileGenerator generator;

	public SleniumGridDockerComposeFileGeneratorTest() {

		this.generator = new SleniumGridDockerComposeFileGenerator();
	}

	@Test
	public void testGenerate() {

		SeleniumGridConfiguration gridConfiguration = createGridConfiguration()
			.setHubConfiguration(createHubConfiguration())
			.addNodeConfiguration(createChromeNodeConfiguration())
			.addNodeConfiguration(createFirefoxNodeConfiguration())
			.setNetworkConfiguration(createNetworkConfiguration());

		String fileContent = generator.generate(gridConfiguration);

		assertEquals(getExpectedFileContent(), fileContent);
	}

	private SeleniumGridConfiguration createGridConfiguration() {

		return new SeleniumGridConfiguration()//
			.setContainerVersion(getExpectedGridContainerVersion())
			.setUuid("29347bab-d26c-4b59-97f3-fea59c8d70fa")
			.setWorkerThreadCount(2)
			.setTemporaryDirectory(System.getProperty("java.io.tmpdir") + "/selenium-grid-29347bab-d26c-4b59-97f3-fea59c8d70fa")
			.setDeferredGridShutdown(true);
	}

	private SeleniumHubConfiguration createHubConfiguration() {

		return new SeleniumHubConfiguration()//
			.setBrowserTimeout(60)
			.setIp("172.0.0.222")
			.setMaximumSessionCount(1)
			.setPortExternal(4444)
			.setPortEventBusPublish(4442)
			.setPortEventBusSubscribe(4443)
			.setSessionTimeout(20);
	}

	private SeleniumNodeConfiguration createFirefoxNodeConfiguration() {

		return new SeleniumNodeConfiguration()//
			.setImageName("node-firefox")
			.setNamePrefix("selenium-node-firefox")
			.setFactor(BigDecimal.valueOf(1.5));
	}

	private SeleniumNodeConfiguration createChromeNodeConfiguration() {

		return new SeleniumNodeConfiguration()//
			.setImageName("node-chrome")
			.setNamePrefix("selenium-node-chrome")
			.setFactor(BigDecimal.valueOf(1));
	}

	private SeleniumNetworkConfiguration createNetworkConfiguration() {

		return new SeleniumNetworkConfiguration()//
			.setSubnetwork("172.0.0.0/24");
	}

	private String getExpectedFileContent() {

		StringBuilder output = new StringBuilder();
		output.append("version: \"3\"\n");
		output.append("services:\n");
		output.append("  selenium-hub-29347bab-d26c-4b59-97f3-fea59c8d70fa:\n");
		output.append("    image: selenium/hub:%s\n".formatted(getExpectedGridContainerVersion()));
		output.append("    container_name: selenium-hub-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("    environment:\n");
		output.append("      - GRID_BROWSER_TIMEOUT=60\n");
		output.append("      - GRID_TIMEOUT=20\n");
		output.append("      - GRID_MAX_SESSION=1\n");
		output.append("    ports:\n");
		output.append("      - 4444\n");
		output.append("      - 4442\n");
		output.append("      - 4443\n");
		output.append("    networks:\n");
		output.append("      selenium-grid-network-29347bab-d26c-4b59-97f3-fea59c8d70fa:\n");
		output.append("        ipv4_address: \"172.0.0.222\"\n");
		output.append("\n");
		output.append("  selenium-node-chrome-29347bab-d26c-4b59-97f3-fea59c8d70fa_1:\n");
		output.append("    image: selenium/node-chrome:%s\n".formatted(getExpectedGridContainerVersion()));
		output.append("    container_name: selenium-node-chrome-29347bab-d26c-4b59-97f3-fea59c8d70fa_1\n");
		output.append("    volumes:\n");
		output.append("      - /dev/shm:/dev/shm\n");
		output.append("    depends_on:\n");
		output.append("      - selenium-hub-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("    environment:\n");
		output.append("      - SE_EVENT_BUS_HOST=selenium-hub-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("      - SE_EVENT_BUS_PUBLISH_PORT=4442\n");
		output.append("      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443\n");
		output.append("    networks:\n");
		output.append("      - selenium-grid-network-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("\n");
		output.append("  selenium-node-chrome-29347bab-d26c-4b59-97f3-fea59c8d70fa_2:\n");
		output.append("    image: selenium/node-chrome:%s\n".formatted(getExpectedGridContainerVersion()));
		output.append("    container_name: selenium-node-chrome-29347bab-d26c-4b59-97f3-fea59c8d70fa_2\n");
		output.append("    volumes:\n");
		output.append("      - /dev/shm:/dev/shm\n");
		output.append("    depends_on:\n");
		output.append("      - selenium-hub-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("    environment:\n");
		output.append("      - SE_EVENT_BUS_HOST=selenium-hub-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("      - SE_EVENT_BUS_PUBLISH_PORT=4442\n");
		output.append("      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443\n");
		output.append("    networks:\n");
		output.append("      - selenium-grid-network-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("\n");
		output.append("  selenium-node-firefox-29347bab-d26c-4b59-97f3-fea59c8d70fa_1:\n");
		output.append("    image: selenium/node-firefox:%s\n".formatted(getExpectedGridContainerVersion()));
		output.append("    container_name: selenium-node-firefox-29347bab-d26c-4b59-97f3-fea59c8d70fa_1\n");
		output.append("    volumes:\n");
		output.append("      - /dev/shm:/dev/shm\n");
		output.append("    depends_on:\n");
		output.append("      - selenium-hub-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("    environment:\n");
		output.append("      - SE_EVENT_BUS_HOST=selenium-hub-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("      - SE_EVENT_BUS_PUBLISH_PORT=4442\n");
		output.append("      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443\n");
		output.append("    networks:\n");
		output.append("      - selenium-grid-network-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("\n");
		output.append("  selenium-node-firefox-29347bab-d26c-4b59-97f3-fea59c8d70fa_2:\n");
		output.append("    image: selenium/node-firefox:%s\n".formatted(getExpectedGridContainerVersion()));
		output.append("    container_name: selenium-node-firefox-29347bab-d26c-4b59-97f3-fea59c8d70fa_2\n");
		output.append("    volumes:\n");
		output.append("      - /dev/shm:/dev/shm\n");
		output.append("    depends_on:\n");
		output.append("      - selenium-hub-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("    environment:\n");
		output.append("      - SE_EVENT_BUS_HOST=selenium-hub-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("      - SE_EVENT_BUS_PUBLISH_PORT=4442\n");
		output.append("      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443\n");
		output.append("    networks:\n");
		output.append("      - selenium-grid-network-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("\n");
		output.append("  selenium-node-firefox-29347bab-d26c-4b59-97f3-fea59c8d70fa_3:\n");
		output.append("    image: selenium/node-firefox:%s\n".formatted(getExpectedGridContainerVersion()));
		output.append("    container_name: selenium-node-firefox-29347bab-d26c-4b59-97f3-fea59c8d70fa_3\n");
		output.append("    volumes:\n");
		output.append("      - /dev/shm:/dev/shm\n");
		output.append("    depends_on:\n");
		output.append("      - selenium-hub-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("    environment:\n");
		output.append("      - SE_EVENT_BUS_HOST=selenium-hub-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("      - SE_EVENT_BUS_PUBLISH_PORT=4442\n");
		output.append("      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443\n");
		output.append("    networks:\n");
		output.append("      - selenium-grid-network-29347bab-d26c-4b59-97f3-fea59c8d70fa\n");
		output.append("\n");
		output.append("networks:\n");
		output.append("  selenium-grid-network-29347bab-d26c-4b59-97f3-fea59c8d70fa:\n");
		output.append("    driver: bridge\n");
		output.append("    ipam:\n");
		output.append("      driver: default\n");
		output.append("      config:\n");
		output.append("        - subnet: \"172.0.0.0/24\"\n");
		output.append("\n");
		return output.toString();
	}

	private String getExpectedGridContainerVersion() {

		String version = AjaxSeleniumTestProperties.GRID_CONTAINER_VERSION.getValue();
		assertFalse(version.isBlank());
		return version;
	}
}

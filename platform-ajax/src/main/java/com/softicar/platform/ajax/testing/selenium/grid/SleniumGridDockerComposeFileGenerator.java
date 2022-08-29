package com.softicar.platform.ajax.testing.selenium.grid;

import com.softicar.platform.ajax.testing.selenium.grid.configuration.grid.ISeleniumGridConfiguration;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.hub.ISeleniumHubConfiguration;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.network.ISeleniumNetworkConfiguration;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.node.ISeleniumNodeConfiguration;
import java.util.Collection;

/**
 * Generates the content of a <tt>docker-compose</tt> YAML file which defines a
 * Selenium grid as a multi-container application.
 *
 * @author Alexander Schmidt
 */
public class SleniumGridDockerComposeFileGenerator {

	private ISeleniumGridConfiguration gridConfiguration;
	private ISeleniumHubConfiguration hubConfiguration;
	private Collection<ISeleniumNodeConfiguration> nodeConfigurations;
	private ISeleniumNetworkConfiguration networkConfiguration;

	public SleniumGridDockerComposeFileGenerator() {

		this.gridConfiguration = null;
		this.hubConfiguration = null;
		this.nodeConfigurations = null;
		this.networkConfiguration = null;
	}

	/**
	 * Generates the content of the <tt>docker-compose</tt> definition file, in
	 * YAML format, based upon the given {@link ISeleniumGridConfiguration}.
	 *
	 * @param gridConfiguration
	 *            {@link ISeleniumGridConfiguration} which defines the
	 *            properties of the Selenium grid (never <i>null</i>)
	 * @return the content of the generated <tt>docker-compose</tt> definition
	 *         file, in YAML format (never <i>null</i>)
	 */
	public String generate(ISeleniumGridConfiguration gridConfiguration) {

		this.gridConfiguration = gridConfiguration.validate();
		this.hubConfiguration = gridConfiguration.getHubConfiguration();
		this.nodeConfigurations = gridConfiguration.getNodeConfigurations();
		this.networkConfiguration = gridConfiguration.getNetworkConfiguration();

		OutputBuilder builder = new OutputBuilder();
		appendHeader(builder);
		appendServicesSection(builder);
		appendSeleniumHubService(builder);
		appendNodeServices(builder);
		appendNetworksSection(builder);
		appendNetwork(builder);

		return builder.toString();
	}

	private void appendHeader(OutputBuilder builder) {

		builder.appendLine("version: \"%s\"", 3);
	}

	private void appendServicesSection(OutputBuilder builder) {

		builder.appendLine("services:");
	}

	private void appendNetworksSection(OutputBuilder builder) {

		builder.appendLine("networks:");
	}

	private void appendSeleniumHubService(OutputBuilder builder) {

		builder.appendLine("  %s:", generateHubName());
		builder.appendLine("    image: selenium/hub:%s", gridConfiguration.getContainerVersion());
		builder.appendLine("    container_name: %s", generateHubName());
		builder.appendLine("    environment:");
		builder.appendLine("      - GRID_BROWSER_TIMEOUT=%s", hubConfiguration.getBrowserTimeout());
		builder.appendLine("      - GRID_TIMEOUT=%s", hubConfiguration.getSessionTimeout());
		builder.appendLine("      - GRID_MAX_SESSION=%s", hubConfiguration.getMaximumSessionCount());
		builder.appendLine("    ports:");
		builder.appendLine("      - %s", hubConfiguration.getPortExternal());
		builder.appendLine("      - %s", hubConfiguration.getPortEventBusPublish());
		builder.appendLine("      - %s", hubConfiguration.getPortEventBusSubscribe());
		builder.appendLine("    networks:");
		builder.appendLine("      %s:", generateNetworkName());
		builder.appendLine("        ipv4_address: \"%s\"", hubConfiguration.getIp());
		builder.appendLine("");
	}

	private void appendNodeServices(OutputBuilder builder) {

		for (ISeleniumNodeConfiguration nodeConfiguration: nodeConfigurations) {
			for (int nodeIndex = 1; nodeIndex <= calculateNodeCount(nodeConfiguration); nodeIndex++) {
				builder.appendLine("  %s:", generateNodeName(nodeConfiguration, nodeIndex));
				builder.appendLine("    image: selenium/%s:%s", nodeConfiguration.getImageName(), gridConfiguration.getContainerVersion());
				builder.appendLine("    container_name: %s", generateNodeName(nodeConfiguration, nodeIndex));
				builder.appendLine("    volumes:");
				builder.appendLine("      - /dev/shm:/dev/shm");
				builder.appendLine("    depends_on:");
				builder.appendLine("      - %s", generateHubName());
				builder.appendLine("    environment:");
				builder.appendLine("      - SE_EVENT_BUS_HOST=%s", generateHubName());
				builder.appendLine("      - SE_EVENT_BUS_PUBLISH_PORT=%s", hubConfiguration.getPortEventBusPublish());
				builder.appendLine("      - SE_EVENT_BUS_SUBSCRIBE_PORT=%s", hubConfiguration.getPortEventBusSubscribe());
				builder.appendLine("    networks:");
				builder.appendLine("      - %s", generateNetworkName());
				builder.appendLine("");
			}
		}
	}

	private void appendNetwork(OutputBuilder builder) {

		builder.appendLine("  %s:", generateNetworkName());
		builder.appendLine("    driver: bridge");
		builder.appendLine("    ipam:");
		builder.appendLine("      driver: default");
		builder.appendLine("      config:");
		builder.appendLine("        - subnet: \"%s\"", networkConfiguration.getSubnetwork());
		builder.appendLine("");
	}

	private String generateHubName() {

		return String.format("selenium-hub-%s", gridConfiguration.getUuid());
	}

	private String generateNodeName(ISeleniumNodeConfiguration nodeConfiguration, int nodeIndex) {

		return String.format("%s-%s_%s", nodeConfiguration.getNamePrefix(), gridConfiguration.getUuid(), nodeIndex);
	}

	private String generateNetworkName() {

		return String.format("selenium-grid-network-%s", gridConfiguration.getUuid());
	}

	private int calculateNodeCount(ISeleniumNodeConfiguration nodeConfiguration) {

		return (int) Math.round(nodeConfiguration.getFactor().doubleValue() * gridConfiguration.getWorkerThreadCount());
	}

	private static class OutputBuilder {

		private final StringBuilder output;

		public OutputBuilder() {

			this.output = new StringBuilder();
		}

		public OutputBuilder appendLine(String format, Object...args) {

			output.append(String.format(format, args) + "\n");
			return this;
		}

		@Override
		public String toString() {

			return output.toString();
		}
	}
}

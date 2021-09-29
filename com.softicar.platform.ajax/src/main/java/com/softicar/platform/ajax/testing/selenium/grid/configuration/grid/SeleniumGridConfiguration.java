package com.softicar.platform.ajax.testing.selenium.grid.configuration.grid;

import com.softicar.platform.ajax.testing.selenium.grid.configuration.hub.ISeleniumHubConfiguration;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.network.ISeleniumNetworkConfiguration;
import com.softicar.platform.ajax.testing.selenium.grid.configuration.node.ISeleniumNodeConfiguration;
import com.softicar.platform.common.core.properties.IProperty;
import java.util.ArrayList;
import java.util.Collection;

public class SeleniumGridConfiguration implements ISeleniumGridConfiguration {

	private String containerVersion;
	private String uuid;
	private Integer workerThreadCount;
	private String temporaryDirectory;
	private Boolean deferredGridShutdown;
	private ISeleniumHubConfiguration hubConfiguration;
	private final Collection<ISeleniumNodeConfiguration> nodeConfigurations = new ArrayList<>();
	private ISeleniumNetworkConfiguration networkConfiguration;

	@Override
	public String getContainerVersion() {

		return containerVersion;
	}

	public SeleniumGridConfiguration setContainerVersion(String containerVersion) {

		this.containerVersion = containerVersion;
		return this;
	}

	public SeleniumGridConfiguration setContainerVersion(IProperty<String> containerVersion) {

		return setContainerVersion(containerVersion.getValue());
	}

	@Override
	public String getUuid() {

		return uuid;
	}

	public SeleniumGridConfiguration setUuid(String uuid) {

		this.uuid = uuid;
		return this;
	}

	public SeleniumGridConfiguration setUuid(IProperty<String> uuid) {

		return setUuid(uuid.getValue());
	}

	@Override
	public Integer getWorkerThreadCount() {

		return workerThreadCount;
	}

	public SeleniumGridConfiguration setWorkerThreadCount(Integer workerThreadCount) {

		this.workerThreadCount = workerThreadCount;
		return this;
	}

	public SeleniumGridConfiguration setWorkerThreadCount(IProperty<Integer> workerThreadCount) {

		return setWorkerThreadCount(workerThreadCount.getValue());
	}

	@Override
	public String getTemporaryDirectory() {

		return temporaryDirectory;
	}

	public SeleniumGridConfiguration setTemporaryDirectory(String temporaryDirectory) {

		this.temporaryDirectory = temporaryDirectory;
		return this;
	}

	public SeleniumGridConfiguration setTemporaryDirectory(IProperty<String> temporaryDirectory) {

		return setTemporaryDirectory(temporaryDirectory.getValue());
	}

	@Override
	public Boolean isDeferredGridShutdown() {

		return deferredGridShutdown;
	}

	public SeleniumGridConfiguration setDeferredGridShutdown(Boolean deferredGridShutdown) {

		this.deferredGridShutdown = deferredGridShutdown;
		return this;
	}

	public SeleniumGridConfiguration setDeferredGridShutdown(IProperty<Boolean> deferredGridShutdown) {

		return setDeferredGridShutdown(deferredGridShutdown.getValue());
	}

	@Override
	public ISeleniumHubConfiguration getHubConfiguration() {

		return hubConfiguration;
	}

	public SeleniumGridConfiguration setHubConfiguration(ISeleniumHubConfiguration hubConfiguration) {

		this.hubConfiguration = hubConfiguration;
		return this;
	}

	@Override
	public ISeleniumNetworkConfiguration getNetworkConfiguration() {

		return networkConfiguration;
	}

	public SeleniumGridConfiguration setNetworkConfiguration(ISeleniumNetworkConfiguration networkConfiguration) {

		this.networkConfiguration = networkConfiguration;
		return this;
	}

	@Override
	public Collection<ISeleniumNodeConfiguration> getNodeConfigurations() {

		return nodeConfigurations;
	}

	public SeleniumGridConfiguration addNodeConfiguration(ISeleniumNodeConfiguration nodeConfiguration) {

		this.nodeConfigurations.add(nodeConfiguration);
		return this;
	}
}

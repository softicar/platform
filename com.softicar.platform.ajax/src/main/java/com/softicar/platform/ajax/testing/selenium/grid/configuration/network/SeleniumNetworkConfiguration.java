package com.softicar.platform.ajax.testing.selenium.grid.configuration.network;

import com.softicar.platform.common.core.properties.IProperty;

public class SeleniumNetworkConfiguration implements ISeleniumNetworkConfiguration {

	private String subnetwork;

	@Override
	public String getSubnetwork() {

		return subnetwork;
	}

	public SeleniumNetworkConfiguration setSubnetwork(String subnetwork) {

		this.subnetwork = subnetwork;
		return this;
	}

	public SeleniumNetworkConfiguration setSubnetwork(IProperty<String> subnetwork) {

		return setSubnetwork(subnetwork.getValue());
	}
}

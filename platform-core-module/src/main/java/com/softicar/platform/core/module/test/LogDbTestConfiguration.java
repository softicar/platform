package com.softicar.platform.core.module.test;

import com.softicar.platform.core.module.log.configuration.ILogDbConfiguration;

public class LogDbTestConfiguration implements ILogDbConfiguration {

	@Override
	public boolean isEnabled() {

		return true;
	}
}
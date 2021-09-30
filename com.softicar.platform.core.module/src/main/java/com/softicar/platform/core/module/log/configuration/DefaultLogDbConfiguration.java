package com.softicar.platform.core.module.log.configuration;

import com.softicar.platform.core.module.environment.LiveSystem;

public class DefaultLogDbConfiguration implements ILogDbConfiguration {

	private final boolean enabled;

	public DefaultLogDbConfiguration() {

		this.enabled = LiveSystem.isLiveSystem();
	}

	@Override
	public boolean isEnabled() {

		return enabled;
	}
}

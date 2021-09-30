package com.softicar.platform.core.module.log.configuration;

import com.softicar.platform.common.core.singleton.Singleton;

public class CurrentLogDbConfiguration {

	private static final Singleton<ILogDbConfiguration> INSTANCE = new Singleton<>(DefaultLogDbConfiguration::new);

	public static ILogDbConfiguration get() {

		return INSTANCE.get();
	}

	public static void set(ILogDbConfiguration configuration) {

		INSTANCE.set(configuration);
	}
}

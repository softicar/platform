package com.softicar.platform.common.core.properties;

import java.nio.file.Path;

// TODO move to somewhere else?
// TODO javadoc
// TODO unit test
public class SofticarHome {

	private static final String HOME_DIRECTORY_NAME = ".softicar";

	// TODO javadoc
	// TODO unit test
	public static Path getPath() {

		return Path.of(SystemPropertyEnum.USER_HOME.get(), HOME_DIRECTORY_NAME);
	}

	// TODO javadoc
	// TODO unit test
	public static Path getLogPath() {

		return getPath().resolve("log");
	}
}

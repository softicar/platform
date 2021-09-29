package com.softicar.platform.common.core.logger;

import com.softicar.platform.common.core.logging.LogLevel;

public class DevNullLogger extends AbstractLogger {

	@Override
	public void doLog(LogLevel logLevel, String text) {

		// nothing to do
	}
}

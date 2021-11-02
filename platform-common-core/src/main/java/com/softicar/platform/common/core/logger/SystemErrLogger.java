package com.softicar.platform.common.core.logger;

import com.softicar.platform.common.core.logging.LogLevel;

/**
 * An implementation of {@link ILogger} that logs to the system error stream.
 *
 * @author Oliver Richers
 */
public class SystemErrLogger extends AbstractLogger {

	@Override
	public void doLog(LogLevel logLevel, String text) {

		System.err.println(logLevel.name() + ": " + text);
	}
}

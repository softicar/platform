package com.softicar.platform.common.core.logger;

import com.softicar.platform.common.core.logging.LogLevel;

/**
 * An implementation of {@link ILogger} that logs to the system output stream.
 *
 * @author Oliver Richers
 */
public class SystemOutLogger extends AbstractLogger {

	@Override
	public void doLog(LogLevel logLevel, String text) {

		System.out.println(logLevel.name() + ": " + text);
	}
}

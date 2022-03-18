package com.softicar.platform.common.core.logger;

import com.softicar.platform.common.core.logging.LogLevel;

public abstract class AbstractLogger implements ILogger {

	@Override
	public void verbose(String text, Object...arguments) {

		log(LogLevel.VERBOSE, text, arguments);
	}

	@Override
	public void info(String text, Object...arguments) {

		log(LogLevel.INFO, text, arguments);
	}

	@Override
	public void warning(String text, Object...arguments) {

		log(LogLevel.WARNING, text, arguments);
	}

	@Override
	public void error(String text, Object...arguments) {

		log(LogLevel.ERROR, text, arguments);
	}

	@Override
	public void log(LogLevel logLevel, String text, Object...arguments) {

		doLog(logLevel, String.format(text, arguments));
	}

	protected abstract void doLog(LogLevel logLevel, String text);
}

package com.softicar.platform.common.core.logger;

import com.softicar.platform.common.core.logging.LogLevel;

public class BufferedLoggerLine {

	private final LogLevel logLevel;
	private final String text;

	public BufferedLoggerLine(LogLevel logLevel, String text) {

		this.logLevel = logLevel;
		this.text = text;
	}

	public LogLevel getLogLevel() {

		return logLevel;
	}

	public String getText() {

		return text;
	}
}

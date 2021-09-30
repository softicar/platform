package com.softicar.platform.common.core.logger;

import com.softicar.platform.common.core.logging.LogLevel;

public interface ILogger {

	void verbose(String text, Object...arguments);

	void info(String text, Object...arguments);

	void warning(String text, Object...arguments);

	void error(String text, Object...arguments);

	void log(LogLevel logLevel, String text, Object...arguments);
}

package com.softicar.platform.common.core.logger;

import com.softicar.platform.common.core.logging.LogLevel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BufferedLogger extends AbstractLogger {

	private final List<BufferedLoggerLine> lines;

	public BufferedLogger() {

		this.lines = new ArrayList<>();
	}

	public void clear() {

		lines.clear();
	}

	public Collection<BufferedLoggerLine> getLines() {

		return lines;
	}

	@Override
	public void doLog(LogLevel logLevel, String text) {

		lines.add(new BufferedLoggerLine(logLevel, text));
	}
}

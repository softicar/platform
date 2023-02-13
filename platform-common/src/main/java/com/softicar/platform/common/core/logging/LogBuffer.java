package com.softicar.platform.common.core.logging;

/**
 * In-memory implementation of {@link ILogOutput}.
 * <p>
 * Stores all log output internally in a {@link StringBuilder}. The logged text
 * can be returned using {@link #toString()}.
 *
 * @author Oliver Richers
 */
public class LogBuffer implements ILogOutput {

	private final StringBuilder builder;

	public LogBuffer() {

		this.builder = new StringBuilder();
	}

	@Override
	public synchronized void logLine(String line) {

		builder.append(line).append("\n");
	}

	/**
	 * Returns all logged text.
	 */
	@Override
	public synchronized String toString() {

		return builder.toString();
	}

	/**
	 * Clears this buffer by discarding all previously logged text.
	 */
	public synchronized void clear() {

		builder.setLength(0);
	}
}

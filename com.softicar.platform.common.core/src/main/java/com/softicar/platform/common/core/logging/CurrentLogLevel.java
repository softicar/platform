package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * This is a {@link Singleton} to hold the currently selected {@link LogLevel}.
 * <p>
 * It is primarily used by the class {@link Log} to control the desired amount
 * of logging messages.
 *
 * @author Oliver Richers
 */
public class CurrentLogLevel {

	private static final Singleton<LogLevel> LOG_LEVEL = new Singleton<>(() -> LogLevel.INFO).setInheritByIdentity();

	/**
	 * Returns the currently used log level.
	 *
	 * @return the current log level, never null
	 */
	public static LogLevel get() {

		return LOG_LEVEL.get();
	}

	/**
	 * Sets the new log level to use.
	 */
	public static void set(LogLevel logLevel) {

		LOG_LEVEL.set(logLevel);
	}

	/**
	 * Resets the current log level to default.
	 */
	public static void reset() {

		set(null);
	}
}

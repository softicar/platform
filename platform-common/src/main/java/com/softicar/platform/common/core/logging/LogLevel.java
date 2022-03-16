package com.softicar.platform.common.core.logging;

/**
 * The enum constants for all available logging levels.
 * <p>
 * Each log level has a unique priority, which is an integer in the range
 * [-1,3]. The lower this value, the higher the priority of the log level. For
 * example, the panic log level has a priority of -1 while the verbose log level
 * has a priority of 3.
 * 
 * @author Oliver Richers
 */
public enum LogLevel {
	// The order of the constants is important.
	PANIC,
	ERROR,
	WARNING,
	INFO,
	VERBOSE;

	/**
	 * Returns the log level associated with the specified priority.
	 * 
	 * @param priority
	 *            the priority of the log level to return
	 * @return the matching log level, never null
	 */
	public static LogLevel get(int priority) {

		return values()[priority + 1];
	}

	/**
	 * Returns the log level with the specified name.
	 * 
	 * @param name
	 *            the name of the log level to return
	 * @return the matching log level, never null
	 */
	public static LogLevel get(String name) {

		return valueOf(name);
	}

	/**
	 * Returns the priority of this log level.
	 * 
	 * @return the priority of this log level in the range [-1,3]
	 */
	public int getPriority() {

		return ordinal() - 1;
	}

	/**
	 * Sets the global variable {@link CurrentLogLevel} to this log level.
	 */
	public void set() {

		CurrentLogLevel.set(this);
	}
}

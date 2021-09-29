package com.softicar.platform.common.core.logging;

/**
 * Provides some static logging methods.
 *
 * @author Oliver Richers
 */
public class Log {

	public static final int PANIC_LEVEL = -1;
	public static final int ERROR_LEVEL = 0;
	public static final int WARNING_LEVEL = 1;
	public static final int INFO_LEVEL = 2;
	public static final int VERBOSE_LEVEL = 3;
	public static final int DEFAULT_LEVEL = INFO_LEVEL;

	// -------------------------------- LOG LEVEL -------------------------------- //

	/**
	 * Converts the log level id to a long level name.
	 *
	 * @param id
	 *            the id of the log level
	 * @return the name of the log level
	 */
	public static String getLogLevelName(int id) {

		return LogLevel.get(id).toString();
	}

	public static void setLogLevel(int logLevelPriority) {

		LogLevel.get(logLevelPriority).set();
	}

	// -------------------------------- BASIC LOGGING FUNCTIONS -------------------------------- //

	public static void flog(int logLevel, String msg, Object...args) {

		if (logLevel <= getCurrentLogPriority()) {
			log(logLevel, String.format(msg, args));
		}
	}

	public static void log(int logLevel, String message) {

		if (logLevel <= getCurrentLogPriority()) {
			if (logLevel == LogLevel.VERBOSE.getPriority()) {
				StackTraceElement element = findStackTraceElement();
				if (element != null) {
					message = String.format("@%s %s:%s: %s", System.currentTimeMillis(), element.getClassName(), element.getLineNumber(), message);
				} else {
					message = String.format("@%s: %s", System.currentTimeMillis(), message);
				}
			}
			CurrentLogOuput.get().logLine(message);
		}
	}

	private static StackTraceElement findStackTraceElement() {

		for (StackTraceElement element: Thread.currentThread().getStackTrace()) {
			String className = element.getClassName();

			if (!IgnoredPackagesForLogging.get().isIgnored(className)) {
				return element;
			}
		}
		return null;
	}

	private static int getCurrentLogPriority() {

		return CurrentLogLevel.get().getPriority();
	}

	// -------------------------------- COMMON LOGGING FUNCTIONS -------------------------------- //

	//
	// ERROR
	//

	public static void error(Object msg) {

		error(msg + "");
	}

	public static void error(String msg) {

		ferror(msg);
	}

	public static void ferror(Object msg) {

		ferror(msg + "");
	}

	public static void ferror(String msg) {

		log(ERROR_LEVEL, msg);
	}

	public static void ferror(String msg, Object...args) {

		flog(ERROR_LEVEL, msg, args);
	}

	//
	// WARNING
	//

	public static void warning(Object msg) {

		warning(msg + "");
	}

	public static void warning(String msg) {

		fwarning(msg);
	}

	public static void fwarning(Object msg) {

		fwarning(msg + "");
	}

	public static void fwarning(String msg) {

		log(WARNING_LEVEL, msg);
	}

	public static void fwarning(String msg, Object...args) {

		flog(WARNING_LEVEL, msg, args);
	}

	//
	// INFO
	//

	public static void info(Object msg) {

		info(msg + "");
	}

	public static void info(String msg) {

		finfo(msg);
	}

	public static void finfo(Object msg) {

		finfo(msg + "");
	}

	public static void finfo(String msg) {

		log(INFO_LEVEL, msg);
	}

	public static void finfo(String msg, Object...args) {

		flog(INFO_LEVEL, msg, args);
	}

	//
	// VERBOSE
	//

	public static void verbose(Object msg) {

		verbose(msg + "");
	}

	public static void verbose(String msg) {

		fverbose(msg);
	}

	public static void fverbose(Object msg) {

		fverbose(msg + "");
	}

	public static void fverbose(String msg) {

		log(VERBOSE_LEVEL, msg);
	}

	public static void fverbose(String msg, Object...args) {

		flog(VERBOSE_LEVEL, msg, args);
	}
}

package com.softicar.platform.common.core.java.stack.trace;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Utility class for Java stack traces.
 *
 * @author Oliver Richers
 */
public class JavaStackTraces {

	/**
	 * Creates a string describing the given {@link Throwable} including its
	 * complete stack.
	 * <p>
	 * This method also lists all causing exceptions.
	 *
	 * @param throwable
	 *            the {@link Throwable} to format
	 * @return string describing the {@link Throwable}
	 */
	public static String getStackTraceAsString(Throwable throwable) {

		StringWriter stringWriter = new StringWriter();
		try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
			throwable.printStackTrace(printWriter);
		}
		return stringWriter.toString();
	}
}

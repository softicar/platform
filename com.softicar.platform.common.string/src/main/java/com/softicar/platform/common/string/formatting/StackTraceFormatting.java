package com.softicar.platform.common.string.formatting;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

/**
 * Formatting methods for stack traces.
 *
 * @author Oliver Richers
 */
public class StackTraceFormatting {

	/**
	 * Creates a string which lists the stack trace elements.
	 * <p>
	 * Each stack trace element is put on a line with the class name, method
	 * name, filename and line number.
	 *
	 * @param elements
	 *            the stack trace elements to format
	 * @return string with details of all stack trace elements
	 */
	public static String getStackTraceAsString(Collection<StackTraceElement> elements) {

		StringBuilder builder = new StringBuilder();

		for (StackTraceElement element: elements) {
			builder.append(element.getClassName() + "." + element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + ");\n");
		}

		return builder.toString();
	}

	public static String getStackTraceAsString(StackTraceElement[] elements) {

		return getStackTraceAsString(Arrays.asList(elements));
	}

	/**
	 * Creates a string describing the given exception including its complete
	 * stack.
	 * <p>
	 * This method also lists all causing exceptions.
	 *
	 * @param throwable
	 *            the exception to format
	 * @return string describing the exception
	 */
	public static String getStackTraceAsString(Throwable throwable) {

		StringWriter stringWriter = new StringWriter();
		try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
			throwable.printStackTrace(printWriter);
		}
		return stringWriter.toString();
	}
}

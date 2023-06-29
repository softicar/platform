package com.softicar.platform.common.io.writer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;

/**
 * Utility methods for {@link PrintWriter}.
 *
 * @author Oliver Richers
 */
public class PrintWriters {

	/**
	 * Calls the {@link Consumer} with a new {@link PrintWriter} and returns the
	 * printed {@link String}.
	 *
	 * @param consumer
	 *            the consumer (never <i>null</i>)
	 * @return the string printed by the consumer
	 */
	public static String printToString(Consumer<PrintWriter> consumer) {

		var stringWriter = new StringWriter();
		try (var printWriter = new PrintWriter(stringWriter)) {
			consumer.accept(printWriter);
		}
		return stringWriter.toString();
	}
}

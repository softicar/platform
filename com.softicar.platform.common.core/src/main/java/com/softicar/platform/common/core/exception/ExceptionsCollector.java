package com.softicar.platform.common.core.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * Collects exceptions and aggregates them into a single exception.
 *
 * @author Oliver Richers
 */
public class ExceptionsCollector {

	private final Map<List<StackTraceElement>, List<Throwable>> throwables;
	private String preamble;
	private int count;

	public ExceptionsCollector() {

		this.throwables = new HashMap<>();
		this.preamble = "";
		this.count = 0;
	}

	public ExceptionsCollector setPreamble(String preamble, Object...arguments) {

		this.preamble = String.format(preamble, arguments);
		return this;
	}

	public ExceptionsCollector add(Throwable throwable) {

		List<StackTraceElement> stackTrace = Arrays.asList(throwable.getStackTrace());
		throwables.computeIfAbsent(stackTrace, trace -> new ArrayList<>()).add(throwable);
		count++;
		return this;
	}

	public int getExceptionCount() {

		return count;
	}

	public boolean isEmpty() {

		return count == 0;
	}

	/**
	 * Returns the combined error message.
	 *
	 * @return the error message (never null)
	 */
	public String getMessage() {

		StringWriter buffer = new StringWriter();
		try (PrintWriter printer = new PrintWriter(buffer)) {
			printer.append(preamble);
			for (Entry<List<StackTraceElement>, List<Throwable>> entry: throwables.entrySet()) {
				printer.append("\n\n");
				printer.printf("Gathered %s exceptions with the same stacktrace.", entry.getValue().size());
				printer.append("\n\n");
				for (Throwable throwable: entry.getValue()) {
					throwable.printStackTrace(printer);
				}
			}
		}
		return buffer.toString();
	}

	/**
	 * Throws a single exception containing information about all collected
	 * exceptions.
	 * <p>
	 * If no exception was added, this method does nothing.
	 */
	public void throwExceptionIfNotEmpty() {

		if (count > 0) {
			throw new RuntimeException(getMessage());
		}
	}

	/**
	 * Calls the consumer with the return value of {@link #getMessage()}.
	 * <p>
	 * If no exception was added, this method does nothing.
	 */
	public void consumeIfNotEmpty(Consumer<String> messageConsumer) {

		if (count > 0) {
			messageConsumer.accept(getMessage());
		}
	}
}

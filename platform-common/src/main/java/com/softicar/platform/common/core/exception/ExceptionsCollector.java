package com.softicar.platform.common.core.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

/**
 * Collects exceptions and aggregates them into a single exception.
 *
 * @author Oliver Richers
 */
public class ExceptionsCollector {

	private final Map<String, ExceptionCollection> exceptionCollections;
	private String preamble;
	private int count;

	public ExceptionsCollector() {

		this.exceptionCollections = new TreeMap<>();
		this.preamble = "";
		this.count = 0;
	}

	public ExceptionsCollector setPreamble(String preamble, Object...arguments) {

		this.preamble = String.format(preamble, arguments);
		return this;
	}

	public ExceptionsCollector add(Throwable throwable) {

		List<StackTraceElement> stackTrace = List.of(throwable.getStackTrace());
		exceptionCollections//
			.computeIfAbsent(stackTrace.toString(), dummy -> new ExceptionCollection(stackTrace))
			.add(throwable);
		count++;
		return this;
	}

	public int getExceptionCount() {

		return count;
	}

	public Collection<ExceptionCollection> getExceptionCollections() {

		return Collections.unmodifiableCollection(exceptionCollections.values());
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
			for (var exceptionCollection: exceptionCollections.values()) {
				printer.append("\n\n");
				printer.printf("Gathered %s exceptions with the same stacktrace.", exceptionCollection.getThrowables().size());
				printer.append("\n\n");
				for (Throwable throwable: exceptionCollection.getThrowables()) {
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

	public static class ExceptionCollection {

		private final List<StackTraceElement> stackTraceElements;
		private final List<Throwable> throwables;

		public ExceptionCollection(List<StackTraceElement> stackTraceElements) {

			this.stackTraceElements = stackTraceElements;
			this.throwables = new ArrayList<>();
		}

		public List<StackTraceElement> getStackTraceElements() {

			return Collections.unmodifiableList(stackTraceElements);
		}

		public List<Throwable> getThrowables() {

			return Collections.unmodifiableList(throwables);
		}

		public void add(Throwable throwable) {

			this.throwables.add(throwable);
		}
	}
}

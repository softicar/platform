package com.softicar.platform.common.core.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MultiExceptionMessageBuilder {

	private final Collection<Throwable> exceptions;

	public MultiExceptionMessageBuilder(Collection<Throwable> exceptions) {

		this.exceptions = exceptions;
	}

	public String buildMessage() {

		StringWriter buffer = new StringWriter();
		try (PrintWriter printer = new PrintWriter(buffer)) {
			for (var exceptionList: getExceptionLists()) {
				printer.append("\n\n");
				printer.printf("Gathered %s exceptions with the same stacktrace.", exceptionList.size());
				printer.append("\n\n");
				for (Throwable exception: exceptionList) {
					exception.printStackTrace(printer);
				}
			}
		}
		return buffer.toString();
	}

	private Collection<List<Throwable>> getExceptionLists() {

		return exceptions//
			.stream()
			.collect(Collectors.groupingBy(this::getExceptionStackTraceKey, TreeMap::new, Collectors.toList()))
			.values();
	}

	private String getExceptionStackTraceKey(Throwable exception) {

		return List.of(exception.getStackTrace()).toString();
	}
}

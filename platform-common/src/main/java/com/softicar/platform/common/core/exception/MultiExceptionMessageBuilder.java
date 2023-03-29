package com.softicar.platform.common.core.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

class MultiExceptionMessageBuilder {

	private final Collection<Throwable> exceptions;

	public MultiExceptionMessageBuilder(Collection<Throwable> exceptions) {

		this.exceptions = exceptions;
	}

	public String buildMessage() {

		var buffer = new StringWriter();
		try (var printer = new PrintWriter(buffer)) {
			Collection<List<Throwable>> exceptionLists = getExceptionLists();
			if (!exceptionLists.isEmpty()) {
				printer.println();
				printer.println("Gathered a total of %s exceptions with %s different stack traces.".formatted(exceptions.size(), exceptionLists.size()));
				printer.println();
			}

			int index = 0;
			for (List<Throwable> exceptionList: exceptionLists) {
				printer.println("-------------------- %s exceptions with stack trace #%s --------------------".formatted(exceptionList.size(), index));
				printer.println();
				for (Throwable exception: exceptionList) {
					exception.printStackTrace(printer);
					printer.println();
				}
				index++;
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

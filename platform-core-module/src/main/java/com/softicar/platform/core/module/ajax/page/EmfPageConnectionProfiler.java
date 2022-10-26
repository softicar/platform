package com.softicar.platform.core.module.ajax.page;

import com.softicar.platform.ajax.document.IAjaxDocumentParameters;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.profiler.DbConnectionProfiler;
import com.softicar.platform.db.core.statement.IDbStatement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class EmfPageConnectionProfiler extends DbConnectionProfiler {

	public static final String PROFILER_PARAMETER = "profiler";
	public static final String STACKTRACE_PARAMETER = "stacktrace";
	private static final int STACK_TRACE_START = 2;
	private final int statementCount;
	private final Integer stacktraceLength;
	private final Map<String, Map<List<StackTraceElement>, Integer>> executionCountPerStatementPerStacktrace;

	public EmfPageConnectionProfiler(IAjaxDocumentParameters parameters) {

		this.statementCount = parameters.getParameterOrNull(PROFILER_PARAMETER, IntegerParser::parse).orElse(20);
		this.stacktraceLength = parameters.getParameterOrNull(STACKTRACE_PARAMETER, it -> IntegerParser.parse(it).orElse(1000));
		this.executionCountPerStatementPerStacktrace = new HashMap<>();
	}

	public static void installIfRequested(IAjaxDocumentParameters parameters) {

		if (parameters.getParameterOrNull(PROFILER_PARAMETER) != null) {
			EmfPageConnectionProfiler profiler = new EmfPageConnectionProfiler(parameters);
			DbConnections.setProfiler(profiler);
		}
	}

	@Override
	public void acceptStatementStarted(IDbStatement statement) {

		if (stacktraceLength != null) {
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			List<StackTraceElement> stackTrace = Arrays//
				.asList(stackTraceElements)
				.subList(STACK_TRACE_START, Math.min(stackTraceElements.length, STACK_TRACE_START + stacktraceLength));
			executionCountPerStatementPerStacktrace//
				.computeIfAbsent(statement.getText(), it -> new HashMap<>())
				.merge(stackTrace, 1, (a, b) -> a + b);
		}

		super.acceptStatementStarted(statement);
	}

	@Override
	public void afterConnectionClosed() {

		// print summary
		if (!getStatements().isEmpty()) {
			Log
				.finfo(//
					"[%6sms, %4sx] Total",
					getStatements()//
						.stream()
						.map(this::getTotalMilliseconds)
						.mapToLong(it -> it)
						.sum(),
					getStatements()//
						.stream()
						.map(this::getExecutionCount)
						.mapToLong(it -> it)
						.sum());
		}

		// print statements
		for (String statement: getStatementsWithHighestLoad(statementCount)) {
			Log.finfo("[%6sms, %4sx] %s", getTotalMilliseconds(statement), getExecutionCount(statement), statement);

			// print stack traces
			if (stacktraceLength != null) {
				for (Entry<List<StackTraceElement>, Integer> entry: executionCountPerStatementPerStacktrace//
					.get(statement)
					.entrySet()
					.stream()
					.sorted(Comparator.comparing(this::getCountFromEntry).reversed())
					.collect(Collectors.toList())) {

					Log.finfo("\t" + "[%4sx]", entry.getValue());

					for (StackTraceElement traceElement: entry.getKey()) {
						Log
							.finfo(//
								"\t\t%s.%s(%s:%s);",
								traceElement.getClassName(),
								traceElement.getMethodName(),
								traceElement.getFileName(),
								traceElement.getLineNumber());
					}
				}
			}
		}
	}

	private int getCountFromEntry(Entry<List<StackTraceElement>, Integer> entry) {

		return entry.getValue();
	}
}

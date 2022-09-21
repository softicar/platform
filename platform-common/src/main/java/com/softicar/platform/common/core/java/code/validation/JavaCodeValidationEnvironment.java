package com.softicar.platform.common.core.java.code.validation;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.io.serialization.json.JsonValueReader;
import java.util.Arrays;

public class JavaCodeValidationEnvironment {

	private static final String JAR_FILTER_PARAMETER_NAME = "jarFilterRegex";
	private final boolean verbose;
	private final JsonValueReader jsonValueReader;
	private final String jarFilterRegex;
	private String logPrefix;

	public JavaCodeValidationEnvironment(String[] arguments) {

		this.verbose = parseVerboseParameter(arguments);
		this.jsonValueReader = new JsonValueReader(getConfigurationJsonParameter(arguments));
		this.jarFilterRegex = jsonValueReader//
			.readValue(JAR_FILTER_PARAMETER_NAME)
			.orElseThrow(() -> new SofticarDeveloperException("Missing validator configuration parameter: %s", JAR_FILTER_PARAMETER_NAME));
		this.logPrefix = "";

	}

	public JsonValueReader getConfigurationJsonValueReader() {

		return jsonValueReader;
	}

	public String getJarFilterRegex() {

		return jarFilterRegex;
	}

	public void logVerbose(Class<?> context, String message, Object...arguments) {

		logVerbose(context.getSimpleName() + ": " + message.formatted(arguments));
	}

	public void logVerbose(String message, Object...arguments) {

		if (verbose) {
			System.err.println(logPrefix + String.format(message, arguments));
		}
	}

	void setLogPrefix(String logPrefix) {

		this.logPrefix = logPrefix;
	}

	private String getConfigurationJsonParameter(String[] arguments) {

		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i].equals("--configurationJson")) {
				if (i + 1 < arguments.length) {
					return arguments[i + 1];
				}
			}
		}
		return "";
	}

	private boolean parseVerboseParameter(String[] arguments) {

		return Arrays//
			.asList(arguments)
			.stream()
			.anyMatch(argument -> argument.equals("--verbose"));
	}
}

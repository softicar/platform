package com.softicar.platform.common.core.java.code.validation;

import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import com.softicar.platform.common.core.java.classpath.JavaClasspathLoader;
import com.softicar.platform.common.io.serialization.json.JsonValueReader;
import java.util.Arrays;

public class JavaCodeValidationEnvironment {

	private final JavaClasspath classPath;
	private final boolean verbose;
	private final JsonValueReader jsonValueReader;

	public JavaCodeValidationEnvironment(String[] arguments) {

		this.classPath = new JavaClasspathLoader().load();
		this.jsonValueReader = new JsonValueReader(getConfigurationJsonParameter(arguments));
		this.verbose = parseVerboseParameter(arguments);
	}

	public JavaClasspath getClassPath() {

		return classPath;
	}

	public JsonValueReader getConfigurationJsonValueReader() {

		return jsonValueReader;
	}

	public void logVerbose(String message, Object...arguments) {

		if (verbose) {
			System.err.println(String.format(message, arguments));
		}
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

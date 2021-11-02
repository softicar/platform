package com.softicar.platform.common.core.java.code.validation;

import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import com.softicar.platform.common.core.java.classpath.JavaClasspathLoader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class JavaCodeValidationEnvironment {

	private final JavaClasspath classPath;
	private final Set<String> mandatoryTranslations;
	private final boolean verbose;

	public JavaCodeValidationEnvironment(String[] arguments) {

		this.classPath = new JavaClasspathLoader().load();
		this.mandatoryTranslations = parseMandatoryTranslationsParameter(arguments);
		this.verbose = parseVerboseParameter(arguments);
	}

	public JavaClasspath getClassPath() {

		return classPath;
	}

	public Set<String> getMandatoryTranslations() {

		return mandatoryTranslations;
	}

	public void logVerbose(String message, Object...arguments) {

		if (verbose) {
			System.err.println(String.format(message, arguments));
		}
	}

	private Set<String> parseMandatoryTranslationsParameter(String[] arguments) {

		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i].equals("--mandatoryTranslations")) {
				if (i + 1 < arguments.length) {
					return Arrays//
						.asList(arguments[i + 1].split(","))
						.stream()
						.map(String::trim)
						.filter(language -> !language.isEmpty())
						.collect(Collectors.toCollection(TreeSet::new));
				}
			}
		}
		return Collections.emptySet();
	}

	private boolean parseVerboseParameter(String[] arguments) {

		return Arrays//
			.asList(arguments)
			.stream()
			.anyMatch(argument -> argument.equals("--verbose"));
	}
}

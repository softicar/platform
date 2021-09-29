package com.softicar.platform.common.core.java.classes.name.matcher;

/**
 * Converts a Java class name <i>glob pattern</i> into a regular expression.
 *
 * @see JavaClassNameGlobPatternMatcher
 * @author Oliver Richers
 */
class InternalGlobPatternToRegexConverter {

	private final String globPattern;

	public InternalGlobPatternToRegexConverter(String globPattern) {

		if (!globPattern.matches("[a-zA-Z0-9*_$\\.]+")) {
			throw new IllegalArgumentException(String.format("Illegal glob pattern: '%s'", globPattern));
		}

		this.globPattern = globPattern;
	}

	public String convert() {

		return globPattern//
			.replace(".", "\\.")
			.replaceAll("\\*\\*", "@")
			.replaceAll("\\*", "[^\\\\.]*")
			.replaceAll("@", ".*");
	}
}

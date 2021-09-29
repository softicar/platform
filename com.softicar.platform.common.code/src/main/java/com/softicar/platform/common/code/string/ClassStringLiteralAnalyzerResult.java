package com.softicar.platform.common.code.string;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class ClassStringLiteralAnalyzerResult {

	private final JavaClassName className;
	private final Set<String> stringLiterals;
	private boolean isEnum;

	public ClassStringLiteralAnalyzerResult(Class<?> classToAnalyze) {

		this.className = new JavaClassName(classToAnalyze);
		this.stringLiterals = new TreeSet<>();
		this.isEnum = false;
	}

	public boolean isEnum() {

		return isEnum;
	}

	public Set<String> getStringLiterals() {

		return Collections.unmodifiableSet(stringLiterals);
	}

	@Override
	public String toString() {

		if (stringLiterals.isEmpty()) {
			return String.format("No string literals in class '%s'.", className);
		}
		StringBuilder builder = new StringBuilder()//
			.append(String.format("String literals in class '%s':\n", className));
		stringLiterals.forEach(it -> builder.append("\t" + it + "\n"));
		return builder.toString();
	}

	protected void addStringLiteral(String stringLiteral) {

		stringLiterals.add(stringLiteral);
	}

	protected void addStringLiterals(Collection<String> stringLiterals) {

		stringLiterals.forEach(this::addStringLiteral);
	}

	protected void setEnum(boolean isEnum) {

		this.isEnum = isEnum;
	}
}

package com.softicar.platform.common.core.java.code.validator;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classpath.IJavaClasspathRoot;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import java.util.Collection;
import java.util.stream.Collectors;

public class JavaCodeValidatorClassesFinder {

	private static final String JAR_NAME_PREFIX = "com.softicar.";
	private final JavaCodeValidationEnvironment environment;

	public JavaCodeValidatorClassesFinder(JavaCodeValidationEnvironment environment) {

		this.environment = environment;
	}

	public Collection<AnalyzedJavaClass> findAll() {

		return environment//
			.getClassPath()
			.getAllRoots()
			.stream()
			.filter(this::isRelevantRoot)
			.map(IJavaClasspathRoot::getAnalyzedClasses)
			.flatMap(Collection::stream)
			.filter(new JavaCodeValidatorClassPredicate())
			.collect(Collectors.toList());
	}

	private boolean isRelevantRoot(IJavaClasspathRoot root) {

		if (root.isFolder()) {
			return true;
		} else {
			return root.isJar() && root.getFile().getName().startsWith(JAR_NAME_PREFIX);
		}
	}
}

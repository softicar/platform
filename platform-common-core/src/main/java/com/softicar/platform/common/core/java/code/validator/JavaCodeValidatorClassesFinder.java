package com.softicar.platform.common.core.java.code.validator;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classpath.IJavaClasspathRoot;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import java.util.Collection;
import java.util.stream.Collectors;

public class JavaCodeValidatorClassesFinder {

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

		return root.isFolder() || root.isJar();
	}
}

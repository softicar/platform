package com.softicar.platform.common.core.java.classes.analyzer;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.classpath.IJavaClasspathRoot;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AnalyzedJavaClassFetcher {

	private Predicate<AnalyzedJavaClass> predicate;

	public AnalyzedJavaClassFetcher() {

		this.predicate = dummy -> true;
	}

	public AnalyzedJavaClassFetcher setFilter(Predicate<AnalyzedJavaClass> predicate) {

		this.predicate = Objects.requireNonNull(predicate);
		return this;
	}

	public Collection<AnalyzedJavaClass> fetchJavaClasses(IJavaClasspathRoot classpathRoot) {

		return classpathRoot//
			.getAnalyzedClasses()
			.stream()
			.filter(predicate)
			.collect(Collectors.toList());
	}

	public Optional<AnalyzedJavaClass> fetchJavaClass(IJavaClasspathRoot classpathRoot, JavaClassName className) {

		return classpathRoot//
			.getAnalyzedClass(className)
			.filter(predicate);
	}
}

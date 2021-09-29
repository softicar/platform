package com.softicar.platform.common.core.java.classes.analyzer;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class AnalyzedJavaClassHierarchy {

	private final IAnalyzedJavaClassProvider cache;

	public AnalyzedJavaClassHierarchy(IAnalyzedJavaClassProvider cache) {

		this.cache = cache;
	}

	public boolean isInstance(AnalyzedJavaClass javaClass, JavaClassName superClassName) {

		Objects.requireNonNull(javaClass);
		Objects.requireNonNull(superClassName);
		if (fetch(javaClass)) {
			return isSameClass(javaClass, superClassName) || isSubClass(javaClass, superClassName);
		} else {
			return false;
		}
	}

	private boolean fetch(AnalyzedJavaClass javaClass) {

		return cache.getAnalyzedClass(javaClass.getClassName()).isPresent();
	}

	private boolean isSameClass(AnalyzedJavaClass javaClass, JavaClassName className) {

		return javaClass.getClassName().equals(className);
	}

	private boolean isSubClass(AnalyzedJavaClass javaClass, JavaClassName superClassName) {

		return determineTransitiveSuperClassNames(javaClass).contains(superClassName);
	}

	private Set<JavaClassName> determineTransitiveSuperClassNames(AnalyzedJavaClass javaClass) {

		return recursivelyDetermineSuperTypeClassNames(javaClass.getClassName(), new TreeSet<>());
	}

	private Set<JavaClassName> recursivelyDetermineSuperTypeClassNames(JavaClassName className, Set<JavaClassName> knownClassNames) {

		Set<JavaClassName> superTypeClassNames = getSuperTypeClassNames(className);
		superTypeClassNames.removeAll(knownClassNames);
		if (!superTypeClassNames.isEmpty()) {
			knownClassNames.addAll(superTypeClassNames);
			for (JavaClassName superTypeClassName: superTypeClassNames) {
				recursivelyDetermineSuperTypeClassNames(superTypeClassName, knownClassNames);
			}
		}
		return knownClassNames;
	}

	private Set<JavaClassName> getSuperTypeClassNames(JavaClassName className) {

		return cache//
			.getAnalyzedClass(className)
			.map(this::getSuperTypeClassNames)
			.orElse(new TreeSet<>());
	}

	private Set<JavaClassName> getSuperTypeClassNames(AnalyzedJavaClass javaClass) {

		Set<JavaClassName> classNames = new TreeSet<>();
		classNames.add(javaClass.getSuperClass());
		classNames.addAll(javaClass.getInterfaces());
		return classNames;
	}
}

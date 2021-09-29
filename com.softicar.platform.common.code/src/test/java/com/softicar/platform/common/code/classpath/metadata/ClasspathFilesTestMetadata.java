package com.softicar.platform.common.code.classpath.metadata;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClasspathFilesTestMetadata implements IClasspathFilesMetadata {

	private final Map<Class<?>, Set<Class<?>>> implementingClasses;
	private final Map<Class<?>, Set<Class<?>>> extendingClasses;

	public ClasspathFilesTestMetadata() {

		this.implementingClasses = new HashMap<>();
		this.extendingClasses = new HashMap<>();
	}

	@Override
	public Collection<Class<?>> getDirectlyImplementingClasses(Class<?> interfaceClass) {

		return implementingClasses.getOrDefault(interfaceClass, Collections.emptySet());
	}

	@Override
	public Collection<Class<?>> getDirectlyExtendingClasses(Class<?> baseClass) {

		return extendingClasses.getOrDefault(baseClass, Collections.emptySet());
	}

	@Override
	public Collection<Class<?>> getDirectlyAnnotatedClasses(Class<? extends Annotation> annotation) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Class<?>> getTransitivelyImplementingClasses(Class<?> interfaceClass) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Class<?>> getTransitivelyExtendingClasses(Class<?> baseClass) {

		throw new UnsupportedOperationException();
	}

	public void addImplementingClass(Class<?> implementingClass, Class<?> interfaceClass) {

		implementingClasses.computeIfAbsent(interfaceClass, dummy -> new HashSet<>()).add(implementingClass);
	}

	public void addExtendingClass(Class<?> extendingClass, Class<?> baseClass) {

		extendingClasses.computeIfAbsent(baseClass, dummy -> new HashSet<>()).add(extendingClass);
	}
}

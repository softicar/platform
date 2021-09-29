package com.softicar.platform.common.code.java.reflection;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DeclaredFieldFinder {

	private final Class<?> declaringClass;
	private Predicate<Field> predicate;

	public DeclaredFieldFinder(Class<?> declaringClass) {

		this.declaringClass = Objects.requireNonNull(declaringClass);
		this.predicate = dummy -> true;
	}

	public DeclaredFieldFinder setFilter(Predicate<Field> predicate) {

		this.predicate = Objects.requireNonNull(predicate);
		return this;
	}

	public Collection<Field> findDeclaredFields() {

		return Arrays//
			.asList(declaringClass.getDeclaredFields())
			.stream()
			.filter(predicate)
			.collect(Collectors.toList());
	}
}

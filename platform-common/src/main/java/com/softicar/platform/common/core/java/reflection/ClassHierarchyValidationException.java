package com.softicar.platform.common.core.java.reflection;

public class ClassHierarchyValidationException extends RuntimeException {

	private final Class<?> classUnderTest;

	public ClassHierarchyValidationException(Class<?> classUnderTest, String message, Object...args) {

		super(String.format(message, args));

		this.classUnderTest = classUnderTest;
	}

	public Class<?> getClassUnderTest() {

		return classUnderTest;
	}
}

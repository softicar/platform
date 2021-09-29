package com.softicar.platform.common.core.java.code.validator;

import com.softicar.platform.common.core.java.code.validation.output.IJavaCodeValidationOuput;
import com.softicar.platform.common.core.java.reflection.ClassHierarchyUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Provides assert methods to test properties of a given Java {@link Class}.
 *
 * @author Oliver Richers
 */
public class JavaClassValidator {

	private final IJavaCodeValidationOuput output;
	private final Class<?> javaClass;

	public JavaClassValidator(IJavaCodeValidationOuput output, Class<?> javaClass) {

		this.output = output;
		this.javaClass = javaClass;
	}

	public JavaClassValidator assertIsNotAbstract() {

		if (Modifier.isAbstract(javaClass.getModifiers())) {
			output
				.formatViolation(//
					"Class must not be abstract: %s",
					javaClass.getCanonicalName());
		}
		return this;
	}

	public JavaClassValidator assertIsNotInterface() {

		if (javaClass.isInterface()) {
			output
				.formatViolation(//
					"Class must not be interface: %s",
					javaClass.getCanonicalName());
		}
		return this;
	}

	public JavaClassValidator assertImplementsInterface(Class<?> expectedInterface) {

		if (!expectedInterface.isAssignableFrom(javaClass)) {
			output
				.formatViolation(//
					"Class must implement interface '%s': %s",
					expectedInterface.getSimpleName(),
					javaClass.getCanonicalName());
		}
		return this;
	}

	public JavaClassValidator assertHasAnnotation(Class<?> expectedAnnotation) {

		if (Stream.of(javaClass.getAnnotations()).noneMatch(it -> it.annotationType().equals(expectedAnnotation))) {
			output
				.formatViolation(//
					"Class must have annotation '%s': %s",
					expectedAnnotation.getSimpleName(),
					javaClass.getCanonicalName());
		}
		return this;
	}

	public JavaClassValidator assertHasParameterlessConstructor() {

		try {
			javaClass.getConstructor();
		} catch (@SuppressWarnings("unused") NoSuchMethodException exception) {
			output
				.formatViolation(//
					"Missing parameterless constructor in class: %s",
					javaClass.getCanonicalName());
		}
		return this;
	}

	public JavaClassValidator assertHasNoNonStaticFieldsShallow() {

		if (hasNonStaticFields()) {
			output
				.formatViolation(//
					"Unexpected non-static field in class: %s",
					javaClass.getCanonicalName());
		}
		return this;
	}

	public JavaClassValidator assertHasNoNonStaticFieldsDeep() {

		ClassHierarchyUtils.forEachClassInHierarchy(javaClass, it -> new JavaClassValidator(output, it).assertHasNoNonStaticFieldsShallow());
		return this;
	}

	// ------------------------------ private auxiliary methods ------------------------------ //

	private boolean hasNonStaticFields() {

		return Arrays//
			.asList(javaClass.getDeclaredFields())
			.stream()
			.anyMatch(this::isNonStaticField);
	}

	private boolean isNonStaticField(Field field) {

		return !Modifier.isStatic(field.getModifiers());
	}
}

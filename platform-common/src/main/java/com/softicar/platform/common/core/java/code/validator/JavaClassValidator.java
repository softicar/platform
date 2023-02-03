package com.softicar.platform.common.core.java.code.validator;

import com.softicar.platform.common.core.java.code.validation.output.IJavaCodeValidationOuput;
import com.softicar.platform.common.core.java.reflection.ClassHierarchyUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;
import javax.xml.stream.events.Characters;

/**
 * Provides assert methods to test properties of a given Java {@link Class}.
 *
 * @author Oliver Richers
 */
public class JavaClassValidator {

	private static final Set<Class<?>> IMMUTABLE_TYPES = Set
		.of(//
			Boolean.class,
			Byte.class,
			Characters.class,
			Double.class,
			Float.class,
			Integer.class,
			Long.class,
			Short.class,
			String.class);
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

	public JavaClassValidator assertHasNoMutableInstanceFieldsShallow() {

		if (hasMutableInstanceFields()) {
			output
				.formatViolation(//
					"Unexpected mutable instance field in class: %s",
					javaClass.getCanonicalName());
		}
		return this;
	}

	public JavaClassValidator assertHasNoMutableInstanceFieldsDeep() {

		ClassHierarchyUtils.forEachClassInHierarchy(javaClass, it -> new JavaClassValidator(output, it).assertHasNoMutableInstanceFieldsShallow());
		return this;
	}

	// ------------------------------ private auxiliary methods ------------------------------ //

	private boolean hasMutableInstanceFields() {

		return Arrays//
			.asList(javaClass.getDeclaredFields())
			.stream()
			.anyMatch(this::isMutableInstanceField);
	}

	private boolean isMutableInstanceField(Field field) {

		return !isImmutable(field) && !isStatic(field);
	}

	private boolean isStatic(Field field) {

		return Modifier.isStatic(field.getModifiers());
	}

	private boolean isImmutable(Field field) {

		return isFinal(field) && hasImmutableType(field);
	}

	private boolean isFinal(Field field) {

		return Modifier.isFinal(field.getModifiers());
	}

	private boolean hasImmutableType(Field field) {

		return field.getType().isPrimitive() || IMMUTABLE_TYPES.contains(field.getType());
	}
}

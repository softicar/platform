package com.softicar.platform.common.core.java.reflection;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Utility methods to analyze the hierarchy of a class or interface.
 *
 * @author Oliver Richers
 */
public class ClassHierarchyUtils {

	/**
	 * Returns all interfaces that the given {@link Class} implements.
	 *
	 * @param javaClass
	 *            the class to analyze (never <i>null</i>)
	 * @return a {@link Set} of all implemented interfaces
	 */
	public static Set<Class<?>> getAllInterfaces(Class<?> javaClass) {

		Objects.requireNonNull(javaClass);

		Set<Class<?>> interfaces = new HashSet<>();
		forEachClassInHierarchy(javaClass, it -> gatherInterfaces(it, interfaces));
		return interfaces;
	}

	private static void gatherInterfaces(Class<?> theClass, Set<Class<?>> interfaces) {

		for (Class<?> theInterface: theClass.getInterfaces()) {
			interfaces.add(theInterface);
			gatherInterfaces(theInterface, interfaces);
		}
	}

	/**
	 * Executes the given {@link Consumer} for the given {@link Class} and all
	 * its super classes.
	 *
	 * @param javaClass
	 *            the class (never <i>null</i>)
	 * @param consumer
	 *            the consumer to execute (never <i>null</i>)
	 */
	public static void forEachClassInHierarchy(Class<?> javaClass, Consumer<Class<?>> consumer) {

		Objects.requireNonNull(javaClass);
		Objects.requireNonNull(consumer);

		consumer.accept(javaClass);

		Optional//
			.ofNullable(javaClass.getSuperclass())
			.ifPresent(superClass -> forEachClassInHierarchy(superClass, consumer));
	}
}

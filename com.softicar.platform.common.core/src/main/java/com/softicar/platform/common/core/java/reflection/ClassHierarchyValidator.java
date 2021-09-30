package com.softicar.platform.common.core.java.reflection;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class to validate the inheritance hierarchy of a class or interface.
 *
 * @author Oliver Richers
 */
public class ClassHierarchyValidator {

	private final Class<?> classUnderTest;

	public ClassHierarchyValidator(Class<?> classUnderTest) {

		this.classUnderTest = classUnderTest;
	}

	/**
	 * Checks that the class does not implement or extend any interface.
	 *
	 * @return this validator for chaining
	 * @throws ClassHierarchyValidationException
	 *             if the class implements or extends an interface
	 */
	public ClassHierarchyValidator assertNoInterfaces() {

		Set<Class<?>> interfaces = ClassHierarchyUtils.getAllInterfaces(classUnderTest);
		if (interfaces.size() > 0) {
			String message = classUnderTest.isInterface()? //
					"Expected interface %s not to extend any other interface, but extends:\n%s"
					: "Expected class %s not to implement any interface, but implements:\n%s";
			throw new ClassHierarchyValidationException(
				classUnderTest,
				message,
				classUnderTest.getCanonicalName(),
				interfaces.stream().map(i -> i.getCanonicalName()).sorted().collect(Collectors.joining("\n")));
		}
		return this;
	}

	/**
	 * Checks that the class does not extend any other class,
	 * except @link{Object}.
	 *
	 * @return this validator for chaining
	 * @throws ClassHierarchyValidationException
	 *             if the class extends another class
	 */
	public ClassHierarchyValidator assertNoSuperClass() {

		Class<?> superClass = classUnderTest.getSuperclass();
		if (superClass != null && superClass != Object.class) {
			throw new ClassHierarchyValidationException(
				classUnderTest,
				"Expected class %s not to extend another class, but extends %s.",
				classUnderTest.getCanonicalName(),
				superClass.getCanonicalName());
		}
		return this;
	}

	/**
	 * Asserts that the class does only implement the given interface.
	 *
	 * @param interfaceClass
	 *            the interface to implement or extend
	 * @return this validator for chaining
	 * @throws ClassHierarchyValidationException
	 *             if the class implements another interface or implements no
	 *             interface at all
	 */
	public ClassHierarchyValidator assertOnlyOneInterface(Class<?> interfaceClass) {

		Set<Class<?>> interfaces = ClassHierarchyUtils.getAllInterfaces(classUnderTest);
		if (interfaces.size() > 1) {
			throw new ClassHierarchyValidationException(
				classUnderTest,
				"Class %s implements too many interfaces. Expected to implement only %s. Implemented interfaces:\n%s",
				classUnderTest.getCanonicalName(),
				interfaceClass.getSimpleName(),
				interfaces.stream().map(i -> i.getCanonicalName()).sorted().collect(Collectors.joining("\n")));
		}
		return this;
	}
}

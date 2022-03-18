package com.softicar.platform.common.code.classpath.metadata;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class determines all classes that directly or transitively implement a
 * given interface or set of interfaces.
 *
 * @author Oliver Richers
 */
class TransitivelyImplementingClassesFinder {

	private final IClasspathFilesMetadata metadata;

	public TransitivelyImplementingClassesFinder(IClasspathFilesMetadata metadata) {

		this.metadata = metadata;
	}

	/**
	 * Determines and returns all classes that directly or transitively
	 * implement the given interface.
	 * <p>
	 * The returned classes also contain all interfaces extending the given
	 * interface. Please note that the JVM and the Java class file format does
	 * not distinguish between interfaces and classes, that is, an interface is
	 * considered only a special kind of class.
	 *
	 * @param interfaceClass
	 *            the interface (never null)
	 * @return all implementing classes, excluding the given interface (never
	 *         null)
	 */
	public Set<Class<?>> findAll(Class<?> interfaceClass) {

		return findAll(Collections.singleton(interfaceClass));
	}

	/**
	 * Determines and returns all classes that directly or transitively
	 * implement the given interfaces.
	 * <p>
	 * The returned classes also contain all interfaces extending the given
	 * interface. Please note that the JVM and the Java class file format does
	 * not distinguish between interfaces and classes, that is, an interface is
	 * considered only a special kind of class.
	 *
	 * @param interfaces
	 *            the interfaces (never null)
	 * @return all implementing classes, excluding the given interfaces (never
	 *         null)
	 */
	public Set<Class<?>> findAll(Collection<Class<?>> interfaces) {

		Set<Class<?>> result = new HashSet<>();
		Set<Class<?>> implementingClasses = getImplementingClasses(interfaces);
		result.addAll(implementingClasses);
		result.addAll(getExtendingClasses(implementingClasses));
		result.removeAll(interfaces);
		return result;
	}

	private Set<Class<?>> getImplementingClasses(Collection<Class<?>> interfaces) {

		return new TransitivelyRelatedClassesFinder(metadata::getDirectlyImplementingClasses).findAll(interfaces);
	}

	private Set<Class<?>> getExtendingClasses(Set<Class<?>> baseClasses) {

		return new TransitivelyRelatedClassesFinder(metadata::getDirectlyExtendingClasses).findAll(baseClasses);
	}
}

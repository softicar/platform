package com.softicar.platform.common.code.classpath.metadata;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * This class determines all classes that directly or transitively extend a
 * given class or set of classes.
 *
 * @author Oliver Richers
 */
class TransitivelyExtendingClassesFinder {

	private final IClasspathFilesMetadata metadata;

	public TransitivelyExtendingClassesFinder(IClasspathFilesMetadata metadata) {

		this.metadata = metadata;
	}

	/**
	 * Determines and returns all classes that directly or transitively
	 * extending the given base class.
	 *
	 * @param baseClass
	 *            the base class (never null)
	 * @return all extending classes, excluding the given base class (never
	 *         null)
	 */
	public Set<Class<?>> findAll(Class<?> baseClass) {

		return findAll(Collections.singleton(baseClass));
	}

	/**
	 * Determines and returns all classes that directly or transitively
	 * extending the given base classes.
	 *
	 * @param baseClasses
	 *            a collection of base classes (never null)
	 * @return all extending classes, excluding the given base classes (never
	 *         null)
	 */
	public Set<Class<?>> findAll(Collection<Class<?>> baseClasses) {

		return new TransitivelyRelatedClassesFinder(metadata::getDirectlyExtendingClasses).findAll(baseClasses);
	}
}

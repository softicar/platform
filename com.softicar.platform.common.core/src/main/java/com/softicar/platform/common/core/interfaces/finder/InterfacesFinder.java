package com.softicar.platform.common.core.interfaces.finder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Searches the class inheritance tree to find all interfaces that a given class
 * implements.
 *
 * @author Oliver Richers
 */
public class InterfacesFinder {

	private final Class<?> someClass;
	private final Set<Class<?>> interfaces;

	public InterfacesFinder(Class<?> someClass) {

		this.someClass = someClass;
		this.interfaces = new HashSet<>();
	}

	public Collection<Class<?>> findAll() {

		gatherAll(someClass);
		return interfaces;
	}

	public void gatherAll(Class<?> currentClass) {

		while (currentClass != null) {
			for (Class<?> someInterface: currentClass.getInterfaces()) {
				if (interfaces.add(someInterface)) {
					gatherAll(someInterface);
				}
			}
			currentClass = currentClass.getSuperclass();
		}
	}
}

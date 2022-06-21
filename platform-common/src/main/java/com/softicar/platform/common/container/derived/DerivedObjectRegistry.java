package com.softicar.platform.common.container.derived;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

/**
 * Manages all dependencies between {@link DerivedObject} instances and their
 * source objects.
 * <p>
 * All {@link DerivedObject} instances are referenced via weak references, thus,
 * this registry will not prevent them from being collected. This is also true
 * for the registered source objects.
 *
 * @author Oliver Richers
 */
public class DerivedObjectRegistry {

	private final Map<Object, Map<DerivedObject<?>, Void>> dependencies;

	public DerivedObjectRegistry() {

		this.dependencies = new WeakHashMap<>();
	}

	/**
	 * Calls {@link DerivedObject#invalidate} on all {@link DerivedObject}
	 * instances that were registered for the given source {@link Object}.
	 *
	 * @param sourceObject
	 *            the source {@link Object} (never <i>null</i>)
	 */
	public void invalidateDerivedObjects(Object sourceObject) {

		Objects.requireNonNull(sourceObject);

		dependencies//
			.getOrDefault(sourceObject, Collections.emptyMap())
			.keySet()
			.forEach(DerivedObject::invalidate);
	}

	/**
	 * Registers the given {@link DerivedObject} to be invalidated whenever
	 * {@link #invalidateDerivedObjects} is called for the given source
	 * {@link Object}.
	 *
	 * @param sourceObject
	 *            the source {@link Object} (never <i>null</i>)
	 * @param derivedObject
	 *            the {@link DerivedObject} (never <i>null</i>)
	 */
	void addDependency(Object sourceObject, DerivedObject<?> derivedObject) {

		Objects.requireNonNull(sourceObject);
		Objects.requireNonNull(derivedObject);

		dependencies//
			.computeIfAbsent(sourceObject, dummy -> new WeakHashMap<>())
			.put(derivedObject, null);
	}
}

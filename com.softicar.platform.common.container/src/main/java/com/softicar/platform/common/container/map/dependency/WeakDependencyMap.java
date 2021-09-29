package com.softicar.platform.common.container.map.dependency;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * A dependency which holds all objects using {@link WeakReference}.
 *
 * @author Oliver Richers
 */
public class WeakDependencyMap<T> {

	private final Map<T, Set<T>> dependers;

	public WeakDependencyMap() {

		this.dependers = new WeakHashMap<>();
	}

	/**
	 * Defines a dependency, between the dependee and the depender.
	 *
	 * @param dependee
	 *            the object on which the depender depends
	 * @param depender
	 *            the object that depends on the depender
	 */
	public void addDependency(T dependee, T depender) {

		dependers//
			.computeIfAbsent(dependee, dummy -> createSet())
			.add(depender);
	}

	/**
	 * Returns the transitive closure for the given seed objects.
	 * <p>
	 * The returned collection contains at least the seed objects. Additionally,
	 * all objects that transitively depend on the seed objects will also be
	 * returned.
	 *
	 * @param seeds
	 *            the seed objects
	 * @return a set of all transitively dependent objects, including the seed
	 *         objects
	 */
	public Collection<T> getTransitiveClosure(Collection<T> seeds) {

		return new ClosureComputer().addAll(seeds).getClosure();
	}

	private Set<T> createSet() {

		return Collections.newSetFromMap(new WeakHashMap<>());
	}

	private class ClosureComputer {

		private final Set<T> closure;

		public ClosureComputer() {

			this.closure = new HashSet<>();
		}

		public ClosureComputer add(T dependee) {

			if (closure.add(dependee)) {
				dependers//
					.getOrDefault(dependee, Collections.emptySet())
					.forEach(this::add);
			}
			return this;
		}

		public ClosureComputer addAll(Collection<T> dependees) {

			dependees.forEach(this::add);
			return this;
		}

		public Set<T> getClosure() {

			return closure;
		}
	}
}

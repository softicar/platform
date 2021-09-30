package com.softicar.platform.common.container.set;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Auxiliary methods for {@link Set} instances.
 *
 * @author Oliver Richers
 */
public class Sets {

	/**
	 * Same as {@link #difference(Set, Set, Function)} using {@link HashSet}.
	 */
	public static <T> Set<T> difference(Set<T> a, Set<T> b) {

		return difference(a, b, HashSet::new);
	}

	/**
	 * Computes the difference between two {@link Set} instances.
	 *
	 * @param a
	 *            the first {@link Set} (never <i>null</i>)
	 * @param b
	 *            the {@link Set} to substract from the first {@link Set} (never
	 *            <i>null</i>)
	 * @param factory
	 *            the factory for the returned {@link Set} (never <i>null</i>)
	 * @return a new {@link Set} with all elements from the first {@link Set}
	 *         that are not contained in the second {@link Set} (never
	 *         <i>null</i>)
	 */
	public static <T> Set<T> difference(Set<T> a, Set<T> b, Function<Collection<T>, Set<T>> factory) {

		Set<T> difference = factory.apply(a);
		difference.removeAll(b);
		return difference;
	}
}

package com.softicar.platform.common.container.pair;

/**
 * Provides static factory methods for {@link ComparablePair}.
 * 
 * @author Oliver Richers
 */
public abstract class ComparablePairFactory {

	/**
	 * Creates a new {@link ComparablePair} with the specified elements.
	 */
	public static <A extends Comparable<? super A>, B extends Comparable<? super B>> ComparablePair<A, B> create(A first, B second) {

		return new ComparablePair<>(first, second);
	}
}

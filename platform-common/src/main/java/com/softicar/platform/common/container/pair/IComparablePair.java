package com.softicar.platform.common.container.pair;

/**
 * An extension of {@link IPair} with {@link Comparable}.
 * <p>
 * In contrast to {@link IPair}, this interface requires the elements to be
 * comparable.
 * 
 * @author Oliver Richers
 */
public interface IComparablePair<A extends Comparable<? super A>, B extends Comparable<? super B>> extends IPair<A, B>, Comparable<IComparablePair<A, B>> {

	/**
	 * Compares this pair to the other pair, lexicographically.
	 */
	@Override
	int compareTo(IComparablePair<A, B> other);
}

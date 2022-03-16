package com.softicar.platform.common.container.pair;

/**
 * The default implementation of {@link IComparablePair}.
 * 
 * @author Oliver Richers
 */
public class ComparablePair<A extends Comparable<? super A>, B extends Comparable<? super B>> extends Pair<A, B> implements IComparablePair<A, B> {

	public ComparablePair(A first, B second) {

		super(first, second);
	}

	@Override
	public int compareTo(IComparablePair<A, B> other) {

		int cmp = getFirst().compareTo(other.getFirst());
		return cmp != 0? cmp : getSecond().compareTo(other.getSecond());
	}
}

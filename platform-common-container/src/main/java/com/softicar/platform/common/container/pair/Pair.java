package com.softicar.platform.common.container.pair;

import com.softicar.platform.common.core.utils.CompareUtils;
import java.util.Objects;

/**
 * The default implementation of {@link IPair}.
 *
 * @author Oliver Richers
 */
public class Pair<A, B> implements IPair<A, B> {

	private A first;
	private B second;

	public Pair(A first, B second) {

		this.first = first;
		this.second = second;
	}

	@Override
	public A getFirst() {

		return first;
	}

	@Override
	public B getSecond() {

		return second;
	}

	@Override
	public void setFirst(A first) {

		this.first = first;
	}

	@Override
	public void setSecond(B second) {

		this.second = second;
	}

	/**
	 * Converts both elements to string and concatenates them, separated by a
	 * comma and enclosed by brackets.
	 * <p>
	 * As always, do not assume the returned format to be stable. Only use the
	 * output of this method for logging.
	 */
	@Override
	public String toString() {

		return String.format("[%s, %s]", "" + first, "" + second);
	}

	/**
	 * Returns true of the first elements in both pairs and the second elements
	 * in both pairs are equal.
	 * <p>
	 * This also supports null values in the pairs, by treating null and null to
	 * be equal.
	 */
	@Override
	public boolean equals(Object other) {

		if (other instanceof Pair<?, ?>) {
			Pair<?, ?> otherPair = (Pair<?, ?>) other;
			return CompareUtils.isEqual(first, otherPair.first) && CompareUtils.isEqual(second, otherPair.second);
		} else {
			return false;
		}
	}

	/**
	 * Returns a combination of the hash codes of both elements.
	 * <p>
	 * This also supports null values.
	 */
	@Override
	public int hashCode() {

		return Objects.hash(first, second);
	}
}

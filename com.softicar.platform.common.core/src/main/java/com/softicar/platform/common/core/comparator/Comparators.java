package com.softicar.platform.common.core.comparator;

import java.util.Comparator;

/**
 * Utility methods for {@link Comparator}.
 *
 * @author Oliver Richers
 */
public class Comparators {

	/**
	 * Returns a null-safe natural order {@link Comparator}.
	 * <p>
	 * This is the same as
	 *
	 * <pre>
	 * {@code Comparator.thenComparing(Comparator.naturalOrder())}.
	 * </pre>
	 *
	 * @param <T>
	 * @return the respective {@link Comparator}
	 */
	public static <T extends Comparable<T>> Comparator<T> naturalOrderNullsFirst() {

		return Comparator.nullsFirst(Comparator.naturalOrder());
	}
}

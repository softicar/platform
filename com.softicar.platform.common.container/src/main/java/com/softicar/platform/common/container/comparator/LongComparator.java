package com.softicar.platform.common.container.comparator;

import java.util.Comparator;

/**
 * A special comparator for longs.
 *
 * @author Oliver Richers
 */
public class LongComparator implements Comparator<Long> {

	private static LongComparator singleton = new LongComparator();

	/**
	 * Returns the singleton instance of this comparator.
	 *
	 * @return comparator singleton
	 */
	public static LongComparator get() {

		return singleton;
	}

	/**
	 * Compares the given longs and produces the default order, which is
	 * ascending.
	 *
	 * @return compareAscending(left, right)
	 */
	@Override
	public int compare(Long left, Long right) {

		return compareAscending(left, right);
	}

	/**
	 * Compares the given longs and produces an ascending order, that is, a
	 * negative value if {@code left < right}, a positive value if
	 * {@code left > right}, and zero otherwise.
	 *
	 * @return left minus right
	 */
	public static int compareAscending(long left, long right) {

		if (left < right) {
			return -1;
		} else if (left > right) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Compares the given longs and produces a descending order, that is, a
	 * positive value if {@code left < right}, a negative value if
	 * {@code left > right}, and zero otherwise.
	 *
	 * @return right minus left
	 */
	public static int compareDescending(long left, long right) {

		return -compareAscending(left, right);
	}
}

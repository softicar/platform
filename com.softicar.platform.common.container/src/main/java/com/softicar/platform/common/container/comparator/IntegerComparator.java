package com.softicar.platform.common.container.comparator;

import java.util.Comparator;

/**
 * A special comparator for integers.
 * <p>
 * Integers can easily be compared using subtraction, but some developers have
 * problems to quickly determine how to produce an ascending order and how to
 * produce a descending order.
 *
 * @author Oliver Richers
 */
public class IntegerComparator implements Comparator<Integer> {

	private static IntegerComparator singleton = new IntegerComparator();

	/**
	 * Returns the singleton instance of this comparator.
	 *
	 * @return comparator singleton
	 */
	public static IntegerComparator get() {

		return singleton;
	}

	/**
	 * Compares the given integers and produces the default order, which is
	 * ascending.
	 *
	 * @return compareAscending(left, right)
	 */
	@Override
	public int compare(Integer left, Integer right) {

		return compareAscending(left, right);
	}

	/**
	 * Compares the given integers and produces an ascending order, that is, a
	 * negative value if {@code left < right}, a positive value if
	 * {@code left > right}, and zero otherwise.
	 *
	 * @return left minus right
	 */
	public static int compareAscending(int left, int right) {

		return left - right;
	}

	/**
	 * Compares the given integers and produces a descending order, that is, a
	 * positive value if {@code left < right}, a negative value if
	 * {@code left > right}, and zero otherwise.
	 *
	 * @return right minus left
	 */
	public static int compareDescending(int left, int right) {

		return -compareAscending(left, right);
	}
}

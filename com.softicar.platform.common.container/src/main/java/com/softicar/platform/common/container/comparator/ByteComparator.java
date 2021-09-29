package com.softicar.platform.common.container.comparator;

import java.util.Comparator;

/**
 * Compares primitive bytes.
 * <p>
 * All integral types can easily be compared using subtraction, but some
 * developers have problems to quickly determine how to produce an ascending or
 * descending order. By default an ascending order is used.
 * 
 * @author Oliver Richers
 */
public class ByteComparator implements Comparator<Byte> {

	private static ByteComparator singleton = new ByteComparator();

	/**
	 * Returns the singleton instance of this comparator.
	 * 
	 * @return comparator singleton
	 */
	public static ByteComparator get() {

		return singleton;
	}

	/**
	 * Compares the bytes using the {@link #compareBytesAscending(byte, byte)}
	 * method.
	 */
	@Override
	public int compare(Byte left, Byte right) {

		return compareBytesAscending(left, right);
	}

	/**
	 * Compares the given bytes to produce an ascending order.
	 * 
	 * @param left
	 *            the first byte
	 * @param right
	 *            the second byte
	 * @return negative if left is less than right, positive if right is less
	 *         than left, zero if left equals right
	 */
	public static int compareBytesAscending(byte left, byte right) {

		return left - right;
	}

	/**
	 * Compares the given bytes to produce a descending order.
	 * 
	 * @param left
	 *            the first byte
	 * @param right
	 *            the second byte
	 * @return positive if left is less than right, negative if right is less
	 *         than left, zero if left equals right
	 */
	public static int compareBytesDescending(byte left, byte right) {

		return right - left;
	}
}

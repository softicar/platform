package com.softicar.platform.common.container.comparator;

import java.util.Comparator;

/**
 * This is a simple comparator for byte arrays.
 * <p>
 * The given arrays are compared lexicographically to produce an ascending
 * order.
 * 
 * @author Oliver Richers
 */
public class ByteArrayComparator implements Comparator<byte[]> {

	private static ByteArrayComparator singleton = new ByteArrayComparator();

	/**
	 * Returns the singleton instance of this comparator.
	 * 
	 * @return comparator singleton
	 */
	public static ByteArrayComparator get() {

		return singleton;
	}

	/**
	 * Compares the byte arrays using the
	 * {@link #compareArraysAscending(byte[], byte[])} method.
	 */
	@Override
	public int compare(byte[] left, byte[] right) {

		return compareArraysAscending(left, right);
	}

	/**
	 * Compares the given byte arrays to produce an ascending order.
	 * <p>
	 * The arrays are compared lexicographically.
	 * 
	 * @param left
	 *            the first byte
	 * @param right
	 *            the second byte
	 * @return negative if left is less than right, positive if right is less
	 *         than left, zero if left equals right
	 */
	public static int compareArraysAscending(byte[] left, byte[] right) {

		int n = Math.min(left.length, right.length);

		for (int i = 0; i < n; ++i) {

			int cmp = ByteComparator.compareBytesAscending(left[i], right[i]);
			if (cmp != 0) {
				return cmp;
			}
		}

		return left.length - right.length;
	}
}

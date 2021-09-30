package com.softicar.platform.common.container.array;

/**
 * Provides static utility method to copy arrays.
 * 
 * @author Oliver Richers
 */
public class ArrayCopying {

	/**
	 * Creates an exact copy of the given array.
	 * <p>
	 * If the given array reference is null, this method will also return null.
	 * 
	 * @param source
	 *            the array to copy
	 * @return new array with the same content as the source array, or null if
	 *         the source array is null
	 */
	public static byte[] copyArray(byte[] source) {

		if (source != null) {
			int n = source.length;
			byte[] result = new byte[n];
			for (int i = 0; i < n; ++i) {
				result[i] = source[i];
			}
			return result;
		} else {
			return null;
		}
	}

	/**
	 * Copies all bytes from the source array into the target array.
	 * <p>
	 * If the target array is bigger than the source array, the remaining bytes
	 * at the and of the target array will be left untouched.
	 * 
	 * @param source
	 *            the source array
	 * @param target
	 *            the target array
	 */
	public static void copyArray(byte[] source, byte[] target) {

		copyArray(source, target, source.length);
	}

	/**
	 * Copies the given amount of bytes from the source array into the target
	 * array.
	 * 
	 * @param source
	 *            the source array
	 * @param target
	 *            the target array
	 * @param n
	 *            the number of bytes to copy
	 * @throws IndexOutOfBoundsException
	 *             if the size of the source or target array is less than n
	 */
	public static void copyArray(byte[] source, byte[] target, int n) {

		copy(0, source, 0, target, n);
	}

	/**
	 * Copies bytes from the source array into the target array.
	 * 
	 * @param s
	 *            the start index into the source array
	 * @param source
	 *            the source array
	 * @param t
	 *            the start index into the target array
	 * @param target
	 *            the target array
	 * @param n
	 *            the number of bytes to copy
	 * @throws IndexOutOfBoundsException
	 *             if there are not enough elements in the source or target
	 *             array
	 */
	public static void copy(int s, byte[] source, int t, byte[] target, int n) {

		for (int k = 0; k < n; ++k) {
			target[t + k] = source[s + k];
		}
	}
}

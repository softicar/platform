package com.softicar.platform.common.string.array;

import java.util.Objects;

/**
 * A {@link CharSequence} wrapper around an array of characters.
 *
 * @author Oliver Richers
 */
public class CharSequenceArray implements CharSequence {

	private final char[] array;
	private final int start;
	private final int end;

	/**
	 * Constructs this {@link CharSequenceArray} with a new character array of
	 * the given size.
	 *
	 * @param size
	 *            the size (must not be less than zero)
	 */
	public CharSequenceArray(int size) {

		this(new char[size]);
	}

	/**
	 * Constructs this {@link CharSequenceArray} with the given character array.
	 *
	 * @param array
	 *            the character array (never <i>null</i>)
	 */
	public CharSequenceArray(char[] array) {

		this(array, 0, array.length);
	}

	/**
	 * Constructs this {@link CharSequenceArray} as a sub-sequence of the given
	 * character array.
	 *
	 * @param array
	 *            the character array (never <i>null</i>)
	 * @param start
	 *            the start index, inclusive
	 * @param end
	 *            the end index, exclusive
	 */
	public CharSequenceArray(char[] array, int start, int end) {

		this.array = Objects.requireNonNull(array);
		this.start = start;
		this.end = end;

		if (start < 0 || start > array.length) {
			throw new IllegalArgumentException("Illegal start index.");
		}

		if (end < start || end > array.length) {
			throw new IllegalArgumentException("Illegal end index.");
		}
	}

	/**
	 * Returns the underlying character array.
	 *
	 * @return the character array (never <i>null</i>)
	 */
	public char[] getArray() {

		return array;
	}

	@Override
	public String toString() {

		return String.valueOf(array, start, end - start);
	}

	@Override
	public int length() {

		return end - start;
	}

	@Override
	public char charAt(int index) {

		return array[start + index];
	}

	@Override
	public CharSequence subSequence(int start, int end) {

		return new CharSequenceArray(array, this.start + start, this.start + end);
	}
}

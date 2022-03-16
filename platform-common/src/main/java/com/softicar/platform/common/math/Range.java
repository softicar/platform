package com.softicar.platform.common.math;

/**
 * A simple immutable range class.
 *
 * @author Oliver Richers
 */
public class Range<T extends Comparable<? super T>> {

	private T min;
	private T max;

	/**
	 * Creates an invalid range.
	 */
	public Range() {

		// nothing to do
	}

	/**
	 * Creates a singular range.
	 */
	public Range(T value) {

		this(value, value);
	}

	/**
	 * Create a range with the specified min and max value.
	 */
	public Range(T min, T max) {

		this.min = min;
		this.max = max;
	}

	public static <T extends Comparable<? super T>> Range<T> create(T min, T max) {

		return new Range<>(min, max);
	}

	/**
	 * Returns true if this is an valid range.
	 *
	 * @return whether this range is valid or not
	 */
	public boolean isValid() {

		return min != null;
	}

	/**
	 * Returns true if this range is valid and if {@link #getMin()} equals
	 * {@link #getMax()}.
	 *
	 * @return whether this is a valid singular range
	 */
	public boolean isSingular() {

		return isValid() && min.compareTo(max) == 0;
	}

	/**
	 * Returns true if this range is valid and not singular.
	 *
	 * @return whether this range is valid and not singular
	 */
	public boolean hasSize() {

		return isValid() && min.compareTo(max) < 0;
	}

	public T getMin() {

		return min;
	}

	public T getMax() {

		return max;
	}

	public boolean contains(T value) {

		return isValid() && value.compareTo(getMin()) >= 0 && value.compareTo(getMax()) <= 0;
	}

	public void add(T value) {

		if (isValid()) {
			if (value.compareTo(min) < 0) {
				min = value;
			} else if (value.compareTo(max) > 0) {
				max = value;
			}
		} else {
			min = max = value;
		}
	}

	@Override
	public String toString() {

		if (hasSize()) {
			return "(" + min.toString() + " --> " + max.toString() + ")";
		} else if (isSingular()) {
			return "(singular range: " + min.toString() + ")";
		} else {
			return "(invalid range)";
		}
	}
}

package com.softicar.platform.common.math.arithmetic;

import java.util.Comparator;
import java.util.Objects;

/**
 * Defines basic arithmetic properties for a specific type.
 *
 * @author Oliver Richers
 */
public interface IArithmetic<T> extends Comparator<T> {

	T getZero();

	T getOne();

	T plus(T left, T right);

	T minus(T left, T right);

	T times(T left, T right);

	T divided(T left, T right);

	T negate(T value);

	default boolean isZero(T value) {

		return Objects.equals(getZero(), value);
	}

	default boolean isNotZero(T value) {

		return !Objects.equals(getZero(), value);
	}

	default boolean isEqual(T left, T right) {

		return Objects.equals(left, right);
	}

	default boolean isNotEqual(T left, T right) {

		return !Objects.equals(left, right);
	}

	default boolean isLess(T left, T right) {

		return compare(left, right) < 0;
	}

	default boolean isGreater(T left, T right) {

		return compare(left, right) > 0;
	}

	default boolean isLessEqual(T left, T right) {

		return compare(left, right) <= 0;
	}

	default boolean isGreaterEqual(T left, T right) {

		return compare(left, right) >= 0;
	}
}

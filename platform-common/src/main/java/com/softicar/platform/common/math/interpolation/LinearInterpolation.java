package com.softicar.platform.common.math.interpolation;

import com.softicar.platform.common.math.Range;

/**
 * Provides methods to compute linear interpolations.
 *
 * @author Oliver Richers
 */
public class LinearInterpolation {

	/**
	 * Interpolates between the given values according to the specified ratio.
	 * 
	 * @param value1
	 *            first value
	 * @param value2
	 *            second value
	 * @param ratio
	 *            the weight of value1 compared to value2 in the range [0,1]
	 * @return linear interpolation of the specified values
	 */
	public static double interpolate(double value1, double value2, double ratio) {

		return value1 * (1 - ratio) + value2 * ratio;
	}

	/**
	 * Returns the position of value relative within the range [begin, end].
	 * 
	 * @param begin
	 *            the begin of the range
	 * @param end
	 *            the end of the range
	 * @param value
	 *            the value in the range [begin, end]
	 * @return a value in the range [0,1]
	 */
	public static double getRatio(double begin, double end, double value) {

		return (value - begin) / (end - begin);
	}

	public static double getRatio(Range<Double> range, double value) {

		return getRatio(range.getMin(), range.getMax(), value);
	}
}

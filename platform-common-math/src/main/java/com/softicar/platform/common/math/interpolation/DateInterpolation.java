package com.softicar.platform.common.math.interpolation;

import java.util.Date;

/**
 * Provides methods to interpolate values of type {@link Date}.
 *
 * @author Oliver Richers
 */
public class DateInterpolation {

	/**
	 * Interpolates between the given dates according to the specified ratio.
	 * 
	 * @param value1
	 *            first value
	 * @param value2
	 *            second value
	 * @param ratio
	 *            the weight of value1 compared to value2 in the range [0,1]
	 * @return linear interpolation of the specified values
	 */
	public static Date interpolate(Date value1, Date value2, double ratio) {

		double interpolated = LinearInterpolation.interpolate(value1.getTime(), value2.getTime(), ratio);
		return new Date(Math.round(interpolated));
	}

	public static double getRatio(Date begin, Date end, Date value) {

		return LinearInterpolation.getRatio(begin.getTime(), end.getTime(), value.getTime());
	}

}

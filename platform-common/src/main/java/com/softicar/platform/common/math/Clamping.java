package com.softicar.platform.common.math;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;

/**
 * Provides the methods to clamp values in a value range.
 * 
 * @author Oliver Richers
 */
public class Clamping {

	public static int clamp(int min, int max, int v) {

		if (max < min) {
			throw new SofticarDeveloperException("Invalid range [%d, %d]", min, max);
		}
		return Math.min(Math.max(min, v), max);
	}

	public static long clamp(long min, long max, long v) {

		if (max < min) {
			throw new SofticarDeveloperException("Invalid range [%d, %d]", min, max);
		}
		return Math.min(Math.max(min, v), max);
	}

	public static float clamp(float min, float max, float v) {

		if (max < min) {
			throw new SofticarDeveloperException("Invalid range [%g, %g]", min, max);
		}
		return Math.min(Math.max(min, v), max);
	}

	public static double clamp(double min, double max, double v) {

		if (max < min) {
			throw new SofticarDeveloperException("Invalid range [%g, %g]", min, max);
		}
		return Math.min(Math.max(min, v), max);
	}
}

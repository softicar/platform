package com.softicar.platform.core.module.user.password.quality;

import com.softicar.platform.common.math.Clamping;

/**
 * Represents the quality of a password.
 * <p>
 * The password quality is a floating-point value in the range [0, 1].
 *
 * @author Oliver Richers
 */
public class UserPasswordQuality {

	private final double value;

	/**
	 * Constructs a new quality object with the given value.
	 * <p>
	 * The value will automatically be clamped to the range [0, 1].
	 *
	 * @param value
	 *            the quality value
	 */
	public UserPasswordQuality(double value) {

		this.value = Clamping.clamp(0, 1, value);
	}

	public double getValue() {

		return value;
	}
}

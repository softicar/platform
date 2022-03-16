package com.softicar.platform.common.math;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test cases for {@link Clamping}.
 *
 * @author Oliver Richers
 */
public class ClampingTest {

	@Test
	public void clampLower() {

		double value = Clamping.clamp(6, 10, 4);

		assertEquals(6, value, 0.00001);
	}

	@Test
	public void clampUpper() {

		double value = Clamping.clamp(6, 10, 11);

		assertEquals(10, value, 0.00001);
	}

	@Test
	public void clampInside() {

		double value = Clamping.clamp(6, 10, 7);

		assertEquals(7, value, 0.00001);
	}
}

package com.softicar.platform.common.math.rounding;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * Test cases for {@link Rounding}.
 *
 * @author Oliver Richers
 */
public class RoundingTest extends AbstractTest {

	@Test
	public void testEquals() {

		assertTrue(Rounding.equal(0.0040, 0.00, 2));
		assertTrue(Rounding.equal(0.0049, 0.00, 2));
		assertTrue(Rounding.equal(0.0050, 0.01, 2));
		assertTrue(Rounding.equal(0.0090, 0.01, 2));
		assertTrue(Rounding.equal(0.0149, 0.01, 2));
		assertTrue(Rounding.equal(0.0150, 0.02, 2));

		assertTrue(Rounding.equal(-0.0040, -0.00, 2));
		assertTrue(Rounding.equal(-0.0049, -0.00, 2));
		assertTrue(Rounding.equal(-0.0050, -0.01, 2));
		assertTrue(Rounding.equal(-0.0090, -0.01, 2));
	}

	@Test
	public void testRound() {

		assertEquals(0.00, Rounding.round(0.004, 2), 0.0001);
		assertEquals(0.01, Rounding.round(0.005, 2), 0.0001);
		assertEquals(0.02, Rounding.round(0.015, 2), 0.0001);
	}
}

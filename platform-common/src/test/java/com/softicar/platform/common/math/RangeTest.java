package com.softicar.platform.common.math;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class RangeTest extends AbstractTest {

	@Test
	public void testInvalidRange() {

		assertFalse(new Range<>().isValid());
		assertTrue(new Range<>(1).isValid());
		assertTrue(new Range<>(1, 2).isValid());
	}

	@Test
	public void testSingularRange() {

		assertFalse(new Range<>().isSingular());
		assertTrue(new Range<>(1).isSingular());
		assertTrue(new Range<>(1, 1).isSingular());
		assertFalse(new Range<>(1, 2).isSingular());
	}
}

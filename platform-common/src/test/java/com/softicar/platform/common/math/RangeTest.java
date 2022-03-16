package com.softicar.platform.common.math;

import org.junit.Assert;
import org.junit.Test;

public class RangeTest extends Assert {

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

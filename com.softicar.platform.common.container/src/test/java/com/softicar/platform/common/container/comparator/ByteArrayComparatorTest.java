package com.softicar.platform.common.container.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Test cases for {@link ByteArrayComparator}.
 *
 * @author Oliver Richers
 */
public class ByteArrayComparatorTest {

	@Test
	public void returnsZeroOnEmptyArrays() {

		byte[] left = new byte[] {};
		byte[] right = new byte[] {};

		assertEquals(0, ByteArrayComparator.get().compare(left, right));
	}

	@Test
	public void returnsZeroOnEqualArrays() {

		byte[] left = new byte[] { 1, 2, 3 };
		byte[] right = new byte[] { 1, 2, 3 };

		assertEquals(0, ByteArrayComparator.get().compare(left, right));
	}

	@Test
	public void returnsPositiveIntegerWhenLeftIsGreater() {

		byte[] left = new byte[] { 2, 2, 3 };
		byte[] right = new byte[] { 1, 2, 3 };

		assertTrue(ByteArrayComparator.get().compare(left, right) > 0);
	}

	@Test
	public void returnsNegativeIntegerWhenLeftIsLess() {

		byte[] left = new byte[] { 1, 2, 3 };
		byte[] right = new byte[] { 2, 2, 3 };

		assertTrue(ByteArrayComparator.get().compare(left, right) < 0);
	}

	@Test
	public void returnsPositiveIntegerWhenLeftIsLonger() {

		byte[] left = new byte[] { 1, 2, 3, 4 };
		byte[] right = new byte[] { 1, 2, 3 };

		assertTrue(ByteArrayComparator.get().compare(left, right) > 0);
	}

	@Test
	public void returnsNegativeIntegerWhenLeftIsShorter() {

		byte[] left = new byte[] { 1, 2 };
		byte[] right = new byte[] { 1, 2, 3 };

		assertTrue(ByteArrayComparator.get().compare(left, right) < 0);
	}
}

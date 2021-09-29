package com.softicar.platform.common.container.comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Test cases for {@link ByteComparator}.
 *
 * @author Oliver Richers
 */
public class ByteComparatorTest {

	@Test
	public void returnsZeroForEqualBytes() {

		assertEquals(0, ByteComparator.get().compare((byte) 0, (byte) 0));
		assertEquals(0, ByteComparator.get().compare((byte) 2, (byte) 2));
		assertEquals(0, ByteComparator.get().compare((byte) -2, (byte) -2));
		assertEquals(0, ByteComparator.get().compare((byte) -128, (byte) -128));
		assertEquals(0, ByteComparator.get().compare((byte) 127, (byte) 127));
	}

	@Test
	public void returnsNegativeIfLeftIsLess() {

		assertTrue(ByteComparator.get().compare((byte) 0, (byte) 1) < 0);
		assertTrue(ByteComparator.get().compare((byte) 2, (byte) 3) < 0);
		assertTrue(ByteComparator.get().compare((byte) -2, (byte) -1) < 0);
		assertTrue(ByteComparator.get().compare((byte) -128, (byte) -127) < 0);
		assertTrue(ByteComparator.get().compare((byte) 126, (byte) 127) < 0);
	}

	@Test
	public void returnsPositiveIfLeftIsGreater() {

		assertTrue(ByteComparator.get().compare((byte) 1, (byte) 0) > 0);
		assertTrue(ByteComparator.get().compare((byte) 3, (byte) 2) > 0);
		assertTrue(ByteComparator.get().compare((byte) -1, (byte) -2) > 0);
		assertTrue(ByteComparator.get().compare((byte) -127, (byte) -128) > 0);
		assertTrue(ByteComparator.get().compare((byte) 127, (byte) 126) > 0);
	}
}

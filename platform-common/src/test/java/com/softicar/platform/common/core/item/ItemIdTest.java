package com.softicar.platform.common.core.item;

import org.junit.Assert;
import org.junit.Test;

public class ItemIdTest extends Assert {

	@Test
	public void testCompareTo() {

		assertEquals(0, new ItemId(0).compareTo(new ItemId(0)));
		assertEquals(0, new ItemId(23).compareTo(new ItemId(23)));
		assertEquals(0, new ItemId(+12345678912345L).compareTo(new ItemId(+12345678912345L)));
		assertEquals(0, new ItemId(-12345678912345L).compareTo(new ItemId(-12345678912345L)));

		assertEquals(-1, new ItemId(0).compareTo(new ItemId(1)));
		assertEquals(-1, new ItemId(22).compareTo(new ItemId(23)));
		assertEquals(-1, new ItemId(+12345678912345L).compareTo(new ItemId(+12345678912346L)));
		assertEquals(-1, new ItemId(-12345678912346L).compareTo(new ItemId(-12345678912345L)));

		assertEquals(1, new ItemId(1).compareTo(new ItemId(0)));
		assertEquals(1, new ItemId(23).compareTo(new ItemId(22)));
		assertEquals(1, new ItemId(+12345678912346L).compareTo(new ItemId(+12345678912345L)));
		assertEquals(1, new ItemId(-12345678912345L).compareTo(new ItemId(-12345678912346L)));
	}

	@Test
	public void testEquals() {

		assertTrue(new ItemId(0).equals(new ItemId(0)));
		assertTrue(new ItemId(23).equals(new ItemId(23)));
		assertTrue(new ItemId(+12345678912345L).equals(new ItemId(+12345678912345L)));
		assertTrue(new ItemId(-12345678912345L).equals(new ItemId(-12345678912345L)));

		assertFalse(new ItemId(0).equals(new ItemId(1)));
		assertFalse(new ItemId(23).equals(new ItemId(22)));
		assertFalse(new ItemId(+12345678912345L).equals(new ItemId(+12345678912346L)));
		assertFalse(new ItemId(-12345678912345L).equals(new ItemId(-12345678912346L)));
	}

	@Test
	public void testHashCode() {

		assertEquals(new ItemId(0).hashCode(), new ItemId(0).hashCode());
		assertEquals(new ItemId(23).hashCode(), new ItemId(23).hashCode());
		assertEquals(new ItemId(+12345678912345L).hashCode(), new ItemId(+12345678912345L).hashCode());
		assertEquals(new ItemId(-12345678912345L).hashCode(), new ItemId(-12345678912345L).hashCode());
	}

	@Test
	public void testToString() {

		assertEquals("0", new ItemId(0).toString());
		assertEquals("23", new ItemId(23).toString());
		assertEquals("12345678912345", new ItemId(+12345678912345L).toString());
		assertEquals("-12345678912345", new ItemId(-12345678912345L).toString());
	}
}

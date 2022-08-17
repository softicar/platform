package com.softicar.platform.common.core.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DisplayStringsTest {

	@Test
	public void testFormatThenToString() {

		var displayString = DisplayStrings.format("foo %s", "bar");

		assertEquals("foo bar", displayString.toString());
	}

	@Test
	public void testFormatThenEqualsWithSameObject() {

		var displayString = DisplayStrings.format("foo %s", "bar");

		assertTrue(displayString.equals(displayString));
	}

	@Test
	public void testFormatThenEqualsWithDifferentObjects() {

		var first = DisplayStrings.format("foo %s", "bar");
		var second = DisplayStrings.format("foo %s", "xxx");

		assertFalse(first.equals(second));
	}

	@Test
	public void testFormatThenHashCodeWithSameObject() {

		var displayString = DisplayStrings.format("foo %s", "bar");

		assertEquals(displayString.hashCode(), displayString.hashCode());
	}

	@Test
	public void testFormatThenHashCodeWithDifferentObjects() {

		var first = DisplayStrings.format("foo %s", "bar");
		var second = DisplayStrings.format("foo %s", "xxx");

		assertNotEquals(first.hashCode(), second.hashCode());
	}
}

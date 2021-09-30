package com.softicar.platform.common.core.i18n;

import org.junit.Assert;
import org.junit.Test;

public class FormattingDisplayStringTest extends Assert {

	@Test
	public void testToString() {

		FormattingDisplayString displayString = new FormattingDisplayString("foo %s", "bar");

		assertEquals("foo bar", displayString.toString());
	}

	@Test
	public void testEqualsWithSameObject() {

		FormattingDisplayString displayString = new FormattingDisplayString("foo %s", "bar");

		assertTrue(displayString.equals(displayString));
	}

	@Test
	public void testEqualsWithEqualObject() {

		FormattingDisplayString first = new FormattingDisplayString("foo %s", "bar");
		FormattingDisplayString second = new FormattingDisplayString("foo %s", "bar");

		assertTrue(first.equals(second));
	}

	@Test
	public void testEqualsWithDifferentObjects() {

		FormattingDisplayString first = new FormattingDisplayString("foo %s", "bar");
		FormattingDisplayString second = new FormattingDisplayString("foo %s", "xxx");

		assertFalse(first.equals(second));
	}

	@Test
	public void testHashCodeWithSameObject() {

		FormattingDisplayString displayString = new FormattingDisplayString("foo %s", "bar");

		assertEquals(displayString.hashCode(), displayString.hashCode());
	}

	@Test
	public void testHashCodeWithEqualObject() {

		FormattingDisplayString first = new FormattingDisplayString("foo %s", "bar");
		FormattingDisplayString second = new FormattingDisplayString("foo %s", "bar");

		assertEquals(first.hashCode(), second.hashCode());
	}

	@Test
	public void testHashCodeWithDifferentObjects() {

		FormattingDisplayString first = new FormattingDisplayString("foo %s", "bar");
		FormattingDisplayString second = new FormattingDisplayString("foo %s", "xxx");

		assertNotEquals(first.hashCode(), second.hashCode());
	}
}

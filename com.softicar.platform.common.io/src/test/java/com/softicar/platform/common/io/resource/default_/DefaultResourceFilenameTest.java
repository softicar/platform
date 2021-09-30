package com.softicar.platform.common.io.resource.default_;

import org.junit.Assert;
import org.junit.Test;

public class DefaultResourceFilenameTest extends Assert {

	@Test
	public void testWithSoleLetter() {

		var filename = create("x");
		assertEquals("x", filename.get());
		assertTrue(filename.isValid());
	}

	// -------------------------------- Dots -------------------------------- //

	@Test
	public void testWithSoleDot() {

		var filename = create(".");
		assertEquals(".", filename.get());
		assertFalse(filename.isValid());
	}

	@Test
	public void testWithLeadingDot() {

		var filename = create(".foo");
		assertEquals(".foo", filename.get());
		assertFalse(filename.isValid());
	}

	@Test
	public void testWithContainedDot() {

		var filename = create("foo.bar");
		assertEquals("foo.bar", filename.get());
		assertTrue(filename.isValid());
	}

	@Test
	public void testWithContainedDots() {

		var filename = create("foo.bar.baz");
		assertEquals("foo.bar.baz", filename.get());
		assertFalse(filename.isValid());
	}

	@Test
	public void testWithAdjacentDots() {

		var filename = create("foo..bar");
		assertEquals("foo..bar", filename.get());
		assertFalse(filename.isValid());
	}

	@Test
	public void testWithTailingDot() {

		var filename = create("foo.");
		assertEquals("foo.", filename.get());
		assertFalse(filename.isValid());
	}

	// -------------------------------- Digits -------------------------------- //

	@Test
	public void testWithSoleDigit() {

		var filename = create("1");
		assertEquals("1", filename.get());
		assertTrue(filename.isValid());
	}

	@Test
	public void testWithLeadingDigit() {

		var filename = create("1bar");
		assertEquals("1bar", filename.get());
		assertTrue(filename.isValid());
	}

	@Test
	public void testWithContainedDigit() {

		var filename = create("foo1bar");
		assertEquals("foo1bar", filename.get());
		assertTrue(filename.isValid());
	}

	@Test
	public void testWithContainedDigits() {

		var filename = create("foo1bar2baz");
		assertEquals("foo1bar2baz", filename.get());
		assertTrue(filename.isValid());
	}

	@Test
	public void testWithAdjacentDigits() {

		var filename = create("foo12bar");
		assertEquals("foo12bar", filename.get());
		assertTrue(filename.isValid());
	}

	@Test
	public void testWithTailingDigit() {

		var filename = create("foo1");
		assertEquals("foo1", filename.get());
		assertTrue(filename.isValid());
	}

	// -------------------------------- Dashes -------------------------------- //

	@Test
	public void testWithSoleDash() {

		var filename = create("-");
		assertEquals("-", filename.get());
		assertFalse(filename.isValid());
	}

	@Test
	public void testWithLeadingDash() {

		var filename = create("-bar");
		assertEquals("-bar", filename.get());
		assertFalse(filename.isValid());
	}

	@Test
	public void testWithContainedDash() {

		var filename = create("foo-bar");
		assertEquals("foo-bar", filename.get());
		assertTrue(filename.isValid());
	}

	@Test
	public void testWithContainedDashes() {

		var filename = create("foo-bar-baz");
		assertEquals("foo-bar-baz", filename.get());
		assertTrue(filename.isValid());
	}

	@Test
	public void testWithAdjacentDashes() {

		var filename = create("foo--bar");
		assertEquals("foo--bar", filename.get());
		assertFalse(filename.isValid());
	}

	@Test
	public void testWithTailingDash() {

		var filename = create("foo-");
		assertEquals("foo-", filename.get());
		assertFalse(filename.isValid());
	}

	// -------------------------------- Spaces -------------------------------- //

	@Test
	public void testWithSoleSpace() {

		var filename = create(" ");
		assertEquals(" ", filename.get());
		assertFalse(filename.isValid());
	}

	@Test
	public void testWithLeadingSpace() {

		var filename = create(" bar");
		assertEquals(" bar", filename.get());
		assertFalse(filename.isValid());
	}

	@Test
	public void testWithContainedSpace() {

		var filename = create("foo bar");
		assertEquals("foo bar", filename.get());
		assertFalse(filename.isValid());
	}

	@Test
	public void testWithContainedSpaces() {

		var filename = create("foo bar baz");
		assertEquals("foo bar baz", filename.get());
		assertFalse(filename.isValid());
	}

	@Test
	public void testWithAdjacentSpaces() {

		var filename = create("foo  bar");
		assertEquals("foo  bar", filename.get());
		assertFalse(filename.isValid());
	}

	@Test
	public void testWithTailingSpace() {

		var filename = create("foo ");
		assertEquals("foo ", filename.get());
		assertFalse(filename.isValid());
	}

	// -------------------------------- Combinations -------------------------------- //

	@Test
	public void testWithAdjacentDashAndDot() {

		var filename = create("foo-.bar");
		assertEquals("foo-.bar", filename.get());
		assertFalse(filename.isValid());
	}

	@Test
	public void testWithAdjacentDotAndDash() {

		var filename = create("foo.-bar");
		assertEquals("foo.-bar", filename.get());
		assertFalse(filename.isValid());
	}

	// -------------------------------- Undefined Arguments -------------------------------- //

	@Test
	public void testWithEmptyString() {

		var filename = create("");
		assertEquals("", filename.get());
		assertFalse(filename.isValid());
	}

	@Test(expected = NullPointerException.class)
	public void testWithNull() {

		create(null);
	}

	// -------------------------------- Internal -------------------------------- //

	private DefaultResourceFilename create(String filename) {

		return new DefaultResourceFilename(filename);
	}
}

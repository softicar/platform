package com.softicar.platform.common.io.resource.key;

import org.junit.Assert;
import org.junit.Test;

public class ResourceKeyBasenameTest extends Assert {

	// -------------------------------- From Filename -------------------------------- //

	@Test
	public void testFromFilenameWithoutDot() {

		var basename = fromFilename("foobar");
		assertEquals("foobar", basename.get());
		assertTrue(basename.isValid());
	}

	@Test
	public void testFromFilenameWithSingleDot() {

		var basename = fromFilename("foo.bar");
		assertEquals("foo", basename.get());
		assertTrue(basename.isValid());
	}

	@Test
	public void testFromFilenameWithSeveralDots() {

		var basename = fromFilename("foo.bar.baz");
		assertEquals("foo", basename.get());
		assertTrue(basename.isValid());
	}

	@Test
	public void testFromFilenameWithLeadingDot() {

		var basename = fromFilename(".foo.bar");
		assertEquals("", basename.get());
		assertFalse(basename.isValid());
	}

	@Test
	public void testFromFilenameWithTailingDot() {

		var basename = fromFilename("foo.bar.");
		assertEquals("foo", basename.get());
		assertTrue(basename.isValid());
	}

	@Test
	public void testFromFilenameWithEmptyString() {

		var basename = fromFilename("");
		assertEquals("", basename.get());
		assertFalse(basename.isValid());
	}

	@Test(expected = NullPointerException.class)
	public void testFromFilenameWithNull() {

		fromFilename(null);
	}

	// -------------------------------- From Basename -------------------------------- //

	@Test
	public void testFromBasenameWithNumberAndDash() {

		var basename = fromBasename("foo-bar-0");
		assertEquals("foo-bar-0", basename.get());
		assertTrue(basename.isValid());
	}

	@Test
	public void testFromBasenameWithAdjacentDashes() {

		var basename = fromBasename("foo--bar");
		assertEquals("foo--bar", basename.get());
		assertFalse(basename.isValid());
	}

	@Test
	public void testFromBasenameWithContainedDot() {

		var basename = fromBasename("foo.bar");
		assertEquals("foo.bar", basename.get());
		assertFalse(basename.isValid());
	}

	@Test
	public void testFromBasenameWithSoleLetter() {

		var basename = fromBasename("a");
		assertEquals("a", basename.get());
		assertTrue(basename.isValid());
	}

	@Test
	public void testFromBasenameWithSoleDigit() {

		var basename = fromBasename("0");
		assertEquals("0", basename.get());
		assertTrue(basename.isValid());
	}

	@Test
	public void testFromBasenameWithLeadingDigit() {

		var basename = fromBasename("0a");
		assertEquals("0a", basename.get());
		assertTrue(basename.isValid());
	}

	@Test
	public void testFromBasenameWithTailingDigit() {

		var basename = fromBasename("a0");
		assertEquals("a0", basename.get());
		assertTrue(basename.isValid());
	}

	@Test
	public void testFromBasenameWithSoleDash() {

		var basename = fromBasename("-");
		assertEquals("-", basename.get());
		assertFalse(basename.isValid());
	}

	@Test
	public void testFromBasenameWithLeadingDash() {

		var basename = fromBasename("-a");
		assertEquals("-a", basename.get());
		assertFalse(basename.isValid());
	}

	@Test
	public void testFromBasenameWithTailingDash() {

		var basename = fromBasename("a-");
		assertEquals("a-", basename.get());
		assertFalse(basename.isValid());
	}

	@Test
	public void testFromBasenameWithEmptyString() {

		var basename = fromBasename("");
		assertEquals("", basename.get());
		assertFalse(basename.isValid());
	}

	@Test(expected = NullPointerException.class)
	public void testFromBasenameWithNull() {

		fromBasename(null);
	}

	// -------------------------------- Internal -------------------------------- //

	private ResourceKeyBasename fromFilename(String filename) {

		return ResourceKeyBasename.fromFilename(filename);
	}

	private ResourceKeyBasename fromBasename(String basename) {

		return ResourceKeyBasename.fromBasename(basename);
	}
}

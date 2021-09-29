package com.softicar.platform.common.string;

import org.junit.Assert;
import org.junit.Test;

public class TrimTest extends Assert {

	// ------------------------------ trimToLength ------------------------------ //

	@Test
	public void testTrimToLength() {

		assertEquals("12", Trim.trimToLength("12", 4));
		assertEquals("1234", Trim.trimToLength("1234", 4));
		assertEquals("1234", Trim.trimToLength("123456", 4));
	}

	@Test
	public void testTrimToLengthWithEmptyString() {

		assertEquals("", Trim.trimToLength("", 0));
		assertEquals("", Trim.trimToLength("", 4));
	}

	@Test
	public void testTrimToLengthWithNullString() {

		assertNull(Trim.trimToLength(null, 0));
		assertNull(Trim.trimToLength(null, 4));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTrimToLengthWithNegativeLength() {

		Trim.trimToLength("1234", -4);
	}

	// ------------------------------ trimToLengthWithEllipsis ------------------------------ //

	@Test
	public void testTrimToLengthWithEllipsis() {

		assertEquals("123456", Trim.trimToLengthWithEllipsis("123456", 10, "..."));
		assertEquals("123456", Trim.trimToLengthWithEllipsis("123456", 6, "..."));
		assertEquals("12...", Trim.trimToLengthWithEllipsis("123456", 5, "..."));
		assertEquals("1...", Trim.trimToLengthWithEllipsis("123456", 4, "..."));
		assertEquals("...", Trim.trimToLengthWithEllipsis("123456", 3, "..."));
		assertEquals("..", Trim.trimToLengthWithEllipsis("123456", 2, "..."));
		assertEquals(".", Trim.trimToLengthWithEllipsis("123456", 1, "..."));
		assertEquals("", Trim.trimToLengthWithEllipsis("123456", 0, "..."));

		// pathologic cases
		assertEquals("", Trim.trimToLengthWithEllipsis("", 4, "..."));
		assertEquals("", Trim.trimToLengthWithEllipsis("", -1, "..."));
		assertEquals("12", Trim.trimToLengthWithEllipsis("1234", 2, ""));
	}

	// ------------------------------ trim* ------------------------------ //

	@Test
	public void testPrefix() {

		assertEquals("Foo", Trim.trimPrefix("Foo", ""));
		assertEquals("", Trim.trimPrefix("Foo", "Foo"));
		assertEquals("", Trim.trimPrefix("", "Foo"));

		assertEquals("Foo", Trim.trimPrefix("Foo", "Prefix"));
		assertEquals("Foo", Trim.trimPrefix("PrefixFoo", "Prefix"));
		assertEquals("PrefixFoo", Trim.trimPrefix("PrefixPrefixFoo", "Prefix"));
	}

	@Test
	public void testSuffix() {

		assertEquals("Foo", Trim.trimSuffix("Foo", ""));
		assertEquals("", Trim.trimSuffix("Foo", "Foo"));
		assertEquals("", Trim.trimSuffix("", "Foo"));

		assertEquals("Foo", Trim.trimSuffix("Foo", "Suffix"));
		assertEquals("Foo", Trim.trimSuffix("FooSuffix", "Suffix"));
		assertEquals("FooSuffix", Trim.trimSuffix("FooSuffixSuffix", "Suffix"));
	}

	@Test
	public void testTrimZeros() {

		assertEquals("120", Trim.trimZeros("120"));
		assertEquals("120", Trim.trimZeros("0120"));
		assertEquals("120", Trim.trimZeros("00120"));
	}

	@Test
	public void testTrimLeft() {

		assertEquals("", Trim.trimLeft("", 'x'));
		assertEquals("", Trim.trimLeft("xxx", 'x'));
		assertEquals("FOO", Trim.trimLeft("xxFOO", 'x'));
		assertEquals("FOOxx", Trim.trimLeft("xxFOOxx", 'x'));
	}

	@Test
	public void testTrimRight() {

		assertEquals("", Trim.trimRight("", 'x'));
		assertEquals("", Trim.trimRight("xxx", 'x'));
		assertEquals("FOO", Trim.trimRight("FOOxx", 'x'));
		assertEquals("xxFOO", Trim.trimRight("xxFOOxx", 'x'));
	}

	@Test
	public void testTrim() {

		assertEquals("", Trim.trim("", 'x'));
		assertEquals("", Trim.trim("xxx", 'x'));
		assertEquals("FOO", Trim.trim("FOOxx", 'x'));
		assertEquals("FOO", Trim.trim("xxFOO", 'x'));
		assertEquals("FOO", Trim.trim("xxFOOxx", 'x'));
	}

	// ------------------------------ trimOrDefault ------------------------------ //

	@Test
	public void testTrimOrDefault() {

		assertNull(Trim.trimOrDefault(null, null));
		assertNull(Trim.trimOrDefault("", null));
		assertNull(Trim.trimOrDefault(" \n\t", null));

		assertEquals("abc", Trim.trimOrDefault("abc", null));
		assertEquals("abc", Trim.trimOrDefault(" abc ", null));
		assertEquals("abc", Trim.trimOrDefault("\nabc\t", null));

		String defaultValue = " x ";
		assertEquals(defaultValue, Trim.trimOrDefault(null, defaultValue));
		assertEquals(defaultValue, Trim.trimOrDefault("", defaultValue));
		assertEquals(defaultValue, Trim.trimOrDefault(" \n\t", defaultValue));
		assertEquals("abc", Trim.trimOrDefault(" abc ", " x "));
	}
}

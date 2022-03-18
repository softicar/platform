package com.softicar.platform.common.core.string;

import org.junit.Assert;
import org.junit.Test;

public class StringsTest extends Assert {

	@Test
	public void testReversed() {

		assertEquals("", Strings.reversed(""));
		assertEquals("cba", Strings.reversed("abc"));
	}

	@Test
	public void testSubstringClamped() {

		// not exceeding limits
		assertEquals("abc", Strings.substringClamped("abc", 0, 3));
		assertEquals("bc", Strings.substringClamped("abc", 1, 3));
		assertEquals("ab", Strings.substringClamped("abc", 0, 2));
		assertEquals("", Strings.substringClamped("abc", 1, 1));

		// exceeding limits
		assertEquals("", Strings.substringClamped("abc", -2, -1));
		assertEquals("", Strings.substringClamped("abc", -2, 0));
		assertEquals("a", Strings.substringClamped("abc", -2, 1));
		assertEquals("ab", Strings.substringClamped("abc", -2, 2));
		assertEquals("abc", Strings.substringClamped("abc", -2, 3));
		assertEquals("abc", Strings.substringClamped("abc", -2, 4));
		assertEquals("abc", Strings.substringClamped("abc", -2, 5));
		assertEquals("abc", Strings.substringClamped("abc", -1, 5));
		assertEquals("abc", Strings.substringClamped("abc", 0, 5));
		assertEquals("bc", Strings.substringClamped("abc", 1, 5));
		assertEquals("c", Strings.substringClamped("abc", 2, 5));
		assertEquals("", Strings.substringClamped("abc", 3, 5));
		assertEquals("", Strings.substringClamped("abc", 4, 5));

		// pathological
		assertEquals("", Strings.substringClamped("abc", 2, 1));
	}

	@Test
	public void testTokenize() {

		assertEquals("[]", Strings.tokenize("", "|").toString());
		assertEquals("[]", Strings.tokenize("|", "|").toString());
		assertEquals("[a]", Strings.tokenize("a", "|").toString());
		assertEquals("[a]", Strings.tokenize("a|", "|").toString());
		assertEquals("[a, b]", Strings.tokenize("a|b", "|").toString());
		assertEquals("[a, b]", Strings.tokenize("a||b", "|").toString());
	}
}

package com.softicar.platform.common.string;

import org.junit.Assert;
import org.junit.Test;

public class SubstringTest extends Assert {

	@Test
	public void testBetween() {

		assertEquals("XXX", Substring.between("aaaXXXbbb", "aaa", "bbb"));
	}

	@Test
	public void testBetweenWithEmptyText() {

		assertEquals("", Substring.between("", "aaa", "bbb"));
	}

	@Test
	public void testBetweenWithMultipleAffixMatches() {

		assertEquals("aaXXXbb", Substring.between("aaaXXXbbb", "a", "b"));
	}

	@Test
	public void testBetweenWithSuffixMatchBeforePrefixMatch() {

		assertEquals("", Substring.between("aaaXXXbbb", "bbb", "aaa"));
	}

	@Test
	public void testBetweenWithEmptyPrefix() {

		assertEquals("aaaXXX", Substring.between("aaaXXXbbb", "", "bbb"));
	}

	@Test
	public void testBetweenWithEmptySuffix() {

		assertEquals("XXXbbb", Substring.between("aaaXXXbbb", "aaa", ""));
	}

	@Test
	public void testBetweenWithEmptyAffixes() {

		assertEquals("aaaXXXbbb", Substring.between("aaaXXXbbb", "", ""));
	}

	@Test
	public void testBetweenWithEmptyTextAndAffixes() {

		assertEquals("", Substring.between("", "", ""));
	}

	@Test
	public void testBetweenWithNonMatchingPrefix() {

		assertEquals("", Substring.between("aaaXXXbbb", "c", "bbb"));
	}

	@Test
	public void testBetweenWithNonMatchingSuffix() {

		assertEquals("", Substring.between("aaaXXXbbb", "aaa", "c"));
	}

	@Test
	public void testBetweenWithEqualAffixes() {

		assertEquals("aXXXb", Substring.between("ayaXXXbyb", "y", "y"));
	}

	@Test(expected = NullPointerException.class)
	public void testBetweenWithNullText() {

		Substring.between(null, "aaa", "bbb");
	}

	@Test(expected = NullPointerException.class)
	public void testBetweenWithNullPrefix() {

		Substring.between("aaaXXXbbb", null, "bbb");
	}

	@Test(expected = NullPointerException.class)
	public void testBetweenWithNullSuffix() {

		Substring.between("aaaXXXbbb", "aaa", null);
	}
}

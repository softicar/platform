package com.softicar.platform.common.string.regex;

import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Test;

public class PatternsTest extends Assert {

	private static final Pattern LETTERS = Pattern.compile("[a-z]+");
	private static final Pattern NUMBERS = Pattern.compile("[0-9]+");
	private static final Pattern COMBINED = Pattern.compile("[a-z0-9]+");

	@Test
	public void testMatchesWithLettersAgainstLettersPattern() {

		assertTrue(Patterns.matches("foo", LETTERS));
	}

	@Test
	public void testMatchesWithLettersAgainstNumbersPattern() {

		assertFalse(Patterns.matches("foo", NUMBERS));
	}

	@Test
	public void testMatchesWithLettersAgainstCombinedPattern() {

		assertTrue(Patterns.matches("foo", COMBINED));
	}

	@Test
	public void testMatchesWithNumbersAgainstLettersPattern() {

		assertFalse(Patterns.matches("123", LETTERS));
	}

	@Test
	public void testMatchesWithNumbersAgainstNumbersPattern() {

		assertTrue(Patterns.matches("123", NUMBERS));
	}

	@Test
	public void testMatchesWithNumbersAgainstCombinedPattern() {

		assertTrue(Patterns.matches("123", COMBINED));
	}

	@Test
	public void testMatchesWithLettersAndNumbersAgainstLettersPattern() {

		assertFalse(Patterns.matches("foo123", LETTERS));
	}

	@Test
	public void testMatchesWithLettersAndNumbersAgainstNumbersPattern() {

		assertFalse(Patterns.matches("foo123", NUMBERS));
	}

	@Test
	public void testMatchesWithLettersAndNumbersAgainstCombinedPattern() {

		assertTrue(Patterns.matches("foo123", COMBINED));
	}

	@Test(expected = NullPointerException.class)
	public void testMatchesWithNullString() {

		Patterns.matches(null, LETTERS);
	}

	@Test(expected = NullPointerException.class)
	public void testMatchesWithNullPattern() {

		Patterns.matches("foo", null);
	}

	@Test
	public void testAnyMatchWithLettersAgainstSeparatePatterns() {

		assertTrue(Patterns.anyMatch("foo", LETTERS, NUMBERS));
	}

	@Test
	public void testAnyMatchWithLettersAgainstLettersPattern() {

		assertTrue(Patterns.anyMatch("foo", LETTERS));
	}

	@Test
	public void testAnyMatchWithLettersAgainstNumbersPattern() {

		assertFalse(Patterns.anyMatch("foo", NUMBERS));
	}

	@Test
	public void testAnyMatchWithLettersAgainstCombinedPattern() {

		assertTrue(Patterns.anyMatch("foo", COMBINED));
	}

	@Test
	public void testAnyMatchWithNumbersAgainstSeparatePatterns() {

		assertTrue(Patterns.anyMatch("123", LETTERS, NUMBERS));
	}

	@Test
	public void testAnyMatchWithNumbersAgainstLettersPattern() {

		assertFalse(Patterns.anyMatch("123", LETTERS));
	}

	@Test
	public void testAnyMatchWithNumbersAgainstNumbersPattern() {

		assertTrue(Patterns.anyMatch("123", NUMBERS));
	}

	@Test
	public void testAnyMatchWithNumbersAgainstCombinedPattern() {

		assertTrue(Patterns.anyMatch("123", COMBINED));
	}

	@Test
	public void testAnyMatchWithLettersAndNumbersAgainstSeparatePatterns() {

		assertFalse(Patterns.anyMatch("foo123", LETTERS, NUMBERS));
	}

	@Test
	public void testAnyMatchWithLettersAndNumbersAgainstLettersPattern() {

		assertFalse(Patterns.anyMatch("foo123", LETTERS));
	}

	@Test
	public void testAnyMatchWithLettersAndNumbersAgainstNumbersPattern() {

		assertFalse(Patterns.anyMatch("foo123", NUMBERS));
	}

	@Test
	public void testAnyMatchWithLettersAndNumbersAgainstCombinedPattern() {

		assertTrue(Patterns.anyMatch("foo123", COMBINED));
	}

	@Test
	public void testAnyMatchWithoutPatterns() {

		assertFalse(Patterns.anyMatch("foo"));
	}

	@Test
	public void testAnyMatchWithEmptyString() {

		assertFalse(Patterns.anyMatch("", LETTERS, NUMBERS));
	}

	@Test(expected = NullPointerException.class)
	public void testAnyMatchWithNullString() {

		assertFalse(Patterns.anyMatch(null, LETTERS, NUMBERS));
	}

	@Test
	public void testNoneMatchWithLettersAgainstSeparatePatterns() {

		assertFalse(Patterns.noneMatch("foo", LETTERS, NUMBERS));
	}

	@Test
	public void testNoneMatchWithLettersAgainstLettersPattern() {

		assertFalse(Patterns.noneMatch("foo", LETTERS));
	}

	@Test
	public void testNoneMatchWithLettersAgainstNumbersPattern() {

		assertTrue(Patterns.noneMatch("foo", NUMBERS));
	}

	@Test
	public void testNoneMatchWithLettersAgainstCombinedPattern() {

		assertFalse(Patterns.noneMatch("foo", COMBINED));
	}

	@Test
	public void testNoneMatchWithNumbersAgainstSeparatePatterns() {

		assertFalse(Patterns.noneMatch("123", LETTERS, NUMBERS));
	}

	@Test
	public void testNoneMatchWithNumbersAgainstLettersPattern() {

		assertTrue(Patterns.noneMatch("123", LETTERS));
	}

	@Test
	public void testNoneMatchWithNumbersAgainstNumbersPattern() {

		assertFalse(Patterns.noneMatch("123", NUMBERS));
	}

	@Test
	public void testNoneMatchWithNumbersAgainstCombinedPattern() {

		assertFalse(Patterns.noneMatch("123", COMBINED));
	}

	@Test
	public void testNoneMatchWithLettersAndNumbersAgainstSeparatePatterns() {

		assertTrue(Patterns.noneMatch("foo123", LETTERS, NUMBERS));
	}

	@Test
	public void testNoneMatchWithLettersAndNumbersAgainstLettersPattern() {

		assertTrue(Patterns.noneMatch("foo123", LETTERS));
	}

	@Test
	public void testNoneMatchWithLettersAndNumbersAgainstNumbersPattern() {

		assertTrue(Patterns.noneMatch("foo123", NUMBERS));
	}

	@Test
	public void testNoneMatchWithLettersAndNumbersAgainstCombinedPattern() {

		assertFalse(Patterns.noneMatch("foo123", COMBINED));
	}

	@Test
	public void testNoneMatchWithoutPatterns() {

		assertTrue(Patterns.noneMatch("foo"));
	}

	@Test
	public void testNoneMatchWithEmptyString() {

		assertTrue(Patterns.noneMatch("", LETTERS, NUMBERS));
	}

	@Test(expected = NullPointerException.class)
	public void testNoneMatchWithNullString() {

		assertFalse(Patterns.noneMatch(null, LETTERS, NUMBERS));
	}
}

package com.softicar.platform.common.string.regex;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.regex.Pattern;
import org.junit.Test;

public class PatternFinderTest extends AbstractTest {

	/**
	 * {@link PatternFinder#indexOfPattern(String, String)}
	 */
	@Test
	public void testIndexOfPattern() {

		var result = PatternFinder.indexOfPattern("f[o]{2}", "_foo_foo_bar");
		assertEquals(1, result.getOffset());
		assertEquals("foo", result.getMatchingText());
	}

	/**
	 * {@link PatternFinder#indexOfPattern(String, String)}
	 */
	@Test
	public void testIndexOfPatternWithoutMatch() {

		var result = PatternFinder.indexOfPattern("x[o]{2}", "_foo_foo_bar");
		assertEquals(-1, result.getOffset());
		assertNull(result.getMatchingText());
	}

	/**
	 * {@link PatternFinder#indexOfPattern(String, String)}
	 */
	@Test
	public void testIndexOfPatternWithEmptyPattern() {

		var result = PatternFinder.indexOfPattern("", "_foo_foo_bar");
		assertEquals(0, result.getOffset());
		assertEquals("", result.getMatchingText());
	}

	/**
	 * {@link PatternFinder#indexOfPattern(String, String)}
	 */
	@Test
	public void testIndexOfPatternWithEmptyText() {

		var result = PatternFinder.indexOfPattern("f[o]{2}", "");
		assertEquals(-1, result.getOffset());
		assertNull(result.getMatchingText());
	}

	/**
	 * {@link PatternFinder#indexOfPattern(String, String)}
	 */
	@Test
	public void testIndexOfPatternWithEmptyPatternAndEmptyText() {

		var result = PatternFinder.indexOfPattern("", "");
		assertEquals(0, result.getOffset());
		assertEquals("", result.getMatchingText());
	}

	/**
	 * {@link PatternFinder#indexOfPattern(String, String, int)}
	 */
	@Test
	public void testIndexOfPatternWithOffset() {

		var result = PatternFinder.indexOfPattern("f[o]{2}", "_foo_foo_bar", 2);
		assertEquals(5, result.getOffset());
		assertEquals("foo", result.getMatchingText());
	}

	/**
	 * {@link PatternFinder#indexOfPattern(Pattern, String, int)}
	 */
	@Test
	public void testIndexOfPatternWithOffsetAndPrecompiledPattern() {

		var pattern = Pattern.compile("f[o]{2}");
		var result = PatternFinder.indexOfPattern(pattern, "_foo_foo_bar", 2);
		assertEquals(5, result.getOffset());
		assertEquals("foo", result.getMatchingText());
	}

	/**
	 * {@link PatternFinder#lastIndexOfPattern(String, String)}
	 */
	@Test
	public void testLastIndexOfPattern() {

		var result = PatternFinder.lastIndexOfPattern("f[o]{2}", "_foo_foo_bar");
		assertEquals(5, result);
	}

	/**
	 * {@link PatternFinder#lastIndexOfPattern(String, String)}
	 */
	@Test
	public void testLastIndexOfPatternWithoutMatch() {

		var result = PatternFinder.lastIndexOfPattern("x[o]{2}", "_foo_foo_bar");
		assertEquals(-1, result);
	}

	/**
	 * {@link PatternFinder#lastIndexOfPattern(String, String)}
	 */
	@Test
	public void testLastIndexOfPatternWithEmptyPattern() {

		var result = PatternFinder.lastIndexOfPattern("", "_foo_foo_bar");
		assertEquals(12, result);
	}

	/**
	 * {@link PatternFinder#lastIndexOfPattern(String, String)}
	 */
	@Test
	public void testLastIndexOfPatternWithEmptyText() {

		var result = PatternFinder.lastIndexOfPattern("f[o]{2}", "");
		assertEquals(-1, result);
	}

	/**
	 * {@link PatternFinder#lastIndexOfPattern(String, String)}
	 */
	@Test
	public void testLastIndexOfPatternWithEmptyPatternAndEmptyText() {

		var result = PatternFinder.lastIndexOfPattern("", "");
		assertEquals(0, result);
	}
}

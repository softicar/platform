package com.softicar.platform.common.string.regex;

import org.junit.Assert;
import org.junit.Test;

public class PatternFinderResultTest extends Assert {

	@Test
	public void testConstructor() {

		var result = new PatternFinderResult("foo", 5);
		assertEquals("foo", result.getMatchingText());
		assertEquals(5, result.getOffset());
	}

	@Test
	public void testConstructorWithMissingValues() {

		var result = new PatternFinderResult(null, -1);
		assertNull(result.getMatchingText());
		assertEquals(-1, result.getOffset());
	}
}

package com.softicar.platform.common.core.number.parser;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for {@link IntegerParser}.
 *
 * @author Oliver Richers
 */
public class LongParserTest extends Assert {

	@Test
	public void isLong() {

		assertFalse(LongParser.isLong(null));
		assertFalse(LongParser.isLong("foo"));
		assertFalse(LongParser.isLong("123foo"));
		assertFalse(LongParser.isLong("9223372036854775808"));
		assertFalse(LongParser.isLong("-9223372036854775809"));

		assertTrue(LongParser.isLong("123"));
		assertTrue(LongParser.isLong("-123"));
		assertTrue(LongParser.isLong("2147483647"));
		assertTrue(LongParser.isLong("-2147483648"));
		assertTrue(LongParser.isLong("9223372036854775807"));
		assertTrue(LongParser.isLong("-9223372036854775808"));
	}

	@Test
	public void parseLong() {

		assertEquals(null, LongParser.parseLong(null));
		assertEquals(null, LongParser.parseLong("foo"));
		assertEquals(null, LongParser.parseLong("123foo"));
		assertEquals(null, LongParser.parseLong("9223372036854775808"));
		assertEquals(null, LongParser.parseLong("-9223372036854775809"));

		assertEquals(Long.valueOf(123), LongParser.parseLong("123"));
		assertEquals(Long.valueOf(-123), LongParser.parseLong("-123"));
		assertEquals(Long.valueOf(9223372036854775807L), LongParser.parseLong("9223372036854775807"));
		assertEquals(Long.valueOf(-9223372036854775808L), LongParser.parseLong("-9223372036854775808"));
	}
}

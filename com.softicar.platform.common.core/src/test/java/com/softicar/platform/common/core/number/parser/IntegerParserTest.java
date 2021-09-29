package com.softicar.platform.common.core.number.parser;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for {@link IntegerParser}.
 *
 * @author Oliver Richers
 */
public class IntegerParserTest extends Assert {

	@Test
	public void isInteger() {

		assertFalse(IntegerParser.isInteger(null));
		assertFalse(IntegerParser.isInteger("foo"));
		assertFalse(IntegerParser.isInteger("123foo"));
		assertFalse(IntegerParser.isInteger("2147483648"));
		assertFalse(IntegerParser.isInteger("-2147483649"));

		assertTrue(IntegerParser.isInteger("123"));
		assertTrue(IntegerParser.isInteger("-123"));
		assertTrue(IntegerParser.isInteger("2147483647"));
		assertTrue(IntegerParser.isInteger("-2147483648"));
	}

	@Test
	public void parseInteger() {

		assertEquals(null, IntegerParser.parseInteger(null));
		assertEquals(null, IntegerParser.parseInteger("foo"));
		assertEquals(null, IntegerParser.parseInteger("123foo"));
		assertEquals(null, IntegerParser.parseInteger("2147483648"));
		assertEquals(null, IntegerParser.parseInteger("-2147483649"));

		assertEquals(Integer.valueOf(123), IntegerParser.parseInteger("123"));
		assertEquals(Integer.valueOf(-123), IntegerParser.parseInteger("-123"));
		assertEquals(Integer.valueOf(2147483647), IntegerParser.parseInteger("2147483647"));
		assertEquals(Integer.valueOf(-2147483648), IntegerParser.parseInteger("-2147483648"));
	}
}

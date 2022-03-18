package com.softicar.platform.common.core.number.parser;

import org.junit.Assert;
import org.junit.Test;

public class DoubleParserTest extends Assert {

	@Test
	public void isDouble() {

		assertFalse(DoubleParser.isDouble(null));
		assertFalse(DoubleParser.isDouble("foo"));
		assertFalse(DoubleParser.isDouble("123foo"));

		assertTrue(DoubleParser.isDouble("123"));
		assertTrue(DoubleParser.isDouble("123.432"));
		assertTrue(DoubleParser.isDouble("123.432e23"));

		assertTrue(DoubleParser.isDouble("-123"));
		assertTrue(DoubleParser.isDouble("-123.432"));
		assertTrue(DoubleParser.isDouble("-123.432e-23"));
	}

	@Test
	public void parseDouble() {

		assertNull(DoubleParser.parseDouble(null));
		assertNull(DoubleParser.parseDouble("foo"));
		assertNull(DoubleParser.parseDouble("123foo"));

		assertEquals(123.0, DoubleParser.parseDouble("123"), 1.0);
		assertEquals(123.432, DoubleParser.parseDouble("123.432"), 0.001);
		assertEquals(123.432e23, DoubleParser.parseDouble("123.432e23"), 0.001e23);

		assertEquals(-123.0, DoubleParser.parseDouble("-123"), 1.0);
		assertEquals(-123.432, DoubleParser.parseDouble("-123.432"), 0.001);
		assertEquals(-123.432e-23, DoubleParser.parseDouble("-123.432e-23"), 0.001e-23);
	}
}

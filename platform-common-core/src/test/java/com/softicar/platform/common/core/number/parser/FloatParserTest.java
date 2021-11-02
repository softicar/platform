package com.softicar.platform.common.core.number.parser;

import org.junit.Assert;
import org.junit.Test;

public class FloatParserTest extends Assert {

	@Test
	public void parseFloat() {

		assertNull(FloatParser.parseFloat(null));
		assertNull(FloatParser.parseFloat("foo"));
		assertNull(FloatParser.parseFloat("123foo"));

		assertEquals(123.0f, FloatParser.parseFloat("123"), 1.0f);
		assertEquals(123.432f, FloatParser.parseFloat("123.432"), 0.001f);
		assertEquals(123.432e23f, FloatParser.parseFloat("123.432e23"), 0.001e23f);

		assertEquals(-123.0f, FloatParser.parseFloat("-123"), 1.0f);
		assertEquals(-123.432f, FloatParser.parseFloat("-123.432"), 0.001f);
		assertEquals(-123.432e-23f, FloatParser.parseFloat("-123.432e-23"), 0.001e-23f);
	}
}

package com.softicar.platform.common.string.formatting;

import org.junit.Assert;
import org.junit.Test;

public class DoubleFormatterTest extends Assert {

	@Test
	public void testFormatWithGroupSeparator() {

		assertEquals("0.12", new DoubleFormatter(0.1234, 2).setGroupSeparator(',').format());
		assertEquals("123.12", new DoubleFormatter(123.1234, 2).setGroupSeparator(',').format());
		assertEquals("1,234.12", new DoubleFormatter(1234.1234, 2).setGroupSeparator(',').format());
		assertEquals("123,456.12", new DoubleFormatter(123456.1234, 2).setGroupSeparator(',').format());
		assertEquals("1,234,567.12", new DoubleFormatter(1234567.1234, 2).setGroupSeparator(',').format());

		assertEquals("-0.12", new DoubleFormatter(-0.1234, 2).setGroupSeparator(',').format());
		assertEquals("-123.12", new DoubleFormatter(-123.1234, 2).setGroupSeparator(',').format());
		assertEquals("-1,234.12", new DoubleFormatter(-1234.1234, 2).setGroupSeparator(',').format());
		assertEquals("-123,456.12", new DoubleFormatter(-123456.1234, 2).setGroupSeparator(',').format());
		assertEquals("-1,234,567.12", new DoubleFormatter(-1234567.1234, 2).setGroupSeparator(',').format());
	}

	@Test
	public void testFormatWithRetainZeros() {

		assertEquals("0", new DoubleFormatter(0.0, 2).format());
		assertEquals("0.1", new DoubleFormatter(0.10, 2).format());
		assertEquals("0.1", new DoubleFormatter(0.099, 2).format());

		assertEquals("0.00", new DoubleFormatter(0.0, 2).setRetainZeros(true).format());
		assertEquals("0.10", new DoubleFormatter(0.10, 2).setRetainZeros(true).format());
		assertEquals("0.10", new DoubleFormatter(0.099, 2).setRetainZeros(true).format());

		assertEquals("0.000", new DoubleFormatter(0.0, 3).setRetainZeros(true).format());
		assertEquals("0.100", new DoubleFormatter(0.10, 3).setRetainZeros(true).format());
		assertEquals("0.099", new DoubleFormatter(0.099, 3).setRetainZeros(true).format());
	}

	@Test
	public void testFormatWithDecimalSeparator() {

		assertEquals("123.12", new DoubleFormatter(123.1234, 2).format());
		assertEquals("123,12", new DoubleFormatter(123.1234, 2).setDecimalSeparator(',').format());
		assertEquals("123x12", new DoubleFormatter(123.1234, 2).setDecimalSeparator('x').format());
	}
}

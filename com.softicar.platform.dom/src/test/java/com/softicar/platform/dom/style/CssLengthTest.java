package com.softicar.platform.dom.style;

import org.junit.Assert;
import org.junit.Test;

public class CssLengthTest extends Assert {

	@Test
	public void testToStringWithPixel() {

		assertEquals("-15.55px", new CssLength(CssLengthUnit.PX, -15.55).toString());
		assertEquals("-15px", new CssLength(CssLengthUnit.PX, -15).toString());
		assertEquals("0px", new CssLength(CssLengthUnit.PX, 0).toString());
		assertEquals("15px", new CssLength(CssLengthUnit.PX, 15).toString());
		assertEquals("15.1px", new CssLength(CssLengthUnit.PX, 15.1).toString());
		assertEquals("15.125px", new CssLength(CssLengthUnit.PX, 15.125).toString());
		assertEquals("15.33px", new CssLength(CssLengthUnit.PX, 15.33).toString());
		assertEquals("15.5px", new CssLength(CssLengthUnit.PX, 15.5).toString());
		assertEquals("123px", new CssLength(CssLengthUnit.PX, 123).toString());
	}

	@Test
	public void testToStringWithPercent() {

		assertEquals("-22%", new CssLength(CssLengthUnit.PERCENT, -22).toString());
		assertEquals("0%", new CssLength(CssLengthUnit.PERCENT, 0).toString());
		assertEquals("33%", new CssLength(CssLengthUnit.PERCENT, 33).toString());
		assertEquals("100%", new CssLength(CssLengthUnit.PERCENT, 100).toString());
	}

	@Test
	public void testGetValue() {

		assertEquals(-22, new CssLength(CssLengthUnit.PERCENT, -22).getValue(), 0.1);
		assertEquals(0, new CssLength(CssLengthUnit.PX, 0).getValue(), 0.1);
		assertEquals(0.1, new CssLength(CssLengthUnit.MM, 0.1).getValue(), 0.01);
	}

	@Test
	public void testPlus() {

		assertEquals(11.3, new CssLength(CssLengthUnit.PX, -22).plus(33.3).getValue(), 0.1);
		assertEquals(-55.3, new CssLength(CssLengthUnit.PX, -22).plus(-33.3).getValue(), 0.1);
	}
}

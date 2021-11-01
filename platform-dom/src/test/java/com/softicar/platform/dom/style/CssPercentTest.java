package com.softicar.platform.dom.style;

import org.junit.Assert;
import org.junit.Test;

public class CssPercentTest extends Assert {

	@Test
	public void testToString() {

		assertEquals("-22%", new CssPercent(-22).toString());
		assertEquals("0%", new CssPercent(0).toString());
		assertEquals("33%", new CssPercent(33).toString());
	}
}

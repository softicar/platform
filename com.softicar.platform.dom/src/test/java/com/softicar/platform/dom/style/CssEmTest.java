package com.softicar.platform.dom.style;

import org.junit.Assert;
import org.junit.Test;

public class CssEmTest extends Assert {

	@Test
	public void testToString() {

		assertEquals("-22em", new CssEm(-22).toString());
		assertEquals("0em", new CssEm(0).toString());
		assertEquals("33em", new CssEm(33).toString());
	}
}

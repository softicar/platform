package com.softicar.platform.dom.style;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class CssPixelTest extends AbstractTest {

	@Test
	public void testToString() {

		assertEquals("-22px", new CssPixel(-22).toString());
		assertEquals("0px", new CssPixel(0).toString());
		assertEquals("33px", new CssPixel(33).toString());
	}
}

package com.softicar.platform.dom.styles;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.common.ui.color.RgbColor;
import com.softicar.platform.dom.style.CssEm;
import com.softicar.platform.dom.style.CssPercent;
import com.softicar.platform.dom.style.CssPixel;
import org.junit.Test;

public class CssBoxShadowTest extends AbstractTest {

	@Test
	public void testNormalShadow() {

		CssBoxShadow shadow = new CssBoxShadow(//
			new CssPixel(1),
			new CssEm(2),
			new CssPercent(1),
			new CssPixel(3),
			new RgbColor(0xABCDEF));
		assertEquals("1px 2em 1% 3px #ABCDEF", shadow.getValue());
	}

	@Test
	public void testInsetShadow() {

		CssBoxShadow shadow = new CssBoxShadow(//
			new CssPixel(1),
			new CssEm(2),
			new CssPercent(1),
			new CssPixel(3),
			new RgbColor(0xABCDEF),
			CssBoxShadowType.INSET);
		assertEquals("1px 2em 1% 3px #ABCDEF inset", shadow.getValue());
	}
}

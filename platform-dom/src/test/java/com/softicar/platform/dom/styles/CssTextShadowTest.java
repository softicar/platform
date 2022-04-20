package com.softicar.platform.dom.styles;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.common.ui.color.RgbColor;
import com.softicar.platform.dom.style.CssEm;
import com.softicar.platform.dom.style.CssPercent;
import com.softicar.platform.dom.style.CssPixel;
import org.junit.Test;

public class CssTextShadowTest extends AbstractTest {

	@Test
	public void test() {

		CssTextShadow shadow = new CssTextShadow(//
			new CssPixel(1),
			new CssEm(2),
			new CssPercent(1),
			new RgbColor(0xABCDEF));
		assertEquals("1px 2em 1% #ABCDEF", shadow.getValue());
	}
}

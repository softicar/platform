package com.softicar.platform.dom.styles;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.common.ui.color.RgbColor;
import com.softicar.platform.dom.style.CssPixel;
import org.junit.Test;

public class CssBorderTest extends AbstractTest {

	@Test
	public void test() {

		CssBorder border = new CssBorder(new CssPixel(4), CssBorderStyle.DASHED, new RgbColor(0xABCDEF));
		assertEquals("4px dashed #ABCDEF", border.toString());
	}
}

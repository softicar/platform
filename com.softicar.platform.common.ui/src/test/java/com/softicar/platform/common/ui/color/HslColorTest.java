package com.softicar.platform.common.ui.color;

import org.junit.Assert;
import org.junit.Test;

public class HslColorTest extends Assert {

	@Test
	public void testConstructorWithRgbColor() {

		// gray
		assertHsl(0, 0, 0.000, new HslColor(new RgbColor(0x000000)));
		assertHsl(0, 0, 0.250, new HslColor(new RgbColor(0x404040)));
		assertHsl(0, 0, 0.498, new HslColor(new RgbColor(0x7F7F7F)));
		assertHsl(0, 0, 0.502, new HslColor(new RgbColor(0x808080)));
		assertHsl(0, 0, 0.750, new HslColor(new RgbColor(0xBFBFBF)));
		assertHsl(0, 0, 1.000, new HslColor(new RgbColor(0xFFFFFF)));

		// red
		assertHsl(0.0, 1.0, 0.25, new HslColor(new RgbColor(0x800000)));
		assertHsl(0.0, 1.0, 0.50, new HslColor(new RgbColor(0xFF0000)));
		assertHsl(0.0, 1.0, 0.75, new HslColor(new RgbColor(0xFF8080)));

		// green
		assertHsl(120.0, 1.0, 0.25, new HslColor(new RgbColor(0x008000)));
		assertHsl(120.0, 1.0, 0.50, new HslColor(new RgbColor(0x00FF00)));
		assertHsl(120.0, 1.0, 0.75, new HslColor(new RgbColor(0x80FF80)));

		// blue
		assertHsl(240.0, 1.0, 0.25, new HslColor(new RgbColor(0x000080)));
		assertHsl(240.0, 1.0, 0.50, new HslColor(new RgbColor(0x0000FF)));
		assertHsl(240.0, 1.0, 0.75, new HslColor(new RgbColor(0x8080FF)));

		// various
		assertHsl(027.5, 0.898, 0.614, new HslColor(new RgbColor(0xF59544)));
		assertHsl(306.3, 0.666, 0.424, new HslColor(new RgbColor(0xB424A5)));
		assertHsl(162.4, 0.780, 0.447, new HslColor(new RgbColor(0x19CB97)));
		assertHsl(089.8, 0.824, 0.555, new HslColor(new RgbColor(0x8EEB30)));
		assertHsl(248.4, 0.600, 0.373, new HslColor(new RgbColor(0x362698)));
		assertHsl(283.7, 0.777, 0.543, new HslColor(new RgbColor(0xB430E5)));
		assertHsl(200.5, 0.554, 0.457, new HslColor(new RgbColor(0x3489B5)));
	}

	@Test
	public void testGetRgbValue() {

		// gray
		assertRgb(0x000000, new HslColor(0, 0, 0.00).getRgbValue());
		assertRgb(0x404040, new HslColor(0, 0, 0.25).getRgbValue());
		assertRgb(0x808080, new HslColor(0, 0, 0.50).getRgbValue());
		assertRgb(0xBFBFBF, new HslColor(0, 0, 0.75).getRgbValue());
		assertRgb(0xFFFFFF, new HslColor(0, 0, 1.00).getRgbValue());

		// red
		assertRgb(0x800000, new HslColor(0.0, 1.0, 0.25).getRgbValue());
		assertRgb(0xFF0000, new HslColor(0.0, 1.0, 0.50).getRgbValue());
		assertRgb(0xFF8080, new HslColor(0.0, 1.0, 0.75).getRgbValue());

		// green
		assertRgb(0x008000, new HslColor(120.0, 1.0, 0.25).getRgbValue());
		assertRgb(0x00FF00, new HslColor(120.0, 1.0, 0.50).getRgbValue());
		assertRgb(0x80FF80, new HslColor(120.0, 1.0, 0.75).getRgbValue());

		// blue
		assertRgb(0x000080, new HslColor(240.0, 1.0, 0.25).getRgbValue());
		assertRgb(0x0000FF, new HslColor(240.0, 1.0, 0.50).getRgbValue());
		assertRgb(0x8080FF, new HslColor(240.0, 1.0, 0.75).getRgbValue());

		// various
		assertRgb(0xF59544, new HslColor(027.5, 0.898, 0.614).getRgbValue());
		assertRgb(0xB424A5, new HslColor(306.3, 0.666, 0.424).getRgbValue());
		assertRgb(0x19CB97, new HslColor(162.4, 0.780, 0.447).getRgbValue());
		assertRgb(0x8EEB30, new HslColor(089.8, 0.824, 0.555).getRgbValue());
		assertRgb(0x362698, new HslColor(248.4, 0.600, 0.373).getRgbValue());
		assertRgb(0xB430E5, new HslColor(283.7, 0.777, 0.543).getRgbValue());
		assertRgb(0x3489B5, new HslColor(200.5, 0.554, 0.457).getRgbValue());
	}

	@Test
	public void testToHtml() {

		// without alpha
		assertEquals("hsl(0,0%,0%)", new HslColor(0, 0, 0).toHtml());
		assertEquals("hsl(0,0%,100%)", new HslColor(0, 0, 1).toHtml());
		assertEquals("hsl(240,100%,75%)", new HslColor(240.0, 1.0, 0.75).toHtml());

		// with alpha
		assertEquals("hsla(0,0%,0%,0.0)", new HslColor(0, 0, 0, 0).toHtml());
		assertEquals("hsla(0,0%,100%,0.25)", new HslColor(0, 0, 1, 0.25).toHtml());
		assertEquals("hsla(240,100%,75%,0.75)", new HslColor(240.0, 1.0, 0.75, 0.75).toHtml());
	}

	private static void assertRgb(int expectedRgb, int actualRgb) {

		assertEquals(Integer.toHexString(expectedRgb), Integer.toHexString(actualRgb));
	}

	private static void assertHsl(double hue, double saturation, double lightness, HslColor hslColor) {

		assertEquals(hue, hslColor.getHue(), 0.1);
		assertEquals(saturation, hslColor.getSaturation(), 0.001);
		assertEquals(lightness, hslColor.getLightness(), 0.001);
	}
}

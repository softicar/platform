package com.softicar.platform.common.ui.color;

import static org.junit.Assert.assertEquals;
import com.softicar.platform.common.core.utils.DevNull;
import org.junit.Test;

public class RgbColorTest {

	@Test
	public void parseHtmlCode() {

		RgbColor color = RgbColor.parseHtmlCode("#abcdef");
		assertEquals(0xAB, color.getRed());
		assertEquals(0xCD, color.getGreen());
		assertEquals(0xEF, color.getBlue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwsOnIllegalColorValue() {

		DevNull.swallow(new RgbColor(0x1FFFFFF));
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwsOnLessThanZero() {

		DevNull.swallow(new RgbColor(-0.1, 0.1, 0.1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwsOnGreaterThanOne() {

		DevNull.swallow(new RgbColor(1.1, 0.1, 0.1));
	}

	@Test
	public void returnsCorrectlyNormalizedChannelValues() {

		RgbColor color = new RgbColor(0.1, 0.2, 0.3);

		assertEquals(0.1, color.getRedNormalized(), 1.0 / 255);
		assertEquals(0.2, color.getGreenNormalized(), 1.0 / 255);
		assertEquals(0.3, color.getBlueNormalized(), 1.0 / 255);
	}

	@Test
	public void constructsColorByColorValue() {

		assertEquals("#000000", new RgbColor(0x000000).toHtml());
		assertEquals("#000056", new RgbColor(0x000056).toHtml());
		assertEquals("#34FF00", new RgbColor(0x34FF00).toHtml());
		assertEquals("#C0D0E0", new RgbColor(0xC0D0E0).toHtml());
		assertEquals("#FFFFFF", new RgbColor(0xFFFFFF).toHtml());
	}

	@Test
	public void constructsColorByByteChannels() {

		assertEquals("#000000", new RgbColor(0x00, 0x00, 0x00).toHtml());
		assertEquals("#000056", new RgbColor(0x00, 0x00, 0x56).toHtml());
		assertEquals("#34FF00", new RgbColor(0x34, 0xFF, 0x00).toHtml());
		assertEquals("#C0D0E0", new RgbColor(0xC0, 0xD0, 0xE0).toHtml());
		assertEquals("#FFFFFF", new RgbColor(0xFF, 0xFF, 0xFF).toHtml());
	}

	@Test
	public void constructsColorByNormalizedChannels() {

		assertEquals("#000000", new RgbColor(0.0, 0.0, 0.0).toHtml().toUpperCase());
		assertEquals("#FFFFFF", new RgbColor(1.0, 1.0, 1.0).toHtml().toUpperCase());
		assertEquals("#FF0000", new RgbColor(1.0, 0.0, 0.0).toHtml().toUpperCase());
		assertEquals("#00FF00", new RgbColor(0.0, 1.0, 0.0).toHtml().toUpperCase());
		assertEquals("#0000FF", new RgbColor(0.0, 0.0, 1.0).toHtml().toUpperCase());
		assertEquals("#4080C0", new RgbColor(0x40 / 255.0, 0x80 / 255.0, 0xC0 / 255.0).toHtml().toUpperCase());
	}
}

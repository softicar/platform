package com.softicar.platform.common.ui.color;

import com.softicar.platform.common.math.Clamping;
import com.softicar.platform.common.string.Padding;
import java.util.Objects;

/**
 * Simple color class with channels for red, green and blue.
 *
 * @author Oliver Richers
 */
public class RgbColor implements IColor {

	private final int rgbValue;

	/**
	 * Constructs this color using the specified RGB color value.
	 * <p>
	 * The color value is in the usual 24 bit value format, where the highest 8
	 * bits represent the red color channel, the lowest 8 bits represent the
	 * blue color channel and the 8 bits between red and blue represent the
	 * green color channel.
	 *
	 * @param rgbValue
	 *            the color value in the usual 24 bit layout; the value must be
	 *            in the range [0,0xFFFFFF]
	 */
	public RgbColor(int rgbValue) {

		this.rgbValue = rgbValue;

		if (rgbValue < 0 || rgbValue > 0xFFFFFF) {
			throw new IllegalArgumentException(
				String.format("Value of color value must be in the range [0, 0xFFFFFF] but is %s.", Integer.toHexString(rgbValue)));
		}
	}

	/**
	 * Constructs this color by specifying each color channel independently with
	 * a precision of 8 bits per channel.
	 *
	 * @param red
	 *            the value for the red channel in the range [0,0xFF]
	 * @param green
	 *            the value for the green channel in the range [0,0xFF]
	 * @param blue
	 *            the value for the blue channel in the range [0,0xFF]
	 */
	public RgbColor(int red, int green, int blue) {

		this(getColorValue(red, green, blue));
	}

	/**
	 * Constructs this color by specifying each color channel independently as
	 * normalized value.
	 *
	 * @param red
	 *            the value for the red channel in the range [0.0, 1.0]
	 * @param green
	 *            the value for the green channel in the range [0.0, 1.0]
	 * @param blue
	 *            the value for the blue channel in the range [0.0, 1.0]
	 */
	public RgbColor(double red, double green, double blue) {

		this(getChannelAsInt(red), getChannelAsInt(green), getChannelAsInt(blue));
	}

	public static RgbColor parseHtmlCode(String htmlCode) {

		if (htmlCode.length() == 7 && htmlCode.charAt(0) == '#') {
			int colorValue = Integer.parseInt(htmlCode.substring(1), 16);
			return new RgbColor(colorValue);
		} else {
			throw new IllegalArgumentException(String.format("Illegal HTML color code '%s'.", htmlCode));
		}
	}

	public int getRgbValue() {

		return rgbValue & 0xFFFFFF;
	}

	public int getRed() {

		return rgbValue >> 16 & 0xFF;
	}

	public int getGreen() {

		return rgbValue >> 8 & 0xFF;
	}

	public int getBlue() {

		return rgbValue & 0xFF;
	}

	public double getRedNormalized() {

		return getRed() / 255.0;
	}

	public double getGreenNormalized() {

		return getGreen() / 255.0;
	}

	public double getBlueNormalized() {

		return getBlue() / 255.0;
	}

	/**
	 * Converts this color into a string in the usual HTML color format.
	 * <p>
	 * The returned string starts with a hash character and followed by the
	 * color value in hexadecimal format. The length of the returned string is
	 * always 7 characters.
	 *
	 * @return the value of this color in the usual HTML color format
	 */
	@Override
	public String toHtml() {

		String hex = Integer.toHexString(rgbValue).toUpperCase();
		return "#" + Padding.padLeft(hex, '0', 6);
	}

	@Override
	public int hashCode() {

		return Objects.hash(rgbValue);
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof RgbColor) {
			return rgbValue == ((RgbColor) other).rgbValue;
		} else {
			return false;
		}
	}

	// -------------------- private -------------------- //

	private static int getChannelAsInt(double channel) {

		checkChannel(channel);
		int channelValue = (int) Math.round(channel * 255);
		return Clamping.clamp(0, 255, channelValue);
	}

	private static int getColorValue(int red, int green, int blue) {

		checkChannel(red);
		checkChannel(green);
		checkChannel(blue);

		return (red << 16) + (green << 8) + blue;
	}

	private static void checkChannel(int value) {

		if (value < 0 || value > 255) {
			throw new IllegalArgumentException(String.format("Value of color channel must be in the range [0, 255] but is %d.", value));
		}
	}

	private static void checkChannel(double value) {

		if (value < 0.0 || value > 1.0) {
			throw new IllegalArgumentException(String.format("Value of color channel must be in the range [0.0, 1.0] but is %.3f.", value));
		}
	}
}

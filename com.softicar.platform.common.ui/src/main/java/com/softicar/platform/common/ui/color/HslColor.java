package com.softicar.platform.common.ui.color;

public class HslColor implements IColor {

	private final double hue;
	private final double saturation;
	private final double lightness;
	private final double alpha;

	public HslColor(RgbColor rgbColor) {

		double r = rgbColor.getRedNormalized();
		double g = rgbColor.getGreenNormalized();
		double b = rgbColor.getBlueNormalized();

		double M = maxValue(r, g, b);
		double m = minValue(r, g, b);
		double C = M - m;
		this.hue = computeHue(M, C, r, g, b);
		this.lightness = computeLightness(M, m);
		this.saturation = computeSaturation(C, lightness);
		this.alpha = 1.0;
	}

	public HslColor(double hue, double saturation, double lightness) {

		this(hue, saturation, lightness, 1.0);
	}

	public HslColor(double hue, double saturation, double lightness, double alpha) {

		this.hue = hue;
		this.saturation = saturation;
		this.lightness = lightness;
		this.alpha = alpha;

		if (hue < 0 || hue > 360) {
			throw new IllegalArgumentException(String.format("Illegal hue value %s. Must be in the range [0, 360].", hue));
		} else if (saturation < 0 || saturation > 1) {
			throw new IllegalArgumentException(String.format("Illegal saturation value %s. Must be in the range [0, 1].", saturation));
		} else if (lightness < 0 || lightness > 1) {
			throw new IllegalArgumentException(String.format("Illegal lightness value %s. Must be in the range [0, 1].", lightness));
		}
	}

	public double getHue() {

		return hue;
	}

	public double getSaturation() {

		return saturation;
	}

	public double getLightness() {

		return lightness;
	}

	public int getRgbValue() {

		return getRgbColor().getRgbValue();
	}

	public RgbColor getRgbColor() {

		double h2 = hue / 60;
		double c = (1 - Math.abs(2 * lightness - 1)) * saturation;
		double x = c * (1 - Math.abs(h2 % 2 - 1));
		double m = lightness - c / 2;

		if (h2 < 1) {
			return getRgbColor(m, c, x, 0);
		} else if (h2 < 2) {
			return getRgbColor(m, x, c, 0);
		} else if (h2 < 3) {
			return getRgbColor(m, 0, c, x);
		} else if (h2 < 4) {
			return getRgbColor(m, 0, x, c);
		} else if (h2 < 5) {
			return getRgbColor(m, x, 0, c);
		} else {
			return getRgbColor(m, c, 0, x);
		}
	}

	@Override
	public String toHtml() {

		int h = (int) Math.round(hue);
		int s = (int) Math.round(saturation * 100);
		int l = (int) Math.round(lightness * 100);
		if (alpha == 1.0) {
			return String.format("hsl(%s,%s%%,%s%%)", h, s, l);
		} else {
			return String.format("hsla(%s,%s%%,%s%%,%s)", h, s, l, alpha);
		}
	}

	// ------------------------------ private ------------------------------ //

	private RgbColor getRgbColor(double m, double r1, double g1, double b1) {

		return new RgbColor(r1 + m, g1 + m, b1 + m);
	}

	private static double computeHue(double M, double C, double red, double green, double blue) {

		if (C == 0) {
			return 0;
		} else if (M == red) {
			return 60 * mod((green - blue) / C, 6);
		} else if (M == green) {
			return 60 * ((blue - red) / C + 2);
		} else {
			return 60 * ((red - green) / C + 4);
		}
	}

	private static double computeLightness(double M, double m) {

		return (M + m) / 2;
	}

	private static double computeSaturation(double C, double lightness) {

		double divisor = 1 - Math.abs(2 * lightness - 1);
		return divisor != 0? C / divisor : 0;
	}

	private static double maxValue(double red, double green, double blue) {

		return Math.max(red, Math.max(green, blue));
	}

	private static double minValue(double red, double green, double blue) {

		return Math.min(red, Math.min(green, blue));
	}

	private static double mod(double value, double divisor) {

		double result = value % divisor;
		return result >= 0? result : result + divisor;
	}
}

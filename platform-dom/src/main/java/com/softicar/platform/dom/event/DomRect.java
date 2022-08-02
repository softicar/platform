package com.softicar.platform.dom.event;

import java.util.Objects;

/**
 * A <i>DOMRect</i> as defined by the W3C standard.
 *
 * @author Oliver Richers
 */
public class DomRect {

	private final double x;
	private final double y;
	private final double width;
	private final double height;

	public DomRect() {

		this(0, 0, 0, 0);
	}

	public DomRect(double x, double y, double width, double height) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}

	public double getWidth() {

		return width;
	}

	public double getHeight() {

		return height;
	}

	@Override
	public int hashCode() {

		return Objects.hash(x, y, width, height);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof DomRect) {
			DomRect other = (DomRect) object;
			return equals(x, other.x) && equals(y, other.y) && equals(width, other.width) && equals(height, other.height);
		} else {
			return false;
		}
	}

	private static boolean equals(double a, double b) {

		return Math.abs(a - b) < 0.001;
	}
}

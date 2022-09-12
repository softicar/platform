package com.softicar.platform.dom.event;

/**
 * An immutable 2-dimensional vector.
 *
 * @author Oliver Richers
 */
public class DomVector2d {

	private final double x;
	private final double y;

	public DomVector2d() {

		this(0, 0);
	}

	public DomVector2d(double x, double y) {

		this.x = x;
		this.y = y;
	}

	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}
}

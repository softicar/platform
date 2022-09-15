package com.softicar.platform.dom.event;

import java.util.Objects;

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

	@Override
	public int hashCode() {

		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof DomVector2d) {
			var other = (DomVector2d) object;
			return x == other.x && y == other.y;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {

		return "[%s,%s]".formatted(x, y);
	}
}

package com.softicar.platform.dom.event;

import java.util.Objects;

/**
 * An immutable 3-dimensional vector.
 *
 * @author Oliver Richers
 */
public class DomVector3d {

	private final double x;
	private final double y;
	private final double z;

	public DomVector3d() {

		this(0, 0, 0);
	}

	public DomVector3d(double x, double y, double z) {

		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}

	public double getZ() {

		return z;
	}

	@Override
	public int hashCode() {

		return Objects.hash(x, y, z);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof DomVector3d) {
			var other = (DomVector3d) object;
			return x == other.x && y == other.y && z == other.z;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {

		return "[%s,%s,%s]".formatted(x, y, z);
	}
}

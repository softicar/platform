package com.softicar.platform.ajax.testing.selenium.engine.common.geometry;

import java.util.Objects;

/**
 * A simple data container class that represents a point in two-dimensional
 * space.
 *
 * @author Alexander Schmidt
 */
public class AjaxSeleniumTestPoint {

	private final int x;
	private final int y;

	/**
	 * Constructs a new {@link AjaxSeleniumTestPoint} with the given coordinates.
	 *
	 * @param x
	 *            the X coordinate
	 * @param y
	 *            the Y coordinate
	 */
	public AjaxSeleniumTestPoint(int x, int y) {

		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the X coordinate of this {@link AjaxSeleniumTestPoint}.
	 *
	 * @return the X coordinate
	 */
	public int getX() {

		return x;
	}

	/**
	 * Returns the Y coordinate of this {@link AjaxSeleniumTestPoint}.
	 *
	 * @return the Y coordinate
	 */
	public int getY() {

		return y;
	}

	/**
	 * Creates a copy of this {@link AjaxSeleniumTestPoint}, moved by the specified
	 * offsets.
	 *
	 * @param xOffset
	 *            the horizontal offset
	 * @param yOffset
	 *            the vertical offset
	 * @return the new {@link AjaxSeleniumTestPoint} (never <i>null</i>)
	 */
	public AjaxSeleniumTestPoint moveBy(int xOffset, int yOffset) {

		return new AjaxSeleniumTestPoint(x + xOffset, y + yOffset);
	}

	/**
	 * Determines whether this {@link AjaxSeleniumTestPoint} is equal to the given
	 * {@link Object}.
	 * <p>
	 * If the given object is a {@link AjaxSeleniumTestPoint}, and if the respective X
	 * and Y coordinates are identical, <i>true</i> is returned.
	 * <p>
	 * Otherwise, <i>false</i> is returned.
	 *
	 * @return <i>true</i> if this {@link AjaxSeleniumTestPoint} is equal to the given
	 *         one; <i>false</i> otherwise
	 */
	@Override
	public boolean equals(Object other) {

		if (other instanceof AjaxSeleniumTestPoint) {
			AjaxSeleniumTestPoint otherPoint = (AjaxSeleniumTestPoint) other;
			return x == otherPoint.x && y == otherPoint.y;
		} else {
			return false;
		}
	}

	/**
	 * Returns the hash code of this {@link AjaxSeleniumTestPoint}.
	 *
	 * @return the hash code
	 */
	@Override
	public int hashCode() {

		return Objects.hash(x, y);
	}
}

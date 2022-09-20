package com.softicar.platform.ajax.testing.selenium.engine.common.geometry;

import java.util.Objects;

/**
 * A simple data container class that represents a rectangular region in
 * two-dimensional space.
 *
 * @author Alexander Schmidt
 */
public class AjaxSeleniumTestRectangle {

	private final AjaxSeleniumTestPoint location;
	private final AjaxSeleniumTestArea area;

	/**
	 * Constructs a new {@link AjaxSeleniumTestRectangle} with the given geometry.
	 *
	 * @param x
	 *            the X coordinate of the top-left corner
	 * @param y
	 *            the Y coordinate of the top-left corner
	 * @param width
	 *            the horizontal size of the rectangle
	 * @param height
	 *            the vertical size of the rectangle
	 */
	public AjaxSeleniumTestRectangle(int x, int y, int width, int height) {

		this(new AjaxSeleniumTestPoint(x, y), new AjaxSeleniumTestArea(width, height));
	}

	/**
	 * Constructs a new {@link AjaxSeleniumTestRectangle} with the given geometry.
	 *
	 * @param location
	 *            the top-left corner as a {@link AjaxSeleniumTestPoint}
	 * @param area
	 *            the horizontal and vertical size as a {@link AjaxSeleniumTestArea}
	 */
	public AjaxSeleniumTestRectangle(AjaxSeleniumTestPoint location, AjaxSeleniumTestArea area) {

		this.location = location;
		this.area = area;
	}

	/**
	 * Returns the X coordinate of top-left corner of this
	 * {@link AjaxSeleniumTestRectangle}.
	 *
	 * @return the X coordinate
	 */
	public int getX() {

		return location.getX();
	}

	/**
	 * Returns the Y coordinate of top-left corner. of this
	 * {@link AjaxSeleniumTestRectangle}
	 *
	 * @return the Y coordinate
	 */
	public int getY() {

		return location.getY();
	}

	/**
	 * Returns the horizontal size of this {@link AjaxSeleniumTestRectangle}.
	 *
	 * @return the width
	 */
	public int getWidth() {

		return area.getWidth();
	}

	/**
	 * Returns the vertical size of this {@link AjaxSeleniumTestRectangle}.
	 *
	 * @return the vertical
	 */
	public int getHeight() {

		return area.getHeight();
	}

	/**
	 * Creates a copy of this {@link AjaxSeleniumTestRectangle}, with the same horizontal
	 * and vertical size, moved by the specified offsets.
	 *
	 * @param xOffset
	 *            the horizontal offset
	 * @param yOffset
	 *            the vertical offset
	 * @return the new {@link AjaxSeleniumTestRectangle} (never <i>null</i>)
	 */
	public AjaxSeleniumTestRectangle moveBy(int xOffset, int yOffset) {

		return new AjaxSeleniumTestRectangle(getLocation().moveBy(xOffset, yOffset), getSize());
	}

	/**
	 * Returns the coordinates of the top-left corner of this
	 * {@link AjaxSeleniumTestRectangle}, as a {@link AjaxSeleniumTestPoint}.
	 *
	 * @return the coordinates of this {@link AjaxSeleniumTestRectangle} (never
	 *         <i>null</i>)
	 */
	public AjaxSeleniumTestPoint getLocation() {

		return location;
	}

	/**
	 * The size of this {@link AjaxSeleniumTestRectangle}, as a {@link AjaxSeleniumTestArea}.
	 *
	 * @return the size of this {@link AjaxSeleniumTestRectangle} (never <i>null</i>)
	 */
	public AjaxSeleniumTestArea getSize() {

		return area;
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof AjaxSeleniumTestRectangle) {
			AjaxSeleniumTestRectangle otherRectangle = (AjaxSeleniumTestRectangle) other;
			return getLocation().equals(otherRectangle.getLocation()) && getSize().equals(((AjaxSeleniumTestRectangle) other).getSize());
		} else {
			return false;
		}
	}

	/**
	 * Returns the hash code of this {@link AjaxSeleniumTestRectangle}.
	 *
	 * @return the hash code
	 */
	@Override
	public int hashCode() {

		return Objects.hash(location, area);
	}
}

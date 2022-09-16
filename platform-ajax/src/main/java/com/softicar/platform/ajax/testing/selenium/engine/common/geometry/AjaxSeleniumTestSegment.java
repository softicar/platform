package com.softicar.platform.ajax.testing.selenium.engine.common.geometry;

import java.util.Objects;

/**
 * A simple data container class that represents a line segment in
 * two-dimensional space.
 *
 * @author Alexander Schmidt
 */
public class AjaxSeleniumTestSegment {

	private final int width;
	private final int height;

	/**
	 * Constructs a new {@link AjaxSeleniumTestSegment} with the size.
	 *
	 * @param width
	 *            the horizontal size of the segment
	 * @param height
	 *            the vertical size of the segment
	 */
	public AjaxSeleniumTestSegment(int width, int height) {

		this.width = width;
		this.height = height;
	}

	/**
	 * Returns the horizontal size of this {@link AjaxSeleniumTestSegment}.
	 *
	 * @return the width
	 */
	public int getWidth() {

		return width;
	}

	/**
	 * Returns the vertical size of this {@link AjaxSeleniumTestSegment}.
	 *
	 * @return the height
	 */
	public int getHeight() {

		return height;
	}

	/**
	 * Determines whether this {@link AjaxSeleniumTestSegment} is equal to the
	 * given {@link Object}.
	 * <p>
	 * If the given object is a {@link AjaxSeleniumTestSegment}, and if the
	 * respective widths and heights are identical, <i>true</i> is returned.
	 * <p>
	 * Otherwise, <i>false</i> is returned.
	 *
	 * @return <i>true</i> if this {@link AjaxSeleniumTestSegment} is equal to
	 *         the given one; <i>false</i> otherwise
	 */
	@Override
	public boolean equals(Object other) {

		if (other instanceof AjaxSeleniumTestSegment) {
			AjaxSeleniumTestSegment otherSegment = (AjaxSeleniumTestSegment) other;
			return width == otherSegment.width && height == otherSegment.height;
		} else {
			return false;
		}
	}

	/**
	 * Returns the hash code of this {@link AjaxSeleniumTestSegment}.
	 *
	 * @return the hash code
	 */
	@Override
	public int hashCode() {

		return Objects.hash(width, height);
	}

	@Override
	public String toString() {

		return "AjaxSeleniumTestSegment [width=" + width + ", height=" + height + "]";
	}
}

package com.softicar.platform.ajax.testing.selenium.engine.common.geometry;

import java.util.Objects;

/**
 * A simple data container class that represents an area in two-dimensional
 * space.
 *
 * @author Alexander Schmidt
 */
public class AjaxSeleniumTestArea {

	private final int width;
	private final int height;

	/**
	 * Constructs a new {@link AjaxSeleniumTestArea} with the size.
	 *
	 * @param width
	 *            the horizontal size of the area
	 * @param height
	 *            the vertical size of the area
	 */
	public AjaxSeleniumTestArea(int width, int height) {

		this.width = width;
		this.height = height;
	}

	/**
	 * Returns the horizontal size of this {@link AjaxSeleniumTestArea}.
	 *
	 * @return the width
	 */
	public int getWidth() {

		return width;
	}

	/**
	 * Returns the vertical size of this {@link AjaxSeleniumTestArea}.
	 *
	 * @return the height
	 */
	public int getHeight() {

		return height;
	}

	/**
	 * Determines whether this {@link AjaxSeleniumTestArea} is equal to the
	 * given {@link Object}.
	 * <p>
	 * If the given object is a {@link AjaxSeleniumTestArea}, and if the
	 * respective widths and heights are identical, <i>true</i> is returned.
	 * <p>
	 * Otherwise, <i>false</i> is returned.
	 *
	 * @return <i>true</i> if this {@link AjaxSeleniumTestArea} is equal to the
	 *         given one; <i>false</i> otherwise
	 */
	@Override
	public boolean equals(Object other) {

		if (other instanceof AjaxSeleniumTestArea) {
			var otherArea = (AjaxSeleniumTestArea) other;
			return width == otherArea.width && height == otherArea.height;
		} else {
			return false;
		}
	}

	/**
	 * Returns the hash code of this {@link AjaxSeleniumTestArea}.
	 *
	 * @return the hash code
	 */
	@Override
	public int hashCode() {

		return Objects.hash(width, height);
	}
}

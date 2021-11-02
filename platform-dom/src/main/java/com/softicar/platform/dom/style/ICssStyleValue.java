package com.softicar.platform.dom.style;

/**
 * Represents a CSS style value.
 *
 * @author Oliver Richers
 */
public interface ICssStyleValue {

	/**
	 * Returns the value in a valid CSS format.
	 *
	 * @return the CSS value
	 */
	@Override
	String toString();
}

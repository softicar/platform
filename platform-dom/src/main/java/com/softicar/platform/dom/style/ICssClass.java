package com.softicar.platform.dom.style;

/**
 * Represents a class definition for cascading style sheets.
 *
 * @author Oliver Richers
 */
public interface ICssClass {

	/**
	 * Returns the name of this CSS class.
	 *
	 * @return class name, never null
	 */
	String getName();

	default void beforeUse() {

		// nothing to do by default
	}
}

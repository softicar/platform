package com.softicar.platform.dom.input;

import com.softicar.platform.dom.element.IDomElement;

/**
 * Simple interface for focusable dom nodes.
 *
 * @author Oliver Richers
 */
public interface IDomFocusable extends IDomElement {

	/**
	 * Assigns or removes the input focus on this dom node.
	 * <p>
	 * Please note that if you call this method on an element within a popup
	 * menu, you must call the show function of the popup menu before you call
	 * this method. This is, because only visible elements can get a focus.
	 *
	 * @param focus
	 *            true to focus, false to blur
	 */
	default IDomElement setFocus(boolean focus) {

		if (focus) {
			getDomEngine().focus(this);
		} else {
			getDomEngine().blur(this);
		}
		return this;
	}
}

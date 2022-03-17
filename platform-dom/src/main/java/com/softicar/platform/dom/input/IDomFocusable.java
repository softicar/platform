package com.softicar.platform.dom.input;

import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.popup.DomPopup;

/**
 * Interface to focus or blur {@link IDomElement} instances.
 *
 * @author Oliver Richers
 */
public interface IDomFocusable extends IDomElement {

	/**
	 * Assigns or removes the input focus on this {@link IDomElement}.
	 * <p>
	 * Please note that an {@link IDomElement} can only gain the focus if it is
	 * appended to the {@link DomDocument}. For example, if this method is
	 * called on an {@link IDomElement} appended to a {@link DomPopup}, the
	 * {@link DomPopup#show()} method must be called before this method is
	 * called. Otherwise, the call has no effect.
	 *
	 * @param focus
	 *            <i>true</i> to focus; <i>false</i> to blur
	 */
	default IDomElement setFocus(boolean focus) {

		if (focus) {
			getDomEngine().focus(this);
		} else {
			getDomEngine().blur(this);
		}
		return this;
	}

	/**
	 * Assigns the focus to this {@link IDomElement}.
	 * <p>
	 * Convenience method for {@link #setFocus(boolean)}.
	 */
	default void focus() {

		setFocus(true);
	}
}

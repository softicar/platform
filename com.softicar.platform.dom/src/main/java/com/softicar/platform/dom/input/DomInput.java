package com.softicar.platform.dom.input;

import com.softicar.platform.dom.element.DomElement;
import com.softicar.platform.dom.element.DomElementTag;

/**
 * Abstract base class of all simple input elements.
 *
 * @author Oliver Richers
 */
public abstract class DomInput extends DomElement implements IDomInputNode {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.INPUT;
	}

	/**
	 * Assigns the focus to this input field.
	 * <p>
	 * Convenience method for {@link #setFocus(boolean)}.
	 */
	public void focus() {

		setFocus(true);
	}

	/**
	 * Disables this input element.
	 * <p>
	 * Convenience method for {@link #setEnabled(boolean)}.
	 */
	public void disable() {

		setEnabled(false);
	}
}

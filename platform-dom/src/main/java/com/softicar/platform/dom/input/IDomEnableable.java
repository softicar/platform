package com.softicar.platform.dom.input;

import com.softicar.platform.dom.element.IDomElement;

/**
 * Interface to enable or disable {@link IDomElement} instances.
 *
 * @author Oliver Richers
 */
public interface IDomEnableable extends IDomElement {

	/**
	 * Enables or disables this {@link IDomElement}.
	 *
	 * @param enabled
	 *            <i>true</i> to enable; <i>false</i> to disable
	 */
	default IDomElement setEnabled(boolean enabled) {

		if (enabled) {
			setAttribute("disabled", null);
		} else {
			setAttribute("disabled", "disabled");
		}
		return this;
	}

	/**
	 * Disables this {@link IDomElement}.
	 * <p>
	 * Convenience method for {@link #setEnabled(boolean)}.
	 */
	default void disable() {

		setEnabled(false);
	}
}

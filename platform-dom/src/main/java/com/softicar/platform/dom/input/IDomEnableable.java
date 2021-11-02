package com.softicar.platform.dom.input;

import com.softicar.platform.dom.element.IDomElement;

/**
 * Simple interface for enableable and disableable dom elements.
 *
 * @author Oliver Richers
 */
public interface IDomEnableable extends IDomElement {

	/**
	 * Enables or disables this element.
	 *
	 * @param enabled
	 *            true to enable, false to disable
	 */
	default IDomElement setEnabled(boolean enabled) {

		if (enabled) {
			setAttribute("disabled", null);
		} else {
			setAttribute("disabled", "disabled");
		}
		return this;
	}
}

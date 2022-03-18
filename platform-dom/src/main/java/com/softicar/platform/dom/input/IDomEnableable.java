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
	 * @param disabled
	 *            <i>true</i> to disable; <i>false</i> to enable
	 */
	default IDomElement setDisabled(boolean disabled) {

		setAttribute("disabled", disabled? "" : null);
		return this;
	}

	/**
	 * Enables or disables this {@link IDomElement}.
	 *
	 * @param enabled
	 *            <i>true</i> to enable; <i>false</i> to disable
	 */
	default IDomElement setEnabled(boolean enabled) {

		return setDisabled(!enabled);
	}

	/**
	 * Tests whether this {@link IDomElement} is disabled.
	 *
	 * @return <i>true</i> if this {@link IDomElement} is disabled; <i>false</i>
	 *         otherwise
	 */
	default boolean isDisabled() {

		return getAttributeValue("disabled").isPresent();
	}
}

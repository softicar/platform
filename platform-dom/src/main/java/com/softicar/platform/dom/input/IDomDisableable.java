package com.softicar.platform.dom.input;

import com.softicar.platform.dom.element.IDomElement;

/**
 * Interface to enable or disable {@link IDomElement} instances.
 *
 * @author Oliver Richers
 */
public interface IDomDisableable extends IDomElement {

	/**
	 * Enables or disables this {@link IDomElement}.
	 *
	 * @param disabled
	 *            <i>true</i> to disable; <i>false</i> to enable
	 * @return this
	 */
	IDomDisableable setDisabled(boolean disabled);

	/**
	 * Tests whether this {@link IDomElement} is disabled.
	 *
	 * @return <i>true</i> if this {@link IDomElement} is disabled; <i>false</i>
	 *         otherwise
	 */
	boolean isDisabled();
}

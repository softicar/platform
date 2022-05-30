package com.softicar.platform.dom.input;

import com.softicar.platform.dom.element.IDomElement;

/**
 * Interface of input {@link IDomElement} instances.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IDomInput extends IDomElement {

	/**
	 * Enables or disables this {@link IDomElement}.
	 *
	 * @param disabled
	 *            <i>true</i> to disable; <i>false</i> to enable
	 * @return this
	 */
	IDomInput setDisabled(boolean disabled);

	/**
	 * Tests whether this {@link IDomElement} is disabled.
	 *
	 * @return <i>true</i> if this {@link IDomElement} is disabled; <i>false</i>
	 *         otherwise
	 */
	boolean isDisabled();

	/**
	 * Enables or disables this {@link IDomElement}.
	 *
	 * @param enabled
	 *            <i>true</i> to enabled; <i>false</i> to disable
	 * @return this
	 */
	IDomInput setEnabled(boolean enabled);

	/**
	 * Tests whether this {@link IDomElement} is enabled.
	 *
	 * @return <i>true</i> if this {@link IDomElement} is enabled; <i>false</i>
	 *         otherwise
	 */
	boolean isEnabled();
}

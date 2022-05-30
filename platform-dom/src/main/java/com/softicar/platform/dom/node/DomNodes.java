package com.softicar.platform.dom.node;

import com.softicar.platform.dom.input.IDomInput;

/**
 * Provides utility functions for {@link IDomNode} instances.
 * <p>
 * Only use this in framework code. Do not use it in payload code.
 *
 * @author Alexander Schmidt
 */
public class DomNodes {

	/**
	 * Enables or disables the given {@link IDomInput}.
	 *
	 * @param element
	 *            the {@link IDomInput} to enable or disable (never
	 *            <i>null</i>)
	 * @param disabled
	 *            <i>true</i> to disable; <i>false</i> to enable
	 * @return the given {@link IDomInput} (never <i>null</i>)
	 */
	public static <E extends IDomInput> E setDisabled(E element, boolean disabled) {

		element.setAttribute("disabled", disabled? "" : null);
		return element;
	}

	/**
	 * Tests whether the given {@link IDomInput} is disabled.
	 *
	 * @param element
	 *            the {@link IDomInput} to check (never <i>null</i>)
	 * @return <i>true</i> if the given {@link IDomInput} is disabled;
	 *         <i>false</i> otherwise
	 */
	public static boolean isDisabled(IDomInput element) {

		return element.getAttributeValue("disabled").isPresent();
	}
}

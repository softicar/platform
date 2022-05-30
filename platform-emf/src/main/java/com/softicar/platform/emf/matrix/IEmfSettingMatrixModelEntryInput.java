package com.softicar.platform.emf.matrix;

import com.softicar.platform.dom.element.IDomElement;
import java.util.Optional;

/**
 * An input element that is used in cells of the matrix in
 * {@link EmfSettingMatrixDiv}.
 *
 * @author Alexander Schmidt
 */
public interface IEmfSettingMatrixModelEntryInput<V> extends IDomElement {

	/**
	 * Returns the value of the input element.
	 *
	 * @return the value of the input element, if any
	 */
	Optional<V> getValue();

	/**
	 * Sets the value of the input element.
	 *
	 * @param value
	 *            the value of the input element (may be null)
	 */
	void setValue(V value);

	/**
	 * Sets the value of the input element.
	 *
	 * @param value
	 *            the value of the input element
	 */
	default void setValue(Optional<V> value) {

		setValue(value.orElse(null));
	}

	/**
	 * Specifies if the input element is disabled.
	 *
	 * @param disabled
	 *            <i>true</i> if the input element is disabled; <i>false</i>
	 *            otherwise
	 */
	void setInputDisabled(boolean disabled);
}

package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.utils.DevNull;
import java.util.Optional;

/**
 * An input element with a typed value.
 *
 * @author Oliver Richers
 */
public interface IDomValueInput<V> extends IDomInput {

	/**
	 * Assigns a value to this input element.
	 *
	 * @param value
	 *            the value to assign or <i>null</i>
	 */
	void setValue(V value);

	/**
	 * Returns the optional value of this input element, as follows:
	 * <ul>
	 * <li>If a valid value was entered, the value is returned.</li>
	 * <li>If no value is entered, {@link Optional#empty()} is returned.</li>
	 * <li>If an invalid value is entered, an exception is thrown.</li>
	 * </ul>
	 *
	 * @return the entered value as an {@link Optional}
	 * @throws DomInputException
	 *             if the user entered something into this input element which
	 *             does not represent a valid value
	 */
	Optional<V> getValue();

	/**
	 * Returns the value of this input element, as follows:
	 * <ul>
	 * <li>If a valid value was entered, the value is returned.</li>
	 * <li>If no value is entered, <i>null</i> is returned.</li>
	 * <li>If an invalid value is entered, an exception is thrown.</li>
	 * </ul>
	 *
	 * @return the entered value (may be <i>null</i>)
	 * @throws DomInputException
	 *             if the user entered something into this input element which
	 *             does not represent a valid value
	 */
	default V getValueOrNull() {

		return getValue().orElse(null);
	}

	/**
	 * Same as {@link #getValue()} but never throws an {@link Exception}.
	 * <p>
	 * Instead of throwing an exception, {@link Optional#empty()} is returned.
	 *
	 * @return the entered value as an {@link Optional} (never <i>null</i>)
	 */
	default Optional<V> getValueNoThrow() {

		try {
			return getValue();
		} catch (Exception exception) {
			DevNull.swallow(exception);
			return Optional.empty();
		}
	}
}

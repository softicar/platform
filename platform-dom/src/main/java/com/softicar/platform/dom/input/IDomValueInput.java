package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
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
	 * Assigns a value to this input element and calls all registered change
	 * callback functions.
	 * <p>
	 * The callback functions will be called in the order in which they where
	 * registered.
	 *
	 * @param value
	 *            the value to assign, or <i>null</i>
	 * @see #addChangeCallback(INullaryVoidFunction)
	 */
	void setValueAndHandleChangeCallback(V value);

	/**
	 * Returns the optional value of this input element, as follows:
	 * <ul>
	 * <li>If a valid value was entered, the value is returned.</li>
	 * <li>If no value was entered, {@link Optional#empty()} is returned.</li>
	 * <li>If an invalid value was entered, an {@link Exception} is thrown.</li>
	 * </ul>
	 *
	 * @return the entered value as an {@link Optional}
	 * @throws DomInputException
	 *             if the entered text does not represent a valid value
	 */
	Optional<V> getValue();

	/**
	 * Returns the value of this input element, as follows:
	 * <ul>
	 * <li>If a valid value was entered, the value is returned.</li>
	 * <li>If no value was entered, <i>null</i> is returned.</li>
	 * <li>If an invalid value was entered, an {@link Exception} is thrown.</li>
	 * </ul>
	 *
	 * @return the entered value (may be <i>null</i>)
	 * @throws DomInputException
	 *             if the entered text does not represent a valid value
	 */
	default V getValueOrNull() {

		return getValue().orElse(null);
	}

	/**
	 * Returns the value of this input element, as follows:
	 * <ul>
	 * <li>If a valid value was entered, the value is returned.</li>
	 * <li>If no value was entered, an {@link Exception} is thrown.</li>
	 * <li>If an invalid value was entered, an {@link Exception} is thrown.</li>
	 * </ul>
	 *
	 * @return the entered value (never <i>null</i>)
	 * @throws DomInputException
	 *             if this input element is empty, or if the entered text does
	 *             not represent a valid value
	 */
	default V getValueOrThrow() {

		return getValue().orElseThrow(() -> new DomInputException(DomI18n.MISSING_INPUT_VALUE));
	}

	/**
	 * Same as {@link #getValue()} but never throws an {@link Exception}.
	 * <p>
	 * Instead of throwing an {@link Exception}, {@link Optional#empty()} is
	 * returned.
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

	/**
	 * Registers the given callback to be executed when the value is changed.
	 * <p>
	 * All registered callback functions will be called when an
	 * {@link IDomEvent} of type {@link DomEventType#CHANGE} occurs, or by
	 * manually calling {@link #setValueAndHandleChangeCallback}.
	 * <p>
	 * All callback functions are called in the order in which they were
	 * registered.
	 *
	 * @param callback
	 *            the callback (never <i>null</i>)
	 */
	void addChangeCallback(INullaryVoidFunction callback);
}

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
	 * The callback functions will be called in the order that they where added.
	 *
	 * @param value
	 *            the value to assign or <i>null</i>
	 */
	void setValueAndHandleChangeCallback(V value);

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
	 * Returns the value of this input element, as follows:
	 * <ul>
	 * <li>If a valid value was entered, the value is returned.</li>
	 * <li>If no value is entered, an exception is thrown.</li>
	 * <li>If an invalid value is entered, an exception is thrown.</li>
	 * </ul>
	 *
	 * @return the entered value (never <i>null</i>)
	 * @throws DomInputException
	 *             if the user entered an invalid value or no value at all
	 */
	default V getValueOrThrow() {

		return getValue().orElseThrow(() -> new DomInputException(DomI18n.MISSING_INPUT_VALUE));
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

	/**
	 * Registers the given callback to be notified when the value changes.
	 * <p>
	 * All registered callback functions will be called when a {@link IDomEvent}
	 * of type {@link DomEventType#CHANGE} occurs, or by manually calling
	 * {@link #setValueAndHandleChangeCallback}. All callback functions will
	 * then be called in the order that they were added.
	 *
	 * @param callback
	 *            the callback (never <i>null</i>)
	 */
	void addChangeCallback(INullaryVoidFunction callback);
}

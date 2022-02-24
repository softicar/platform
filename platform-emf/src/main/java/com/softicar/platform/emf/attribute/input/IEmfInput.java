package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.input.DomInputException;
import com.softicar.platform.dom.input.IDomValueBasedInputNode;
import java.util.Optional;

public interface IEmfInput<V> extends IDomValueBasedInputNode<V>, IEmfChangeCallbackProvider {

	/**
	 * Returns the value of this input element, as follows:
	 * <ul>
	 * <li>If a valid value is entered, the value is returned.</li>
	 * <li>If no value is entered, <i>null</i> is returned.</li>
	 * <li>If an invalid value is entered, an exception is thrown.</li>
	 * </ul>
	 *
	 * @return the entered value (may be null)
	 * @throws DomInputException
	 */
	V getValueOrThrow() throws DomInputException;

	/**
	 * Returns the current value as an {@link Optional}, as follows:
	 * <ul>
	 * <li>If a valid value is entered, the value is returned.</li>
	 * <li>If no value is entered, {@link Optional#empty()} is returned.</li>
	 * <li>If an invalid value is entered, an exception is thrown.</li>
	 * </ul>
	 *
	 * @return the entered value as an {@link Optional} (never null)
	 */
	default Optional<V> getValueAsOptional() throws DomInputException {

		return Optional.ofNullable(getValueOrThrow());
	}

	/**
	 * Defines the given callback to be notified when the value changes.
	 * <p>
	 * This method is called by the entity framework, don't call it directly.
	 *
	 * @param callback
	 *            the callback (never null)
	 */
	@Override
	default void setChangeCallback(INullaryVoidFunction callback) {

		DevNull.swallow(callback);
		// nothing to do by default
	}

	/**
	 * Flags this input as either mandatory or optional.
	 *
	 * @param mandatory
	 *            true if this input is mandatory. false otherwise.
	 */
	default void setMandatory(boolean mandatory) {

		DevNull.swallow(mandatory);
		// nothing to do by default
	}

	/**
	 * This method is called when the value of this or another input has
	 * changed.
	 * <p>
	 * Customizing this methods is an opportunity for this input element to
	 * recompute its input constraints.
	 * <p>
	 * This method is called by the entity framework, don't call it directly.
	 */
	default void refreshInputConstraints() {

		// nothing to do by default
	}

	/**
	 * This hook is executed right after updating the entity and before the
	 * final transaction commit.
	 */
	default void executePostSaveHook() {

		// nothing to do by default
	}
}

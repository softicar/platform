package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.input.IDomValueInput;

public interface IEmfInput<V> extends IDomValueInput<V> {

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

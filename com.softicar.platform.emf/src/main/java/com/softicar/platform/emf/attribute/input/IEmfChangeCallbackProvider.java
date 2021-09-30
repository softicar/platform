package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;

public interface IEmfChangeCallbackProvider {

	/**
	 * Defines the given callback to be notified when the value changes.
	 * <p>
	 * This method is called by the entity framework, don't call it directly.
	 *
	 * @param callback
	 *            the callback (never <i>null</i>)
	 */
	void setChangeCallback(INullaryVoidFunction callback);
}

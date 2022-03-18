package com.softicar.platform.common.core.callback;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import java.util.ArrayList;
import java.util.List;

/**
 * Composes a list of {@link INullaryVoidFunction} into a new
 * {@link INullaryVoidFunction}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class CallbackBuilder {

	private final List<INullaryVoidFunction> callbacks = new ArrayList<>();

	public CallbackBuilder add(INullaryVoidFunction callback) {

		if (callback != null && !callbacks.contains(callback)) {
			callbacks.add(callback);
		}
		return this;
	}

	public INullaryVoidFunction build() {

		return () -> callbacks.forEach(callback -> callback.apply());
	}
}

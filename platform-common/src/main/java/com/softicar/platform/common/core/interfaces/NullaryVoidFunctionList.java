package com.softicar.platform.common.core.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Aggregates multiple {@link INullaryVoidFunction} objects into a list, that
 * itself implements {@link INullaryVoidFunction}.
 *
 * @author Oliver Richers
 */
public class NullaryVoidFunctionList implements INullaryVoidFunction {

	private final Collection<INullaryVoidFunction> functions;

	public NullaryVoidFunctionList() {

		this.functions = new ArrayList<>();
	}

	/**
	 * Calls all contained {@link INullaryVoidFunction} objects in the order
	 * that they were added.
	 */
	@Override
	public void apply() {

		for (var function: functions) {
			function.apply();
		}
	}

	public NullaryVoidFunctionList add(INullaryVoidFunction function) {

		functions.add(Objects.requireNonNull(function));
		return this;
	}

	public NullaryVoidFunctionList addAll(NullaryVoidFunctionList other) {

		functions.addAll(other.functions);
		return this;
	}
}

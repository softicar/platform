package com.softicar.platform.common.core.interfaces;

/**
 * Functional interface for functions taking no parameters and returning
 * <i>void</i>.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface INullaryVoidFunction {

	/**
	 * Returns a default implementation that does nothing.
	 */
	static INullaryVoidFunction noOperation() {

		return NO_OPERATION;
	}

	/**
	 * A default implementation that does nothing.
	 */
	INullaryVoidFunction NO_OPERATION = () -> {
		// nothing to do by definition
	};

	void apply();
}

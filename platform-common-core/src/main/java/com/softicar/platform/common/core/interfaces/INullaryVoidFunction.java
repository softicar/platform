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
	 * A default implementation that does nothing.
	 */
	INullaryVoidFunction NO_OPERATION = () -> {
		// nothing to do by definition
	};

	void apply();
}

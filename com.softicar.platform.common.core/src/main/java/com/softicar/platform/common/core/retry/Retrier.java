package com.softicar.platform.common.core.retry;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;

/**
 * Utility class to retry the execution of a {@link INullaryVoidFunction}.
 *
 * @author Oliver Richers
 */
public class Retrier extends AbstractRetrier<Retrier> implements INullaryVoidFunction {

	private final INullaryVoidFunction function;

	public Retrier(INullaryVoidFunction function) {

		this.function = function;
	}

	/**
	 * Starts the execution of the retry loop.
	 */
	@Override
	public void apply() {

		super.executeRetryLoop();
	}

	@Override
	protected void executeTry() {

		function.apply();
	}

	@Override
	protected Retrier getThis() {

		return this;
	}
}

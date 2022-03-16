package com.softicar.platform.common.core.retry;

import java.util.function.Supplier;

/**
 * Utility class to retry the execution of a {@link Supplier}.
 *
 * @author Oliver Richers
 */
public class RetryingSupplier<T> extends AbstractRetrier<RetryingSupplier<T>> implements Supplier<T> {

	private final Supplier<T> supplier;
	private T result;

	public RetryingSupplier(Supplier<T> supplier) {

		this.supplier = supplier;
	}

	/**
	 * Starts the execution of the retry loop and returns the result.
	 */
	@Override
	public T get() {

		executeRetryLoop();
		return result;
	}

	@Override
	protected void executeTry() {

		this.result = supplier.get();
	}

	@Override
	protected RetryingSupplier<T> getThis() {

		return this;
	}
}

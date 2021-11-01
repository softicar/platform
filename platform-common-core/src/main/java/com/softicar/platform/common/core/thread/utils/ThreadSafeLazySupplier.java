package com.softicar.platform.common.core.thread.utils;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A lazy supplier that is thread safe and detects cyclic calls.
 * <p>
 * This code is burrowed from the Guava library and extended with cycle
 * detection.
 *
 * @author Oliver Richers
 */
public class ThreadSafeLazySupplier<T> implements Supplier<T>, Serializable {

	private static final long serialVersionUID = 0;
	private final Supplier<T> actualSupplier;
	private transient volatile boolean initialized;
	private transient volatile boolean initializing;
	private transient T value;
	// "value" does not need to be volatile; visibility piggy-backs
	// on volatile read of "initialized".

	public ThreadSafeLazySupplier(Supplier<T> actualSupplier) {

		this.actualSupplier = Objects.requireNonNull(actualSupplier);
		this.initialized = false;
		this.initializing = false;
		this.value = null;
	}

	@Override
	public T get() {

		// A 2-field variant of Double Checked Locking.
		if (!initialized) {
			synchronized (this) {
				if (!initialized) {
					if (initializing) {
						throw new ThreadSafeLazySupplierException("Cyclic supplier call.");
					}

					this.initializing = true;
					T value = actualSupplier.get();
					this.initializing = false;

					this.value = value;
					this.initialized = true;
					return value;
				}
			}
		}
		return value;
	}
}

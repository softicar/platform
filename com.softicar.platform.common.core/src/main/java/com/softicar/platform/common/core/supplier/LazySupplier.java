package com.softicar.platform.common.core.supplier;

import java.util.Objects;
import java.util.function.Supplier;

public class LazySupplier<T> implements Supplier<T> {

	private final Supplier<T> supplier;
	private T value;

	public LazySupplier(Supplier<T> supplier) {

		this.supplier = Objects.requireNonNull(supplier);
		this.value = null;
	}

	@Override
	public T get() {

		if (value == null) {
			this.value = supplier.get();
		}
		return value;
	}
}

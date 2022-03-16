package com.softicar.platform.common.core.utils.equals;

import java.util.Objects;
import java.util.function.Function;

class EqualsKeyExtractor<T, K> extends EqualsComparerBase<T> {

	private final Function<? super T, K> keyExtractor;

	public EqualsKeyExtractor(Function<? super T, K> keyExtractor) {

		this.keyExtractor = Objects.requireNonNull(keyExtractor);
	}

	@Override
	public boolean compare(T first, T second) {

		if (first == second) {
			return true;
		} else if (first != null && second != null) {
			return Objects.equals(keyExtractor.apply(first), keyExtractor.apply(second));
		} else {
			return false;
		}
	}
}

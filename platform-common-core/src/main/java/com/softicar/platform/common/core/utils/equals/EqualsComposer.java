package com.softicar.platform.common.core.utils.equals;

import java.util.Objects;

class EqualsComposer<T> extends EqualsComparerBase<T> {

	private final IEqualsComparer<? super T> comparer1;
	private final IEqualsComparer<? super T> comparer2;

	public EqualsComposer(IEqualsComparer<? super T> comparer1, IEqualsComparer<? super T> comparer2) {

		this.comparer1 = Objects.requireNonNull(comparer1);
		this.comparer2 = Objects.requireNonNull(comparer2);
	}

	@Override
	public boolean compare(T first, T second) {

		return comparer1.compare(first, second) && comparer2.compare(first, second);
	}
}

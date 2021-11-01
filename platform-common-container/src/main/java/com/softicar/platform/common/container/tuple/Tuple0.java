package com.softicar.platform.common.container.tuple;

/**
 * This is the empty tuple.
 * 
 * @author Oliver Richers
 */
public final class Tuple0 extends AbstractTuple {

	public <T0> Tuple1<T0> plus(T0 element) {

		return new Tuple1<>(element);
	}

	@Override
	public int size() {

		return 0;
	}

	@Override
	protected Object getElement(int index) {

		throw new UnsupportedOperationException("Cannot access the elements of the empty tuple.");
	}
}

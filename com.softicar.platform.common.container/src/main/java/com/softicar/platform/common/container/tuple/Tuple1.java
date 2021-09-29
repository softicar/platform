package com.softicar.platform.common.container.tuple;

public final class Tuple1<T0> extends AbstractTuple {

	private T0 element0;

	public Tuple1(T0 e0) {

		this.element0 = e0;
	}

	public <T1> Tuple2<T0, T1> plus(T1 element) {

		return new Tuple2<>(element0, element);
	}

	@Override
	public int size() {

		return 1;
	}

	public Tuple0 subTuple0() {

		return new Tuple0();
	}

	public T0 get0() {

		return element0;
	}

	public void set0(T0 value) {

		this.element0 = value;
	}

	@Override
	protected Object getElement(int index) {

		switch (index) {
		case 0:
			return element0;
		default:
			throw new IndexOutOfBoundsException();
		}
	}
}

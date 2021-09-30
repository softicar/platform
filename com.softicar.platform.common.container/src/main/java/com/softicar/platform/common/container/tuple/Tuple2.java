package com.softicar.platform.common.container.tuple;

public final class Tuple2<T0, T1> extends AbstractTuple {

	private T0 element0;
	private T1 element1;

	public Tuple2(T0 e0, T1 e1) {

		this.element0 = e0;
		this.element1 = e1;
	}

	public Tuple2(Tuple1<T0> tuple, T1 element) {

		this.element0 = tuple.get0();
		this.element1 = element;
	}

	public <T2> Tuple3<T0, T1, T2> plus(T2 element) {

		return new Tuple3<>(element0, element1, element);
	}

	@Override
	public int size() {

		return 2;
	}

	public Tuple0 subTuple0() {

		return new Tuple0();
	}

	public Tuple1<T0> subTuple1() {

		return new Tuple1<>(element0);
	}

	public T0 get0() {

		return element0;
	}

	public T1 get1() {

		return element1;
	}

	public void set0(T0 value) {

		this.element0 = value;
	}

	public void set1(T1 value) {

		this.element1 = value;
	}

	@Override
	protected Object getElement(int index) {

		switch (index) {
		case 0:
			return element0;
		case 1:
			return element1;
		default:
			throw new IndexOutOfBoundsException();
		}
	}
}

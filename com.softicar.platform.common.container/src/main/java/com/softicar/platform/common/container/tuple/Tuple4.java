package com.softicar.platform.common.container.tuple;

public final class Tuple4<T0, T1, T2, T3> extends AbstractTuple {

	private T0 element0;
	private T1 element1;
	private T2 element2;
	private T3 element3;

	public Tuple4(T0 e0, T1 e1, T2 e2, T3 e3) {

		this.element0 = e0;
		this.element1 = e1;
		this.element2 = e2;
		this.element3 = e3;
	}

	public Tuple4(Tuple3<T0, T1, T2> tuple, T3 element) {

		this.element0 = tuple.get0();
		this.element1 = tuple.get1();
		this.element2 = tuple.get2();
		this.element3 = element;
	}

	public <T4> Tuple5<T0, T1, T2, T3, T4> plus(T4 element) {

		return new Tuple5<>(element0, element1, element2, element3, element);
	}

	@Override
	public int size() {

		return 4;
	}

	public Tuple0 subTuple0() {

		return new Tuple0();
	}

	public Tuple1<T0> subTuple1() {

		return new Tuple1<>(element0);
	}

	public Tuple2<T0, T1> subTuple2() {

		return new Tuple2<>(element0, element1);
	}

	public Tuple3<T0, T1, T2> subTuple3() {

		return new Tuple3<>(element0, element1, element2);
	}

	public T0 get0() {

		return element0;
	}

	public T1 get1() {

		return element1;
	}

	public T2 get2() {

		return element2;
	}

	public T3 get3() {

		return element3;
	}

	public void set0(T0 value) {

		this.element0 = value;
	}

	public void set1(T1 value) {

		this.element1 = value;
	}

	public void set2(T2 value) {

		this.element2 = value;
	}

	public void set3(T3 value) {

		this.element3 = value;
	}

	@Override
	protected Object getElement(int index) {

		switch (index) {
		case 0:
			return element0;
		case 1:
			return element1;
		case 2:
			return element2;
		case 3:
			return element3;
		default:
			throw new IndexOutOfBoundsException();
		}
	}
}

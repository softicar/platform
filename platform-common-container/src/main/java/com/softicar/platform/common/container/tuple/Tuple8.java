package com.softicar.platform.common.container.tuple;

public final class Tuple8<T0, T1, T2, T3, T4, T5, T6, T7> extends AbstractTuple {

	private T0 element0;
	private T1 element1;
	private T2 element2;
	private T3 element3;
	private T4 element4;
	private T5 element5;
	private T6 element6;
	private T7 element7;

	public Tuple8(T0 e0, T1 e1, T2 e2, T3 e3, T4 e4, T5 e5, T6 e6, T7 e7) {

		this.element0 = e0;
		this.element1 = e1;
		this.element2 = e2;
		this.element3 = e3;
		this.element4 = e4;
		this.element5 = e5;
		this.element6 = e6;
		this.element7 = e7;
	}

	public Tuple8(Tuple7<T0, T1, T2, T3, T4, T5, T6> tuple, T7 element) {

		this.element0 = tuple.get0();
		this.element1 = tuple.get1();
		this.element2 = tuple.get2();
		this.element3 = tuple.get3();
		this.element4 = tuple.get4();
		this.element5 = tuple.get5();
		this.element6 = tuple.get6();
		this.element7 = element;
	}

	public <T8> Tuple9<T0, T1, T2, T3, T4, T5, T6, T7, T8> plus(T8 element) {

		return new Tuple9<>(element0, element1, element2, element3, element4, element5, element6, element7, element);
	}

	@Override
	public int size() {

		return 8;
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

	public Tuple4<T0, T1, T2, T3> subTuple4() {

		return new Tuple4<>(element0, element1, element2, element3);
	}

	public Tuple5<T0, T1, T2, T3, T4> subTuple5() {

		return new Tuple5<>(element0, element1, element2, element3, element4);
	}

	public Tuple6<T0, T1, T2, T3, T4, T5> subTuple6() {

		return new Tuple6<>(element0, element1, element2, element3, element4, element5);
	}

	public Tuple7<T0, T1, T2, T3, T4, T5, T6> subTuple7() {

		return new Tuple7<>(element0, element1, element2, element3, element4, element5, element6);
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

	public T4 get4() {

		return element4;
	}

	public T5 get5() {

		return element5;
	}

	public T6 get6() {

		return element6;
	}

	public T7 get7() {

		return element7;
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

	public void set4(T4 value) {

		this.element4 = value;
	}

	public void set5(T5 value) {

		this.element5 = value;
	}

	public void set6(T6 value) {

		this.element6 = value;
	}

	public void set7(T7 value) {

		this.element7 = value;
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
		case 4:
			return element4;
		case 5:
			return element5;
		case 6:
			return element6;
		case 7:
			return element7;
		default:
			throw new IndexOutOfBoundsException();
		}
	}
}

package com.softicar.platform.common.container.array;

/**
 * An adapter for integer arrays with the difference that this adapter can
 * simulate null values by treating Integer.MIN_VALUE as null value.
 * 
 * @author Oliver Richers
 */
public class IntegerArrayAdapter extends IntArrayAdapter {

	protected IntegerArrayAdapter(int[] array) {

		super(array);
	}

	public static Integer getValue(int[] array, int index) {

		int tmp = array[index];
		return tmp == 0? null : Integer.MIN_VALUE + tmp;
	}

	public static void setValue(int[] array, int index, Integer value) {

		array[index] = value == null? 0 : value - Integer.MIN_VALUE;
	}

	public static IntegerArrayAdapter get(int[] array) {

		return new IntegerArrayAdapter(array);
	}

	@Override
	public Integer get(int index) {

		return getValue(array, index);
	}
}

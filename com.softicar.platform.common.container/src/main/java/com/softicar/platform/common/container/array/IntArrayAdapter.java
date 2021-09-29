package com.softicar.platform.common.container.array;

import java.util.AbstractList;
import java.util.Iterator;

/**
 * An adapter for integer arrays.
 * 
 * @author Oliver Richers
 */
public class IntArrayAdapter extends AbstractList<Integer> {

	protected int[] array;

	protected IntArrayAdapter(int[] array) {

		this.array = array;
	}

	public static IntArrayAdapter get(int[] array) {

		return new IntArrayAdapter(array);
	}

	@Override
	public Integer get(int index) {

		return array[index];
	}

	@Override
	public int size() {

		return array.length;
	}

	@Override
	public Iterator<Integer> iterator() {

		return new IntArrayIterator();
	}

	protected class IntArrayIterator implements Iterator<Integer> {

		int index = 0;

		@Override
		public boolean hasNext() {

			return index < array.length;
		}

		@Override
		public Integer next() {

			return get(index++);
		}

		@Override
		public void remove() {

			throw new UnsupportedOperationException();
		}
	}
}

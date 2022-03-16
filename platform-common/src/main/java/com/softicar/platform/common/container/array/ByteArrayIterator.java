package com.softicar.platform.common.container.array;

import java.util.Iterator;

public class ByteArrayIterator implements Iterator<Byte> {

	private final byte[] array;
	private int index;

	public ByteArrayIterator(byte[] array) {

		this.array = array;
		this.index = 0;
	}

	@Override
	public boolean hasNext() {

		return index < array.length;
	}

	@Override
	public Byte next() {

		return array[index++];
	}

	@Override
	public void remove() {

		throw new UnsupportedOperationException("Remove operation not allowed on ByteArrayIterator.");
	}
}

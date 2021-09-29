package com.softicar.platform.common.container.array;

import java.util.Iterator;

public class IterableByteArray implements Iterable<Byte> {

	private final byte[] array;

	public IterableByteArray(byte[] array) {

		this.array = array;
	}

	@Override
	public Iterator<Byte> iterator() {

		return new ByteArrayIterator(array);
	}
}

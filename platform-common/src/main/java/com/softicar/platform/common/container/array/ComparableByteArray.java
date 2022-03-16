package com.softicar.platform.common.container.array;

import com.softicar.platform.common.container.comparing.ContainerComparing;

/**
 * This adapter makes a byte array comparable to other byte arrays.
 *
 * @author Oliver Richers
 */
public class ComparableByteArray implements Comparable<ComparableByteArray> {

	private final byte[] array;

	public ComparableByteArray(byte[] array) {

		this.array = array;
	}

	public static ComparableByteArray create(byte[] array) {

		return new ComparableByteArray(array);
	}

	public byte[] get() {

		return array;
	}

	@Override
	public int compareTo(ComparableByteArray other) {

		return ContainerComparing.compare(array, other.array);
	}
}

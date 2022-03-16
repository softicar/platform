package com.softicar.platform.common.core.immutable;

/**
 * Base class for long wrapper classes.
 * <p>
 * Same as {@link ImmutableStringWrapper} for longs.
 * 
 * @author Oliver Richers
 */
public class ImmutableLongWrapper implements Comparable<ImmutableLongWrapper> {

	private final long value;

	protected ImmutableLongWrapper(long value) {

		this.value = value;
	}

	public long toLong() {

		return value;
	}

	@Override
	public String toString() {

		return "" + value;
	}

	@Override
	public int hashCode() {

		return (int) (value ^ (value >>> 32));
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof ImmutableLongWrapper) {
			return value == ((ImmutableLongWrapper) other).value;
		}

		return false;
	}

	@Override
	public int compareTo(ImmutableLongWrapper other) {

		if (value < other.value) {
			return -1;
		}
		if (value > other.value) {
			return 1;
		}
		return 0;
	}
}

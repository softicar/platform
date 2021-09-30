package com.softicar.platform.common.core.immutable;

/**
 * Base class for integer wrapper classes.
 * <p>
 * Same as {@link ImmutableStringWrapper} for integers.
 * 
 * @author Oliver Richers
 */
public class ImmutableIntWrapper implements Comparable<ImmutableIntWrapper> {

	private final int value;

	protected ImmutableIntWrapper(int value) {

		this.value = value;
	}

	public int toInt() {

		return value;
	}

	@Override
	public String toString() {

		return "" + value;
	}

	@Override
	public int hashCode() {

		return value;
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof ImmutableIntWrapper) {
			return value == ((ImmutableIntWrapper) other).value;
		}

		return false;
	}

	@Override
	public int compareTo(ImmutableIntWrapper other) {

		return value - other.value;
	}
}

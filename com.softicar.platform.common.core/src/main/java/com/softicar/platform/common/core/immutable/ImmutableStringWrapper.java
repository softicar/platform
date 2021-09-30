package com.softicar.platform.common.core.immutable;

/**
 * Base class for string wrapper classes.
 * <p>
 * For example, if you want to be sure that all article numbers are normalized,
 * derive a new class from this class to represent a valid article number.
 * 
 * @author Oliver Richers
 */
public class ImmutableStringWrapper implements Comparable<ImmutableStringWrapper> {

	private final String value;

	protected ImmutableStringWrapper(String value) {

		this.value = value;
	}

	public boolean isEmpty() {

		return value.isEmpty();
	}

	@Override
	public String toString() {

		return value;
	}

	@Override
	public int hashCode() {

		return value.hashCode();
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof ImmutableStringWrapper) {
			return value.equals(((ImmutableStringWrapper) other).value);
		} else if (other instanceof String) {
			return value.equals(other);
		}

		return false;
	}

	@Override
	public int compareTo(ImmutableStringWrapper other) {

		return value.compareTo(other.value);
	}
}

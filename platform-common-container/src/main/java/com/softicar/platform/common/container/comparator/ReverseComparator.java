package com.softicar.platform.common.container.comparator;

import java.util.Comparator;

/**
 * Reverses the order direction of a given comparator.
 *
 * @param <T>
 *            the type of the compared values
 * @author Oliver Richers
 */
public class ReverseComparator<T> implements Comparator<T> {

	private final Comparator<T> originalComparator;

	/**
	 * Creates a reverse comparator based on {@link Comparable}.
	 *
	 * @return new reverse comparator
	 */
	public static <T extends Comparable<T>> ReverseComparator<T> create() {

		return new ReverseComparator<>(T::compareTo);
	}

	public ReverseComparator(Comparator<T> originalComparator) {

		this.originalComparator = originalComparator;
	}

	@Override
	public int compare(T a, T b) {

		return originalComparator.compare(b, a);
	}
}

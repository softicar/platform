package com.softicar.platform.common.container.list;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Static factory methods for lists.
 * 
 * @author Oliver Richers
 */
public abstract class ListFactory {

	/**
	 * Creates a new and empty {@link ArrayList}.
	 */
	public static <T> ArrayList<T> createArrayList() {

		return new ArrayList<>();
	}

	/**
	 * Creates a new and empty {@link ArrayList} with the specified initial
	 * capacity.
	 * 
	 * @param initialCapacity
	 *            the initial capacity of the returned list
	 */
	public static <T> ArrayList<T> createArrayList(int initialCapacity) {

		return new ArrayList<>(initialCapacity);
	}

	/**
	 * Creates a new {@link ArrayList} and copies the elements of the specified
	 * collection.
	 * 
	 * @param other
	 *            the collection to copy the elements from
	 */
	public static <T> ArrayList<T> createArrayList(Collection<? extends T> other) {

		return new ArrayList<>(other);
	}
}

package com.softicar.platform.common.container.iterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utility methods for the {@link Iterable} interface.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class Iterables {

	/**
	 * Checks if the given {@link Iterable} is empty.
	 *
	 * @return true if iterable contains no elements
	 */
	public static boolean isEmpty(Iterable<?> iterable) {

		return !iterable.iterator().hasNext();
	}

	/**
	 * Returns the number of elements of the given {@link Iterable}.
	 * <p>
	 * If the dynamic type of the iterable implements {@link Collection}, this
	 * method casts the iterable and uses the {@link Collection#size()} method.
	 * Otherwise, all elements of the iterable are iterated and counted.
	 *
	 * @param iterable
	 *            the iterable object
	 * @return the number of elements
	 */
	public static int getSize(Iterable<?> iterable) {

		if (iterable instanceof Collection<?>) {
			return ((Collection<?>) iterable).size();
		} else {
			int size = 0;
			for (@SuppressWarnings("unused")
			Object dummy: iterable) {
				++size;
			}
			return size;
		}
	}

	/**
	 * Returns the first element of the given {@link Iterable}.
	 * <p>
	 * If the given {@link Iterable} is empty, <i>null</i> is returned.
	 *
	 * @param <T>
	 *            the element type
	 * @param iterable
	 *            the {@link Iterable} to obtain the first element from (never
	 *            <i>null</i>)
	 * @return the first element (may be <i>null</i>)
	 */
	public static <T> T getFirst(Iterable<T> iterable) {

		Iterator<T> iterator = iterable.iterator();
		return iterator.hasNext()? iterator.next() : null;
	}

	/**
	 * Iterates the given {@link Iterable}, and returns its elements in a
	 * {@link Collection}.
	 *
	 * @param <T>
	 *            the element type
	 * @param iterable
	 *            the {@link Iterable} to obtain the elements from (never
	 *            <i>null</i>)
	 * @return the given {@link Iterable} as a {@link Collection} (never
	 *         <i>null</i>)
	 */
	public static <T> Collection<T> toCollection(Iterable<T> iterable) {

		Collection<T> collection = new ArrayList<>();
		iterable.forEach(collection::add);
		return collection;
	}

	/**
	 * Iterates the given {@link Iterable}, and returns its elements in a
	 * {@link Stream}.
	 *
	 * @param <T>
	 *            the element type
	 * @param iterable
	 *            the {@link Iterable} to obtain the elements from (never
	 *            <i>null</i>)
	 * @return the given {@link Iterable} as a {@link Stream} (never
	 *         <i>null</i>)
	 */
	public static <T> Stream<T> toStream(Iterable<T> iterable) {

		return StreamSupport.stream(iterable.spliterator(), false);
	}
}

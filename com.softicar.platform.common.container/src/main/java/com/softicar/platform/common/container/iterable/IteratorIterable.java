package com.softicar.platform.common.container.iterable;

import com.softicar.platform.common.container.iterator.EnumerationIterator;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Adapter class that converts an {@link Iterator} or {@link Enumeration} into
 * an {@link Iterable}.
 * <p>
 * Instances of this class may only be traversed once.
 * 
 * @author Oliver Richers
 */
public class IteratorIterable<T> implements Iterable<T> {

	private final Iterator<T> iterator;
	private boolean used;

	public IteratorIterable(Iterator<T> iterator) {

		this.iterator = iterator;
		this.used = false;
	}

	/**
	 * Creates a new instance of this class, based on the specified
	 * {@link Iterator} .
	 * 
	 * @param iterator
	 *            the {@link Iterator} to convert into an {@link Iterable}
	 * @return new instance of {@link IteratorIterable}
	 */
	public static <S> IteratorIterable<S> create(Iterator<S> iterator) {

		return new IteratorIterable<>(iterator);
	}

	/**
	 * Creates a new instance of this class, based on the specified
	 * {@link Enumeration} .
	 * 
	 * @param enumeration
	 *            the {@link Enumeration} to convert into an {@link Iterable}
	 * @return new instance of {@link IteratorIterable}
	 */
	public static <S> IteratorIterable<S> create(Enumeration<S> enumeration) {

		return new IteratorIterable<>(new EnumerationIterator<>(enumeration));
	}

	/**
	 * Returns the iterator.
	 * <p>
	 * <b>This method may only be called once.</b>
	 * 
	 * @throws SofticarDeveloperException
	 *             if this method is called twice
	 */
	@Override
	public Iterator<T> iterator() {

		if (used) {
			throw new SofticarDeveloperException("This iterable may only be used once.");
		}

		used = true;
		return iterator;
	}
}

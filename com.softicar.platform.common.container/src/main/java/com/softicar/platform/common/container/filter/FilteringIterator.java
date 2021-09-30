package com.softicar.platform.common.container.filter;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * An iterator that filters the elements with respect to a given predicate.
 * <p>
 * If the sequence contains <i>null</i> elements the predicate must also support
 * <i>null</i> values.
 *
 * @param <T>
 *            the element type
 * @author Oliver Richers
 */
public class FilteringIterator<T> implements Iterator<T> {

	private final Iterator<? extends T> iterator;
	private final Predicate<T> predicate;
	private boolean haveElement;
	private T element;

	public FilteringIterator(Iterator<? extends T> iterator, Predicate<T> predicate) {

		this.iterator = iterator;
		this.predicate = predicate;
		this.haveElement = false;
		this.element = null;
	}

	@Override
	public boolean hasNext() {

		while (!haveElement && iterator.hasNext()) {
			this.element = iterator.next();
			if (predicate.test(element)) {
				this.haveElement = true;
			}
		}
		return haveElement;
	}

	@Override
	public T next() {

		if (hasNext()) {
			this.haveElement = false;
			return element;
		} else {
			throw new NoSuchElementException();
		}
	}
}

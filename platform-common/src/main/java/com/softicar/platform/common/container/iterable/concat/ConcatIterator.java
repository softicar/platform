package com.softicar.platform.common.container.iterable.concat;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An {@link Iterator} to concatenates multiple iterables.
 *
 * @author Oliver Richers
 * @see ConcatIterable
 */
public class ConcatIterator<T> implements Iterator<T> {

	private final Iterator<? extends Iterable<T>> iterablesIterator;
	private Iterator<T> currentIterator;

	public ConcatIterator(Iterable<? extends Iterable<T>> iterables) {

		this.iterablesIterator = iterables.iterator();
		this.currentIterator = null;
	}

	@Override
	public boolean hasNext() {

		if (currentIterator != null && currentIterator.hasNext()) {
			return true;
		}

		if (iterablesIterator.hasNext()) {
			this.currentIterator = iterablesIterator.next().iterator();
			return hasNext();
		}

		return false;
	}

	@Override
	public T next() {

		if (hasNext()) {
			return currentIterator.next();
		} else {
			throw new NoSuchElementException();
		}
	}
}

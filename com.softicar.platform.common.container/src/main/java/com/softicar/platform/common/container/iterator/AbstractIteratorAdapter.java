package com.softicar.platform.common.container.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Abstract class to simplify the implementation of {@link Iterator} and
 * {@link Iterable}.
 * <p>
 * The derived class only has to implement a single method {@link #fetchNext()},
 * which is much easier to implement than the usual {@link #next()} and
 * {@link #hasNext()} methods. The method {@link #fetchNext()} either returns
 * the next element, which might also be null, or calls the method
 * {@link #setFinished()} to indicate the end of the iterable sequence.
 * <p>
 * Removal of elements is not supported.
 * <p>
 * The {@link #iterator()} method may not be called after {@link #next()} or
 * {@link #hasNext()} has been called.
 *
 * @author Oliver Richers
 * @param <T>
 *            the element type
 */
public abstract class AbstractIteratorAdapter<T> implements Iterator<T>, Iterable<T> {

	private boolean initialized = false;
	private boolean finished = false;
	private T next;

	@Override
	public final boolean hasNext() {

		if (!initialized) {
			initialize();
		}

		return !finished;
	}

	@Override
	public final T next() {

		if (!initialized) {
			initialize();
		}

		if (finished) {
			throw new NoSuchElementException();
		}

		T tmp = next;
		next = fetchNext();
		return tmp;
	}

	@Override
	public final void remove() {

		throw new UnsupportedOperationException();
	}

	@Override
	public final Iterator<T> iterator() {

		if (initialized) {
			throw new IllegalStateException("This iterable may only be interated once.");
		}

		return this;
	}

	/**
	 * Returns the next item or calls {@link #setFinished()} if no more items
	 * available.
	 * <p>
	 * When the end of the sequence is reached, this method will return null,
	 * but since null might also be a valid element of the sequence,
	 * {@link #setFinished()} must be called.
	 *
	 * @return the next item
	 */
	protected abstract T fetchNext();

	/**
	 * Notified that the end of the iterable sequence is reached.
	 *
	 * @return always null for convenience, so you can write: return
	 *         setFinished();
	 */
	protected final T setFinished() {

		finished = true;
		return null;
	}

	private void initialize() {

		next = fetchNext();
		initialized = true;
	}
}

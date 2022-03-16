package com.softicar.platform.common.container.iterator;

import java.util.Iterator;

/**
 * An {@link Iterator} limiting the returned values of another {@link Iterator}.
 *
 * @author Oliver Richers
 */
public class LimitingIterator<T> implements Iterator<T> {

	private final Iterator<T> iterator;
	private final int offset;
	private final int limit;
	private int count;

	public LimitingIterator(Iterator<T> iterator, int limit) {

		this(iterator, 0, limit);
	}

	public LimitingIterator(Iterator<T> iterator, int offset, int limit) {

		this.iterator = iterator;
		this.offset = offset;
		this.limit = limit;
		this.count = 0;

		iterateToOffset();
	}

	private void iterateToOffset() {

		for (int i = 0; i < offset && iterator.hasNext(); i++) {
			iterator.next();
		}
	}

	@Override
	public boolean hasNext() {

		return iterator.hasNext() && !isLimitReached();
	}

	private boolean isLimitReached() {

		return limit > 0 && count >= limit;
	}

	@Override
	public T next() {

		count++;
		return iterator.next();
	}
}

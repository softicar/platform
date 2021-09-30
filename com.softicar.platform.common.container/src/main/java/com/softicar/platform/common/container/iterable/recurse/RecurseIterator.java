package com.softicar.platform.common.container.iterable.recurse;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class RecurseIterator<T> implements Iterator<T> {

	private final Iterator<T> iterator;
	private final Function<T, Iterable<T>> recurseFunction;
	private Iterator<T> subIterator;

	public RecurseIterator(Iterable<T> iterable, Function<T, Iterable<T>> recurseFunction) {

		this(iterable.iterator(), recurseFunction);
	}

	public RecurseIterator(Iterator<T> iterator, Function<T, Iterable<T>> recurseFunction) {

		this.iterator = iterator;
		this.recurseFunction = recurseFunction;
	}

	@Override
	public boolean hasNext() {

		if (subIterator != null && subIterator.hasNext()) {
			return true;
		}

		return iterator.hasNext();
	}

	@Override
	public T next() {

		if (subIterator != null && subIterator.hasNext()) {
			return subIterator.next();
		}

		if (iterator.hasNext()) {
			T element = iterator.next();
			Iterable<T> subIterable = recurseFunction.apply(element);
			this.subIterator = subIterable != null? new RecurseIterator<>(subIterable, recurseFunction) : null;
			return element;
		} else {
			throw new NoSuchElementException();
		}
	}
}

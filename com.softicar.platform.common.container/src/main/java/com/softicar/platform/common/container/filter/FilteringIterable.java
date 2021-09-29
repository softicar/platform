package com.softicar.platform.common.container.filter;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * An {@link Iterable} that filters elements of another {@link Iterable}.
 *
 * @author Oliver Richers
 */
public class FilteringIterable<T> implements Iterable<T> {

	private final Iterable<? extends T> iterable;
	private final Predicate<T> predicate;

	public FilteringIterable(Iterable<? extends T> iterable, Predicate<T> predicate) {

		this.iterable = iterable;
		this.predicate = predicate;
	}

	@Override
	public Iterator<T> iterator() {

		return new FilteringIterator<>(iterable.iterator(), predicate);
	}
}

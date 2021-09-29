package com.softicar.platform.common.container.iterable.recurse;

import java.util.Iterator;
import java.util.function.Function;

/**
 * An {@link Iterable} that iterates a hierarchy of {@link Iterable}s.
 *
 * @author Oliver Richers
 */
public class RecurseIterable<T> implements Iterable<T> {

	private final Iterable<T> iterable;
	private final Function<T, Iterable<T>> recurseFunction;

	public RecurseIterable(Iterable<T> iterable, Function<T, Iterable<T>> recurseFunction) {

		this.iterable = iterable;
		this.recurseFunction = recurseFunction;
	}

	@Override
	public Iterator<T> iterator() {

		return new RecurseIterator<>(iterable, recurseFunction);
	}
}

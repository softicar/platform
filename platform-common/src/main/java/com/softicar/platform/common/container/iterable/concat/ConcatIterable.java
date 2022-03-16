package com.softicar.platform.common.container.iterable.concat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * An {@link Iterable} that concatenates multiple {@link Iterable} instances.
 *
 * @author Oliver Richers
 */
public class ConcatIterable<T> implements Iterable<T> {

	private final Collection<Iterable<T>> iterables;

	public ConcatIterable() {

		this.iterables = new ArrayList<>();
	}

	public ConcatIterable(Collection<? extends Iterable<T>> iterables) {

		this.iterables = new ArrayList<>(iterables);
	}

	@SafeVarargs
	public ConcatIterable(Iterable<T>...iterables) {

		this.iterables = Arrays.asList(iterables);
	}

	public ConcatIterable<T> add(Iterable<T> iterable) {

		this.iterables.add(iterable);
		return this;
	}

	public ConcatIterable<T> add(Iterator<T> iterator) {

		this.iterables.add(() -> iterator);
		return this;
	}

	public ConcatIterable<T> addAll(Collection<? extends Iterable<T>> iterables) {

		this.iterables.addAll(iterables);
		return this;
	}

	@Override
	public Iterator<T> iterator() {

		return new ConcatIterator<>(iterables);
	}
}

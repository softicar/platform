package com.softicar.platform.common.container.iterable;

import com.softicar.platform.common.container.iterator.MappingIterator;
import java.util.Iterator;
import java.util.function.Function;

/**
 * An {@link Iterable} that maps the elements from one type to another.
 *
 * @author Oliver Richers
 */
public class MappingIterable<S, T> implements Iterable<T> {

	private final Iterable<S> sourceIterable;
	private final Function<S, T> mappingFunction;

	public MappingIterable(Iterable<S> sourceIterable, Function<S, T> mappingFunction) {

		this.sourceIterable = sourceIterable;
		this.mappingFunction = mappingFunction;
	}

	@Override
	public Iterator<T> iterator() {

		return new MappingIterator<>(sourceIterable.iterator(), mappingFunction);
	}
}

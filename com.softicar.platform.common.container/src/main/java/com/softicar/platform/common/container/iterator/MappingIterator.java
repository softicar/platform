package com.softicar.platform.common.container.iterator;

import java.util.Iterator;
import java.util.function.Function;

/**
 * An {@link Iterator} that maps the elements from one type to another.
 *
 * @author Oliver Richers
 */
public class MappingIterator<S, T> implements Iterator<T> {

	private final Iterator<S> sourceIterator;
	private final Function<S, T> mappingFunction;

	public MappingIterator(Iterator<S> sourceIterator, Function<S, T> mappingFunction) {

		this.sourceIterator = sourceIterator;
		this.mappingFunction = mappingFunction;
	}

	@Override
	public boolean hasNext() {

		return sourceIterator.hasNext();
	}

	@Override
	public T next() {

		return mappingFunction.apply(sourceIterator.next());
	}

	@Override
	public void remove() {

		sourceIterator.remove();
	}
}

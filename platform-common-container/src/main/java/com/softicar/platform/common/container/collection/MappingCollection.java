package com.softicar.platform.common.container.collection;

import com.softicar.platform.common.container.iterator.MappingIterator;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

/**
 * A {@link Collection} that maps the elements from one type to another.
 *
 * @author Oliver Richers
 */
public class MappingCollection<S, T> extends AbstractCollection<T> {

	private final Collection<S> sourceCollection;
	private final Function<S, T> mappingFunction;

	public MappingCollection(Collection<S> sourceCollection, Function<S, T> mappingFunction) {

		this.sourceCollection = sourceCollection;
		this.mappingFunction = mappingFunction;
	}

	@Override
	public Iterator<T> iterator() {

		return new MappingIterator<>(sourceCollection.iterator(), mappingFunction);
	}

	@Override
	public int size() {

		return sourceCollection.size();
	}
}

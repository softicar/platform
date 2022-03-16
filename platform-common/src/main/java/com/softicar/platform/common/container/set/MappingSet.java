package com.softicar.platform.common.container.set;

import com.softicar.platform.common.container.iterator.MappingIterator;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

/**
 * A {@link Set} that maps the elements using a {@link Function}.
 *
 * @author Oliver Richers
 */
public class MappingSet<S, T> extends AbstractSet<T> {

	private final Set<S> sourceSet;
	private final Function<S, T> mappingFunction;

	public MappingSet(Set<S> sourceSet, Function<S, T> mappingFunction) {

		this.sourceSet = sourceSet;
		this.mappingFunction = mappingFunction;
	}

	@Override
	public int size() {

		return sourceSet.size();
	}

	@Override
	public Iterator<T> iterator() {

		return new MappingIterator<>(sourceSet.iterator(), mappingFunction);
	}
}

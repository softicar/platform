package com.softicar.platform.common.container.list;

import java.util.AbstractList;
import java.util.List;
import java.util.function.Function;

/**
 * A {@link List} that maps the elements from one type to another.
 *
 * @author Oliver Richers
 */
public class MappingList<S, T> extends AbstractList<T> {

	private final List<S> sourceList;
	private final Function<S, T> mappingFunction;

	public MappingList(List<S> sourceList, Function<S, T> mappingFunction) {

		this.sourceList = sourceList;
		this.mappingFunction = mappingFunction;
	}

	@Override
	public int size() {

		return sourceList.size();
	}

	@Override
	public T get(int index) {

		return mappingFunction.apply(sourceList.get(index));
	}
}

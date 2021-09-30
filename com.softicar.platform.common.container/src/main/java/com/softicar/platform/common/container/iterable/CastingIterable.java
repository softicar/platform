package com.softicar.platform.common.container.iterable;

import com.softicar.platform.common.container.filter.FilteringIterator;
import com.softicar.platform.common.container.iterator.MappingIterator;
import java.util.Iterator;

/**
 * An {@link Iterable} that filters and casts the elements of another
 * {@link Iterable}.
 *
 * @author Oliver Richers
 */
public class CastingIterable<T> implements Iterable<T> {

	private final Iterable<?> iterable;
	private final Class<T> targetClass;

	public CastingIterable(Iterable<?> iterable, Class<T> targetClass) {

		this.iterable = iterable;
		this.targetClass = targetClass;
	}

	@Override
	public Iterator<T> iterator() {

		FilteringIterator<Object> filteringIterator = new FilteringIterator<>(iterable.iterator(), targetClass::isInstance);
		return new MappingIterator<>(filteringIterator, targetClass::cast);
	}
}

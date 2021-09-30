package com.softicar.platform.common.container.iterator;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Converts an {@link Iterator} into an {@link Enumeration}.
 * 
 * @author Oliver Richers
 */
public class IteratorEnumeration<T> implements Enumeration<T> {

	private final Iterator<T> iterator;

	public IteratorEnumeration(Iterator<T> iterator) {

		this.iterator = iterator;
	}

	@Override
	public boolean hasMoreElements() {

		return iterator.hasNext();
	}

	@Override
	public T nextElement() {

		return iterator.next();
	}
}

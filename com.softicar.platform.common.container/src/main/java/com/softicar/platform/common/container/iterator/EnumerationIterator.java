package com.softicar.platform.common.container.iterator;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Converts an {@link Enumeration} into an {@link Iterator}.
 * 
 * @author Oliver Richers
 */
public class EnumerationIterator<T> implements Iterator<T> {

	private final Enumeration<T> enumeration;

	public EnumerationIterator(Enumeration<T> enumeration) {

		this.enumeration = enumeration;
	}

	@Override
	public boolean hasNext() {

		return enumeration.hasMoreElements();
	}

	@Override
	public T next() {

		return enumeration.nextElement();
	}

	@Override
	public void remove() {

		throw new UnsupportedOperationException();
	}
}

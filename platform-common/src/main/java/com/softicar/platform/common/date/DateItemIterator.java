package com.softicar.platform.common.date;

import java.util.Iterator;

/**
 * Iterator over a range of {@link DateItem}.
 * 
 * @param <T>
 *            the type of the date items
 * @author Oliver Richers
 */
public class DateItemIterator<T extends DateItem<T>> implements Iterator<T> {

	public DateItemIterator(T first, T last) {

		m_current = first.getPrevious();
		m_last = last;
	}

	@Override
	public boolean hasNext() {

		return m_current.compareTo(m_last) < 0;
	}

	@Override
	public T next() {

		m_current = m_current.getNext();
		return m_current;
	}

	@Override
	public void remove() {

		throw new UnsupportedOperationException();
	}

	private T m_current;
	private final T m_last;
}

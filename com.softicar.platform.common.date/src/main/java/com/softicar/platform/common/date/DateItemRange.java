package com.softicar.platform.common.date;

import java.util.Iterator;

/**
 * Represents a range of from one {@link DateItem} to another.
 * 
 * @param <T>
 *            the type of the date item
 * @author Oliver Richers
 */
public class DateItemRange<T extends DateItem<T>> implements Iterable<T>, Comparable<DateItemRange<T>> {

	/**
	 * A static factory to simplify construction of a range through type
	 * inference.
	 * 
	 * @param first
	 *            the first date item
	 * @param last
	 *            the last date item
	 * @return a new date item range from the first to the last item,
	 *         inclusively
	 */
	public static <TT extends DateItem<TT>> DateItemRange<TT> make(TT first, TT last) {

		return new DateItemRange<>(first, last);
	}

	/**
	 * Constructs a new item range.
	 * 
	 * @param first
	 *            the first date item of this range
	 * @param last
	 *            the last item of this range
	 */
	public DateItemRange(T first, T last) {

		m_first = first;
		m_last = last;
	}

	/**
	 * Constructs a new item range.
	 * 
	 * @param first
	 *            the first date item of this range
	 * @param size
	 *            number of item in this range
	 */
	public DateItemRange(T first, int size) {

		m_first = first;
		m_last = first.getRelative(size - 1);
	}

	/**
	 * Returns the first item of this range.
	 * 
	 * @return the first item
	 */
	public T getFirst() {

		return m_first;
	}

	/**
	 * Returns the last item of this range.
	 * 
	 * @return the last item
	 */
	public T getLast() {

		return m_last;
	}

	/**
	 * Returns the item with the specified index.
	 * 
	 * @param index
	 *            the index staring from 1 for the first item
	 * @return the item with the specified index
	 */
	public T get(int index) {

		return m_first.getRelative(index - 1);
	}

	/**
	 * Returns the size of this range which is the number of items.
	 * 
	 * @return the size of this range
	 */
	public int size() {

		return m_last.getAbsoluteIndex() - m_first.getAbsoluteIndex() + 1;
	}

	/**
	 * Returns true if this range contains the specified item.
	 * 
	 * @param item
	 *            the item to check
	 * @return true if this range contains the item
	 */
	public boolean contains(T item) {

		return item.getAbsoluteIndex() >= m_first.getAbsoluteIndex() && item.getAbsoluteIndex() <= m_last.getAbsoluteIndex();
	}

	/**
	 * Returns an iterator over this range of items.
	 */
	@Override
	public Iterator<T> iterator() {

		return new DateItemIterator<>(m_first, m_last);
	}

	/**
	 * This range is less than the other range if its first item is less than
	 * the other first item, or if both first items are equal and the last item
	 * is less than the other last item. Otherwise, this range is greater or
	 * equal to the other range.
	 */
	@Override
	public int compareTo(DateItemRange<T> other) {

		int cmp1 = m_first.compareTo(other.m_first);
		if (cmp1 < 0) {
			return -1;
		}
		if (cmp1 > 0) {
			return 1;
		}

		int cmp2 = m_last.compareTo(other.m_last);
		if (cmp2 < 0) {
			return -1;
		}
		if (cmp2 > 0) {
			return 1;
		}

		return 0;
	}

	/**
	 * Returns true of this range equals the other range.
	 * 
	 * @param other
	 *            the other range
	 * @return true if both ranges are equal
	 */
	public boolean equals(DateItemRange<T> other) {

		return m_first.equals(other.m_first) && m_last.equals(other.m_last);
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + m_first.hashCode();
		result = prime * result + m_last.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof DateItemRange<?>) {
			DateItemRange<?> other = (DateItemRange<?>) object;
			return m_first.equals(other.m_first) && m_last.equals(other.m_last);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {

		return "(" + m_first + " --> " + m_last + ")";
	}

	private final T m_first;
	private final T m_last;
}

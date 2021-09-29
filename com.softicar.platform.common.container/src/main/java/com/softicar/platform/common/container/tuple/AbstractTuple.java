package com.softicar.platform.common.container.tuple;

import com.softicar.platform.common.string.Imploder;
import java.util.Iterator;

/**
 * The base class of all tuple classes.
 * 
 * @author Oliver Richers
 */
public abstract class AbstractTuple implements Iterable<Object> {

	/**
	 * Returns the element at the specified index.
	 * 
	 * @param index
	 *            the index of the element
	 * @return the element at the specified index
	 * @throws IndexOutOfBoundsException
	 *             if the index is invalid
	 */
	public final Object get(int index) {

		if (index >= 0 && index < size()) {
			return getElement(index);
		} else {
			throw new IndexOutOfBoundsException(String.format("Tried to access index %d of a tuple of size %d.", index, size()));
		}
	}

	/**
	 * Returns the number of elements of this tuple.
	 * 
	 * @return the size of this tuple
	 */
	public abstract int size();

	/**
	 * Returns true if this tuple is empty.
	 * 
	 * @return size() == 0
	 */
	public final boolean isEmpty() {

		return size() == 0;
	}

	/**
	 * Returns an iterator over the elements of this tuple.
	 * 
	 * @return a tuple iterator
	 */
	@Override
	public final Iterator<Object> iterator() {

		return new TupleIterator(this);
	}

	@Override
	public int hashCode() {

		int code = 0;
		for (Object object: this) {
			code += object != null? object.hashCode() : 0;
		}
		return code;
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof AbstractTuple) {
			AbstractTuple otherTuple = (AbstractTuple) other;
			if (otherTuple.size() == size()) {
				Iterator<Object> a = iterator();
				Iterator<Object> b = otherTuple.iterator();
				while (a.hasNext()) {
					Object x = a.next();
					Object y = b.next();
					if (x != y) {
						if (x == null || y == null || !x.equals(y)) {
							return false;
						}
					}
				}
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {

		return "[" + Imploder.implode(this, ", ") + "]";
	}

	/**
	 * Returns the element at the specified index.
	 * 
	 * @param index
	 *            the index of the element
	 * @return the element at the specified index
	 */
	protected abstract Object getElement(int index);
}

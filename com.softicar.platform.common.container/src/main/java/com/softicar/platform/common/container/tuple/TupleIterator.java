package com.softicar.platform.common.container.tuple;

import java.util.Iterator;

/**
 * Iterator over the elements of a tuple.
 * 
 * @author Oliver Richers
 */
public class TupleIterator implements Iterator<Object> {

	private final AbstractTuple tuple;
	private int index;

	public TupleIterator(AbstractTuple tuple) {

		this.tuple = tuple;
		this.index = 0;
	}

	@Override
	public boolean hasNext() {

		return index < tuple.size();
	}

	@Override
	public Object next() {

		return tuple.get(index++);
	}

	@Override
	public void remove() {

		throw new UnsupportedOperationException("You can't remove elements from a tuple.");
	}
}

package com.softicar.platform.common.container.iterator;

import java.util.Iterator;

/**
 * Abstract base class to implement a {@link Iterable} that converts one element
 * type to another.
 * 
 * @author Oliver Richers
 */
public abstract class AbstractConvertingIterable<Source, Target> implements Iterable<Target> {

	private final Iterable<? extends Source> sourceIterable;

	public AbstractConvertingIterable(Iterable<? extends Source> sourceIterable) {

		this.sourceIterable = sourceIterable;
	}

	@Override
	public Iterator<Target> iterator() {

		return new ConvertingIterator(sourceIterable);
	}

	protected abstract Target convertElement(Source source);

	private class ConvertingIterator implements Iterator<Target> {

		private final Iterator<? extends Source> sourceIterator;

		public ConvertingIterator(Iterable<? extends Source> sourceIterable) {

			this.sourceIterator = sourceIterable.iterator();
		}

		@Override
		public boolean hasNext() {

			return sourceIterator.hasNext();
		}

		@Override
		public Target next() {

			return convertElement(sourceIterator.next());
		}

		@Override
		public void remove() {

			sourceIterator.remove();
		}
	}
}

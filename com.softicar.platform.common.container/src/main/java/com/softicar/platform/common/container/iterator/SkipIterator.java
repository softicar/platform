package com.softicar.platform.common.container.iterator;

import com.softicar.platform.common.core.annotations.Nullable;
import java.util.Iterator;

/**
 * An iterator adapter that skips elements that are equal to a given skip
 * element.
 * <p>
 * This supports null elements in the iterated sequence without problem. This
 * also supports null as skip element.
 *
 * @author Oliver Richers
 * @param <T>
 *            the element type
 */
public class SkipIterator<@Nullable T> extends AbstractIteratorAdapter<T> {

	private Iterator<T> iterator;
	private ISkipper<T> skipper;

	// -------------------------------- constructors -------------------------------- //

	/**
	 * Constructs a new skip iterator.
	 *
	 * @param iterator
	 *            the iterator to use as a base
	 * @param skipper
	 *            the skipper instance to use
	 */
	public SkipIterator(Iterator<T> iterator, ISkipper<T> skipper) {

		this.iterator = iterator;
		this.skipper = skipper;
	}

	/**
	 * Constructs a new skip iterator.
	 *
	 * @param iterator
	 *            the iterator to use as a base
	 * @param toSkip
	 *            an object to compare the elements to (null is also supported)
	 */
	public SkipIterator(Iterator<T> iterator, T toSkip) {

		this(iterator, new EqualitySkipper<>(toSkip));
	}

	public SkipIterator(Iterable<T> iterable, T toSkip) {

		this(iterable.iterator(), toSkip);
	}

	// -------------------------------- internal -------------------------------- //

	@Override
	protected T fetchNext() {

		if (iterator.hasNext()) {
			T next = iterator.next();

			if (skipper.shallSkip(next)) {
				return fetchNext();
			} else {
				return next;
			}
		}

		return setFinished();
	}
}

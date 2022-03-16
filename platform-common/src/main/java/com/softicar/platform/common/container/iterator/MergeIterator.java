package com.softicar.platform.common.container.iterator;

import com.softicar.platform.common.core.annotations.Nullable;
import java.util.Iterator;

/**
 * Merges two sequences of {@link Iterator} or {@link Iterable}.
 *
 * @author Oliver Richers
 */
public class MergeIterator<@Nullable T> extends AbstractIteratorAdapter<T> {

	private Iterator<T> iterator1;
	private Iterator<T> iterator2;

	public MergeIterator(Iterable<T> iterable1, Iterable<T> iterable2) {

		this(iterable1.iterator(), iterable2.iterator());
	}

	public MergeIterator(Iterator<T> iterator1, Iterator<T> iterator2) {

		this.iterator1 = iterator1;
		this.iterator2 = iterator2;
	}

	@Override
	protected T fetchNext() {

		if (iterator1 != null) {
			if (iterator1.hasNext()) {
				return iterator1.next();
			} else {
				iterator1 = null;
			}
		}

		if (iterator2 != null) {
			if (iterator2.hasNext()) {
				return iterator2.next();
			} else {
				iterator2 = null;
			}
		}

		return setFinished();
	}
}

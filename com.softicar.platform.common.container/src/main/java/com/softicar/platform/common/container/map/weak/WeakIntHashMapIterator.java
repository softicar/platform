package com.softicar.platform.common.container.map.weak;

import com.softicar.platform.common.core.annotations.Nullable;
import java.util.Iterator;

class WeakIntHashMapIterator<@Nullable V> implements Iterator<V> {

	private final Iterator<WeakIntEntry<V>> iterator;
	private V value;

	public WeakIntHashMapIterator(Iterator<WeakIntEntry<V>> iterator) {

		this.iterator = iterator;
		findNext();
	}

	@Override
	public boolean hasNext() {

		return value != null;
	}

	@Override
	public V next() {

		V value = this.value;
		findNext();
		return value;
	}

	@Override
	public void remove() {

		throw new UnsupportedOperationException();
	}

	private void findNext() {

		value = null;
		while (iterator.hasNext()) {
			WeakIntEntry<V> entry = iterator.next();
			value = entry.get();
			if (value != null) {
				break;
			}
		}
	}
}

package com.softicar.platform.common.container.map.weak;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * A weak reference entry used by {@link WeakIntHashMap}.
 * 
 * @author Oliver Richers
 */
class WeakIntEntry<V> extends WeakReference<V> {

	private final int key;

	public WeakIntEntry(int key, V value, ReferenceQueue<V> queue) {

		super(value, queue);
		this.key = key;
	}

	public int getKey() {

		return key;
	}

	@Override
	public String toString() {

		return "" + get();
	}
}

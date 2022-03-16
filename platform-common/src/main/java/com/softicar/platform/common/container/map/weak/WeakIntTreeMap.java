package com.softicar.platform.common.container.map.weak;

import com.softicar.platform.common.core.annotations.Nullable;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.CastUtils;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.TreeMap;

/**
 * A very memory-efficient implementation of a tree map that maps integers to
 * weak references.
 *
 * @author Alexander Schmidt
 */
public class WeakIntTreeMap<V> implements IWeakMap<Integer, V> {

	private final ReferenceQueue<V> _queue = new ReferenceQueue<>();
	private final TreeMap<Integer, WeakEntry<Integer, V>> _map = new TreeMap<>();

	@Override
	public V put(Integer key, V value) {

		collect();

		if (key != null) {
			_map.put(key, new WeakEntry<>(key, value, _queue));
		}

		return value;
	}

	@Override
	@Nullable
	public V get(Integer key) {

		collect();

		WeakEntry<Integer, V> weakValue = _map.get(key);
		if (weakValue != null) {
			return weakValue.get();
		} else {
			return null;
		}
	}

	@Override
	@Nullable
	public V remove(Integer key) {

		collect();

		WeakEntry<Integer, V> weakEntry = _map.remove(key);
		if (weakEntry != null) {
			return weakEntry.get();
		} else {
			return null;
		}
	}

	@Override
	public int size() {

		return _map.size();
	}

	@Override
	public void collect() {

		long time = System.currentTimeMillis();
		long removeTime = 0;
		long maxRemove = 0;
		int i = 0;
		Object reference;
		while ((reference = _queue.poll()) != null) {
			WeakEntry<Integer, V> collectedEntry = CastUtils.cast(reference);
			long a = System.currentTimeMillis();
			WeakEntry<Integer, V> removedEntry = _map.remove(collectedEntry.getKey());
			long b = System.currentTimeMillis();
			removeTime += b - a;
			if (b - a > maxRemove) {
				maxRemove = b - a;
			}

			if (removedEntry != null) {
				V removedValue = removedEntry.get();
				if (removedValue != null) {
					// NOTE: It is possible that the collected entry in the
					// reference queue and the entry in the map are not the
					// same, so we have to put the entry back!!!
					_map.put(removedEntry.getKey(), removedEntry);
				} else {
					++i;
				}
			}
		}
		if (i > 0) {
			Log
				.fverbose(
					"WeakIntHashMap: %s entries collected, new size: %s (%s ms, %s ms, %s ms)",
					i,
					size(),
					System.currentTimeMillis() - time,
					removeTime,
					maxRemove);
		}
	}

	private class WeakEntry<KK extends Comparable<KK>, VV> extends WeakReference<VV> implements Comparable<WeakEntry<KK, VV>> {

		private final KK key;

		public WeakEntry(KK key, VV value, ReferenceQueue<VV> queue) {

			super(value, queue);
			this.key = key;
		}

		public KK getKey() {

			return key;
		}

		@Override
		public int compareTo(WeakEntry<KK, VV> o) {

			if (o != null && o.key != null) {
				return key.compareTo(o.key);
			} else {
				return 1;
			}
		}

		@Override
		public String toString() {

			return "" + get();
		}
	}
}

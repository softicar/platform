package com.softicar.platform.common.container.map.weak;

import com.softicar.platform.common.core.annotations.Nullable;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.CastUtils;
import java.lang.ref.ReferenceQueue;
import java.util.Iterator;

/**
 * A very memory-efficient implementation of a hash map that maps integers to
 * weak references.
 *
 * @author Oliver Richers
 */
public class WeakIntHashMap<V> implements Iterable<V>, IWeakMap<Integer, V> {

	private final SubMap<V> subMap;
	private final ReferenceQueue<V> referenceQueue = new ReferenceQueue<>();

	public WeakIntHashMap() {

		this.subMap = new SubMap<>();
	}

	public WeakIntHashMap(int maxLoadPercentage) {

		this.subMap = new SubMap<>(maxLoadPercentage);
	}

	@Override
	public final void collect() {

		long time = System.currentTimeMillis();
		long removeTime = 0;
		long maxRemove = 0;
		int i = 0;
		Object reference;
		while ((reference = referenceQueue.poll()) != null) {
			WeakIntEntry<V> collectedEntry = castEntry(reference);
			long a = System.currentTimeMillis();
			WeakIntEntry<V> removedEntry = subMap.remove(collectedEntry.getKey());
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
					subMap.add(removedEntry);
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

	@Override
	public final Iterator<V> iterator() {

		return new WeakIntHashMapIterator<>(subMap.iterator());
	}

	@Override
	@Nullable
	public final V put(Integer key, V value) {

		collect();
		WeakIntEntry<V> entry = subMap.add(createEntry(key, value, referenceQueue));
		return entry != null? entry.get() : null;
	}

	@Override
	@Nullable
	public final V get(Integer key) {

		collect();
		WeakIntEntry<V> entry = subMap.get(key);
		return entry != null? entry.get() : null;
	}

	@Override
	@Nullable
	public final V remove(Integer key) {

		collect();
		WeakIntEntry<V> entry = subMap.remove(key);
		return entry != null? entry.get() : null;
	}

	public final double getStepAverage() {

		return subMap.getStepAverage();
	}

	public final double getClusterAverage() {

		return subMap.getClusterAverage();
	}

	@Override
	public final int size() {

		return subMap.size();
	}

	public final boolean isEmpty() {

		return subMap.isEmpty();
	}

	@Override
	public final String toString() {

		return subMap.toString();
	}

	protected WeakIntEntry<V> createEntry(int key, V value, ReferenceQueue<V> referenceQueue) {

		return new WeakIntEntry<>(key, value, referenceQueue);
	}

	private WeakIntEntry<V> castEntry(Object entry) {

		return CastUtils.cast(entry);
	}
}

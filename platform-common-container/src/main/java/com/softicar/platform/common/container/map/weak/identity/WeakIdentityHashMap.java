package com.softicar.platform.common.container.map.weak.identity;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

/**
 * This map combines the features of {@link IdentityHashMap} and
 * {@link WeakHashMap}.
 * <p>
 * The keys of this map are hashed by identity and referenced by weak
 * references.
 * <p>
 * This map supports null for values but not for keys.
 *
 * @see IdentityHashMap
 * @see WeakHashMap
 * @author Oliver Richers
 */
public class WeakIdentityHashMap<K, V> {

	private final Map<WeakIdentityReference<K>, V> hashMap;
	private final ReferenceQueue<K> referenceQueue;

	public WeakIdentityHashMap() {

		this(HashMap::new);
	}

	public WeakIdentityHashMap(Supplier<Map<WeakIdentityReference<K>, V>> factory) {

		this.hashMap = factory.get();
		this.referenceQueue = new ReferenceQueue<>();
	}

	public V get(K key) {

		collect();
		WeakIdentityReference<K> reference = new WeakIdentityReference<>(key);
		return hashMap.get(reference);
	}

	public V put(K key, V value) {

		collect();
		if (key == null) {
			throw new IllegalArgumentException("No null keys allowed.");
		}
		WeakIdentityReference<K> reference = new WeakIdentityReference<>(key, referenceQueue);
		return hashMap.put(reference, value);
	}

	public V remove(K key) {

		collect();
		WeakIdentityReference<K> reference = new WeakIdentityReference<>(key);
		return hashMap.remove(reference);
	}

	public int size() {

		collect();
		return hashMap.size();
	}

	public boolean isEmpty() {

		return size() == 0;
	}

	public void collect() {

		while (true) {
			Reference<? extends K> deadReference = referenceQueue.poll();

			if (deadReference == null) {
				// no more dead references
				break;
			}

			hashMap.remove(deadReference);
		}
	}
}

package com.softicar.platform.common.container.map.weak.map.adapter;

import com.softicar.platform.common.core.utils.CastUtils;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * A {@link Map} adapter that holds the values with {@link WeakReference}.
 * <p>
 * Similar to {@link WeakHashMap}, the {@link Map} can shrink in size without
 * explicit removal of entries. Usual {@link Map} methods like
 * {@link Map#size()} or {@link Map#isEmpty()} do not make sense on this class.
 * <p>
 * This class is thread-safe if the underlying {@link Map} is thread-safe.
 *
 * @author Oliver Richers
 */
public class WeakValueMapAdapter<K, V> {

	private final Map<K, ValueReference> map;
	private final ReferenceQueue<V> referenceQueue;

	public WeakValueMapAdapter(Supplier<Map<K, ValueReference>> mapFactory) {

		this.map = mapFactory.get();
		this.referenceQueue = new ReferenceQueue<>();
	}

	/**
	 * Puts the given key and value pair into this {@link ConcurrentHashMap}.
	 *
	 * @param key
	 *            the key (never <i>null</i>)
	 * @param value
	 *            the value (never <i>null</i>)
	 */
	public void put(K key, V value) {

		Objects.requireNonNull(key);
		Objects.requireNonNull(value);

		collect();
		map.put(key, new ValueReference(key, value));
	}

	/**
	 * Removes the given key from this {@link ConcurrentHashMap}.
	 *
	 * @param key
	 *            the key to remove (never <i>null</i>)
	 */
	public void remove(K key) {

		Objects.requireNonNull(key);

		collect();
		map.remove(key);
	}

	/**
	 * Obtains the value for the given key from this {@link ConcurrentHashMap}.
	 *
	 * @param key
	 *            the key to get the value for (never <i>null</i>)
	 * @return the matching value as {@link Optional}
	 */
	public Optional<V> get(K key) {

		Objects.requireNonNull(key);

		collect();
		return Optional.ofNullable(map.get(key)).map(ValueReference::get);
	}

	private void collect() {

		while (true) {
			Reference<? extends V> reference = referenceQueue.poll();
			if (reference != null) {
				collect(reference);
			} else {
				break;
			}
		}

	}

	private void collect(Reference<? extends V> reference) {

		CastUtils//
			.tryCast(reference, ValueReference.class)
			.ifPresent(it -> map.remove(it.getKey()));
	}

	public class ValueReference extends WeakReference<V> {

		private final K key;

		public ValueReference(K key, V value) {

			super(value, referenceQueue);

			this.key = key;
		}

		public K getKey() {

			return key;
		}
	}
}

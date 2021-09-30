package com.softicar.platform.common.container.cache;

import com.softicar.platform.common.core.annotations.Nullable;
import com.softicar.platform.common.core.logging.IntervalLog;
import com.softicar.platform.common.core.logging.Log;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * This structure can be used to cache the loading of objects.
 * <p>
 * The cache works like a Map but it has a variable upper limit of its size.
 * When the maximum size is reached, the least used item will be removed. This
 * way, the hit/miss ratio should be optimal.
 *
 * @author Oliver Richers
 */
public class Cache<K, V> {

	private final Map<K, Item<K, V>> keyMap = new TreeMap<>();
	private final int maxSize;
	private final String name;
	private Item<K, V> newest;
	private int queueSize;
	private int hitCount = 0;
	private int missCount = 0;

	/**
	 * Creates the cache with the specified parameters.
	 * <p>
	 * If max is less than 1, the cache will never remove any items.
	 *
	 * @param max
	 *            the maximum size of the cache
	 * @param cacheName
	 *            the name of the cache (only used for logging/debugging)
	 */
	public Cache(int max, String cacheName) {

		this.maxSize = max;
		this.name = cacheName;
	}

	/**
	 * Adds an new value to the cache.
	 * <p>
	 * If the maximum size of the cache is reached, this will remove the least
	 * used item from the cache.
	 * <p>
	 * If there is already a value with the specified key, that value will be
	 * overwritten with the new value.
	 */
	public void put(K key, V value) {

		if (key == null) {
			throw new IllegalArgumentException("The key for a cache entry may not be null.");
		}

		// remove old item with the same key
		Item<K, V> oldItem = keyMap.remove(key);
		if (oldItem != null) {
			removeFromQueue(oldItem);
		}

		// add new item
		Item<K, V> item = new Item<>(key, value);
		keyMap.put(key, item);
		insertIntoQueue(item);

		// remove if too many entries
		if (maxSize > 0) {
			while (keyMap.size() > maxSize) {
				Item<K, V> oldest = newest.prev;
				Log.verbose("Cache: Removing oldest item " + oldest.key + ".");
				keyMap.remove(oldest.key);
				removeFromQueue(oldest);
			}
		}
	}

	/**
	 * Returns true if a value with the specified key is in the cache.
	 */
	public boolean containsKey(K key) {

		return getItem(key) != null;
	}

	/**
	 * Returns the value with the specified key from the cache.
	 *
	 * @param key
	 *            the key of the value
	 * @return the value, or null if no such item can be found
	 */
	public @Nullable V get(K key) {

		Item<K, V> item = getItem(key);
		return item == null? null : item.value;
	}

	/**
	 * Returns the keys of all cached items.
	 *
	 * @return collection of all keys
	 */
	public Set<K> getKeys() {

		return keyMap.keySet();
	}

	/**
	 * Returns the current number of cached items.
	 */
	public int size() {

		return keyMap.size();
	}

	private static class Item<K, V> {

		public Item(K k, V v) {

			key = k;
			value = v;
		}

		public K key;
		public V value;
		public Item<K, V> prev;
		public Item<K, V> next;
	}

	private void insertIntoQueue(Item<K, V> item) {

		if (newest == null) {
			item.prev = item;
			item.next = item;
		} else {
			item.next = newest;
			item.prev = newest.prev;

			item.next.prev = item;
			item.prev.next = item;
		}

		newest = item;
		++queueSize;
	}

	private void removeFromQueue(Item<K, V> item) {

		// special case for one entry
		if (queueSize == 1) {
			if (item != newest || item.next != item || item.prev != item) {
				throw new IllegalStateException("Internal error in cache for item " + item.key + " -> " + item.value + ".");
			}
			newest = null;
		} else {
			item.next.prev = item.prev;
			item.prev.next = item.next;
			if (item == newest) {
				newest = item.next;
			}
		}

		--queueSize;
	}

	private Item<K, V> getItem(K key) {

		Item<K, V> item = keyMap.get(key);
		if (item != null) {
			++hitCount;
			removeFromQueue(item);
			insertIntoQueue(item);
		} else {
			++missCount;
		}

		IntervalLog.intervalLog(Log.VERBOSE_LEVEL, name, "Hit/miss ratio: " + hitCount + "/" + missCount + ".");
		return item;
	}
}

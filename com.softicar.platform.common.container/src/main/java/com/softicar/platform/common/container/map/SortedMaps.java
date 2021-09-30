package com.softicar.platform.common.container.map;

import com.softicar.platform.common.core.utils.CastUtils;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public class SortedMaps {

	/**
	 * Returns the first entry of the specified sorted map.
	 * 
	 * @param map
	 *            the sorted map
	 * @return the first entry or null if and only if the map is empty
	 */
	public static <K, V> Map.Entry<K, V> getFirstEntry(SortedMap<K, V> map) {

		if (map.isEmpty()) {
			return null;
		}

		return map.entrySet().iterator().next();
	}

	/**
	 * Returns the last map entry whose key is less than the specified key. If
	 * there is no such entry, this returns null.
	 * 
	 * @param map
	 *            the sorted map
	 * @param key
	 *            the key (the map does not need contain this key)
	 * @return the preceding entry or null
	 */
	public static <K, V> Map.Entry<K, V> getPrecedingEntry(SortedMap<K, V> map, K key) {

		return SortedMaps.getLastEntry(map.headMap(key));
	}

	/**
	 * Returns the entry with the specified key of the specified sorted map.
	 * 
	 * @param map
	 *            the sorted map
	 * @return the entry or null if there is no matching entry for the key
	 */
	public static <K, V> Map.Entry<K, V> getEntry(SortedMap<K, V> map, K key) {

		Map.Entry<K, V> entry = SortedMaps.getFirstEntry(map.tailMap(key));

		return entry != null && compareKeys(map, key, entry.getKey()) == 0? entry : null;
	}

	/**
	 * Returns the first map entry whose key is greater than the specified key.
	 * If there is no such entry, this returns null.
	 * 
	 * @param map
	 *            the sorted map
	 * @param key
	 *            the key (the map does not need contain this key)
	 * @return the succeeding entry or null
	 */
	public static <K, V> Map.Entry<K, V> getSucceedingEntry(SortedMap<K, V> map, K key) {

		SortedMap<K, V> tailMap = map.tailMap(key);
		for (Map.Entry<K, V> entry: tailMap.entrySet()) {
			if (compareKeys(map, entry.getKey(), key) > 0) {
				return entry;
			}
		}
		return null;
	}

	/**
	 * Returns the last entry of the specified sorted map.
	 * 
	 * @param map
	 *            the sorted map
	 * @return the last entry or null if and only if the map is empty
	 */
	public static <K, V> Map.Entry<K, V> getLastEntry(SortedMap<K, V> map) {

		if (map.isEmpty()) {
			return null;
		}

		return SortedMaps.getEntry(map, map.lastKey());
	}

	private static <K, V> int compareKeys(SortedMap<K, V> map, K a, K b) {

		Comparator<? super K> comparator = map.comparator();
		if (comparator != null) {
			return comparator.compare(a, b);
		} else {
			Comparable<? super K> comparableA = CastUtils.cast(a);
			return comparableA.compareTo(b);
		}
	}
}

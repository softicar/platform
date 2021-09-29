package com.softicar.platform.common.container.map;

import com.softicar.platform.common.core.utils.CastUtils;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class contains static factory methods to conveniently create instances
 * of {@link Map}.
 * 
 * @author Oliver Richers
 */
public abstract class MapFactory {

	/**
	 * Creates a new and empty hash map.
	 */
	public static <K, V> HashMap<K, V> createHashMap() {

		return new HashMap<>();
	}

	/**
	 * Creates a new and empty identity hash map.
	 */
	public static <K, V> IdentityHashMap<K, V> createIdentityHashMap() {

		return new IdentityHashMap<>();
	}

	/**
	 * Creates a new and empty tree map.
	 */
	public static <K, V> TreeMap<K, V> createTreeMap() {

		return new TreeMap<>();
	}

	/**
	 * Creates a new and empty tree map, using the specified comparator.
	 */
	public static <K, V> TreeMap<K, V> createTreeMap(Comparator<? super K> comparator) {

		return new TreeMap<>(comparator);
	}

	/**
	 * Creates a new tree map and inserts the entries of the specified map.
	 * <p>
	 * If the specified map is in fact a {@link SortedMap}, this redirects to
	 * the more efficient method {@link #createTreeMap(SortedMap)}, which will
	 * also transfer the comparator to the new map.
	 * <p>
	 * If the specified map is not a sorted map, the default comparator is used,
	 * and this operation runs in O(n*log n).
	 */
	public static <K, V> SortedMap<K, V> createTreeMap(Map<K, V> map) {

		if (map instanceof SortedMap<?, ?>) {
			SortedMap<K, V> sortedMap = CastUtils.cast(map);
			return createTreeMap(sortedMap);
		}

		return new TreeMap<>(map);
	}

	/**
	 * Creates a new tree map and inserts the entries of the specified map.
	 * <p>
	 * The comparator of the specified map is also used for the new map.
	 * <p>
	 * This operation runs in linear time, O(n).
	 */
	public static <K, V> SortedMap<K, V> createTreeMap(SortedMap<K, V> sortedMap) {

		return new TreeMap<>(sortedMap);
	}
}

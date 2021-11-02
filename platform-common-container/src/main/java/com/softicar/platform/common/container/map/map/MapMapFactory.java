package com.softicar.platform.common.container.map.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * Provides some static factory methods for {@link IMapMap}.
 * 
 * @author Oliver Richers
 */
public class MapMapFactory {

	/**
	 * Creates a new {@link IMapMap} based on {@link TreeMap}.
	 * <p>
	 * No comparators are given, so both keys must implement the interface
	 * {@link Comparable}.
	 * 
	 * @return a new and empty instance of {@link IMapMap}
	 */
	public static <K1 extends Comparable<? super K1>, K2 extends Comparable<? super K2>, V> IMapMap<K1, K2, V> create() {

		return new MapMap<>(new TreeMap<>(), TreeMap::new);
	}

	/**
	 * Creates a new {@link IMapMap} based on {@link HashMap}.
	 * 
	 * @return a new and empty instance of {@link IMapMap}
	 */
	public static <K1, K2, V> IMapMap<K1, K2, V> createHashMapHashMap() {

		return new MapMap<>(new HashMap<>(), HashMap::new);
	}

	/**
	 * Creates a new {@link IMapMap} based on {@link LinkedHashMap} and
	 * {@link TreeMap}.
	 * <p>
	 * Because no comparator for the secondary key is given, the key must
	 * implement the interface {@link Comparable}.
	 * 
	 * @return a new and empty map of maps
	 */
	public static <K1, K2 extends Comparable<? super K2>, V> IMapMap<K1, K2, V> createLinkedHashMapTreeMap() {

		return new MapMap<>(new LinkedHashMap<>(), TreeMap::new);
	}
}

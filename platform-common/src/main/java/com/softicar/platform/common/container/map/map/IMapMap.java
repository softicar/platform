package com.softicar.platform.common.container.map.map;

import java.util.Map;
import java.util.Set;

/**
 * A map of maps.
 * <p>
 * The goal of this class is to simplify common use cases for a 2-level mapping.
 * This is similar to a 2-dimensional matrix but without the explicit notion of
 * rows and columns.
 * <p>
 * For example, instead of this code:
 *
 * <pre>
 * {@code
 * Map<String, Map<String, Integer>> mapMap = ...;
 *
 * // insertion
 * Map<String, Integer> map = mapMap.get("foo");
 * if (map == null)
 *     mapMap.put("foo", map = new HashMap<String, Integer>());
 * map.put("foo", "bar", 42);
 *
 * // fetching
 * Map<String, Integer> map = mapMap.get("foo");
 * Integer value = map != null && map.get("bar") : null;
 * }
 * </pre>
 * <p>
 * You can simply write this code:
 *
 * <pre>
 * {@code
 * IMapMap<String, String, Integer> mapMap = ...;
 *
 * // insertion
 * mapMap.put("foo", "bar", 42);
 *
 * // fetching
 * Integer value = mapMap.get("foo", "bar");
 * }
 * </pre>
 *
 * @param <K1>
 *            the type of the primary key
 * @param <K2>
 *            the type of the secondary key
 * @param <V>
 *            the type of the values
 * @author Oliver Richers
 */
public interface IMapMap<K1, K2, V> {

	/**
	 * Removes all entries from this map.
	 * <p>
	 * The map is completely empty after calling this.
	 */
	void clear();

	/**
	 * Returns <i>true</i> if a value for the specified key combination is
	 * defined.
	 * <p>
	 * Please note that the corresponding value returned by {@link #get} might
	 * be null, even if this method returned true. But this is only the case if
	 * the value was defined to be null, for example by a call to {@link #put}.
	 *
	 * @param primaryKey
	 *            a primary key of the map
	 * @param secondaryKey
	 *            a secondary key of the map
	 * @return <i>true</i> if a value was specified for the given key
	 *         combination, <i>false</i> otherwise
	 */
	boolean contains(K1 primaryKey, K2 secondaryKey);

	/**
	 * Returns <i>true</i> if one or more values are defined for the given
	 * primary key.
	 * <p>
	 * This means that the given primaryKey will be contained in the set,
	 * returned by {@link #keySet()} if this method returns true.
	 */
	boolean containsKey(K1 primaryKey);

	/**
	 * Returns a set of all primary keys and their respective map.
	 * <p>
	 * Do not modify the returned set and maps.
	 */
	Set<Map.Entry<K1, Map<K2, V>>> entrySet();

	/**
	 * Returns the corresponding value for the given key combination.
	 *
	 * @param primaryKey
	 *            the primary key of the value
	 * @param secondaryKey
	 *            the secondary key of the value
	 * @return the corresponding value or null
	 */
	V get(K1 primaryKey, K2 secondaryKey);

	/**
	 * Returns an unmodifiable map for the given primary key (never null).
	 *
	 * @param primaryKey
	 *            the primary key of the map
	 * @return the corresponding map (never null)
	 */
	Map<K2, V> getMap(K1 primaryKey);

	/**
	 * Returns true if this map is empty.
	 */
	boolean isEmpty();

	/**
	 * Returns a set of all primary keys.
	 */
	Set<K1> keySet();

	/**
	 * Sets the value for the specified key combination.
	 *
	 * @param primaryKey
	 *            the primary key of the value to set
	 * @param secondaryKey
	 *            the secondary key of the value to set
	 * @param value
	 *            the value to set, may be null
	 * @return the previously assigned value for the key combination or null
	 */
	V put(K1 primaryKey, K2 secondaryKey, V value);

	/**
	 * Adds all value assignment from the given {@link IMapMap}.
	 *
	 * @param mapMap
	 *            the map to get all values from (never null)
	 */
	void putAll(IMapMap<K1, K2, V> mapMap);

	/**
	 * Removes all values for the given primary key.
	 *
	 * @param primaryKey
	 *            the primary key (never null)
	 */
	void remove(K1 primaryKey);

	/**
	 * Returns the matching value for the specified key combination.
	 * <p>
	 * If no matching value exists, this returns null.
	 *
	 * @return the matching value or null
	 */
	V remove(K1 primaryKey, K2 secondaryKey);

	/**
	 * Returns the number of primary keys.
	 */
	int size();
}

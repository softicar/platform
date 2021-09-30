package com.softicar.platform.common.container.map.set;

import java.util.Collection;
import java.util.Set;

/**
 * Map from a key to a set of values.
 * <p>
 * The goal of this class is to simplify common use cases of a map of sets.
 * <p>
 * For example, instead of this code:
 *
 * <pre>
 * {@code
 * Map<String, Set<Integer>> setMap = ...;
 *
 * // insertion
 * Set<Integer> set = setMap.get("foo");
 * if (set == null)
 *     setMap.put("foo", set = new HashSet<Integer>());
 * set.add(42);
 *
 * // contains
 * Set<Integer> set = setMap.get("foo");
 * boolean contains = set != null && set.contains(42);
 * }
 * </pre>
 * <p>
 * You can write this code:
 *
 * <pre>
 * {@code
 * ISetMap<String, Integer> setMap = ...;
 *
 * // insertion
 * setMap.add("foo", 42);
 *
 * // contains
 * boolean contains = setMap.contains("foo", 42);
 * }
 * </pre>
 *
 * @param <K>
 *            the type of the keys
 * @param <V>
 *            the type of the values
 * @author Oliver Richers
 */
public interface ISetMap<K, V> {

	void addAllToSet(K key, Collection<V> values);

	/**
	 * Adds the specified value to the set of the specified key.
	 *
	 * @param key
	 *            the key of the set
	 * @param value
	 *            the value to add
	 * @return <i>true</i> if the set did not already contain the specified
	 *         value, <i>false</i> otherwise
	 */
	boolean addToSet(K key, V value);

	void clear();

	/**
	 * Returns <i>true</i> if a set with the specified key exists and contains
	 * the given value.
	 *
	 * @param key
	 *            the key of the set
	 * @param value
	 *            the potential value of the set to search for
	 * @return <i>true</i> if and only if the value exists in the set,
	 *         <i>false</i> otherwise
	 */
	boolean contains(K key, V value);

	boolean containsKey(K key);

	Set<V> getSet(K key);

	Collection<Set<V>> getSets();

	boolean isEmpty();

	Set<K> keySet();

	void remove(K key);

	boolean remove(K key, V value);

	int size();
}

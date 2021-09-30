package com.softicar.platform.common.container.map.list;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Map from a key to a {@link List} of values.
 *
 * @param <K>
 *            the key class
 * @param <V>
 *            the values in the sets
 * @author Oliver Richers
 */
public interface IListMap<K, V> {

	// -------------------- read-only -------------------- //

	boolean containsKey(K key);

	boolean isEmpty();

	int size();

	Set<K> keySet();

	Set<Entry<K, List<V>>> entrySet();

	Collection<List<V>> values();

	/**
	 * Returns the list of values for the given key.
	 * <p>
	 * If no matching list exists yet, a new empty list is created and added to
	 * this map. You may use the returned list and add values to it. Those
	 * values will then be part of this map.
	 *
	 * @param key
	 *            the key
	 * @return the matching list, never null
	 */
	List<V> getList(K key);

	/**
	 * Returns all values of all lists in this map.
	 *
	 * @return a list of all list values
	 */
	List<V> getListsContent();

	// -------------------- mutating -------------------- //

	void clear();

	List<V> removeKey(K key);

	void addToList(K key, V value);

	void addAllToList(K key, Collection<V> values);
}

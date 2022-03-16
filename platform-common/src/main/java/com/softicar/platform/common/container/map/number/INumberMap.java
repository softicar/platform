package com.softicar.platform.common.container.map.number;

import com.softicar.platform.common.math.arithmetic.IArithmetic;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Common interface for all maps containing numeric values.
 * <p>
 * An {@link INumberMap} is actually just a convenience facade for a regular
 * {@link Map}.
 *
 * @param <K>
 *            the type of the keys
 * @param <V>
 *            the type of the values
 * @author Oliver Richers
 */
public interface INumberMap<K, V> {

	Map<K, V> getDataContainer();

	IArithmetic<V> getArithmetic();

	// -------------------- read-only -------------------- //

	boolean containsKey(K key);

	/**
	 * Same as {@link #getOrZero}.
	 *
	 * @param key
	 *            the key
	 * @return the corresponding value or zero
	 */
	V get(K key);

	/**
	 * Returns the corresponding value or the given default value.
	 *
	 * @param key
	 *            the key
	 * @return the corresponding value or the default value
	 */
	V getOrDefault(K key, V defaultValue);

	/**
	 * Returns the corresponding value or null.
	 *
	 * @param key
	 *            the key
	 * @return the corresponding value or null
	 */
	V getOrNull(K key);

	/**
	 * Returns the corresponding value or throws an exception.
	 *
	 * @param key
	 *            the key
	 * @return the corresponding value or throws an exception
	 * @throws RuntimeException
	 *             if no matching value exists in the map
	 */
	V getOrThrow(K key);

	/**
	 * Returns the corresponding value or zero.
	 *
	 * @param key
	 *            the key
	 * @return the corresponding value or zero
	 */
	V getOrZero(K key);

	boolean isEmpty();

	int size();

	Set<K> keySet();

	Set<Entry<K, V>> entrySet();

	Collection<V> values();

	// -------------------- mutating -------------------- //

	void clear();

	void put(K key, V value);

	void putAll(Map<K, V> map);

	void putAll(INumberMap<K, V> map);

	V remove(K key);

	// -------------------- arithmetics -------------------- //

	void add(K key, V value);

	void sub(K key, V value);

	void add(INumberMap<K, V> other);

	void sub(INumberMap<K, V> other);

	void inc(K key);

	void dec(K key);
}

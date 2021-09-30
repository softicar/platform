package com.softicar.platform.common.container.map.number;

import com.softicar.platform.common.math.arithmetic.IArithmetic;
import com.softicar.platform.common.string.Imploder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * Abstract base class of all number maps.
 *
 * @param <K>
 *            the type of the keys
 * @param <V>
 *            the type of the values
 * @author Oliver Richers
 */
public class NumberMap<K, V extends Comparable<V>> implements INumberMap<K, V> {

	private final Map<K, V> data;
	private final IArithmetic<V> arithmetic;

	public NumberMap(IArithmetic<V> arithmetic) {

		this(new TreeMap<>(), arithmetic);
	}

	public NumberMap(Map<K, V> data, IArithmetic<V> arithmetic) {

		this.data = data;
		this.arithmetic = arithmetic;
	}

	@Override
	public Map<K, V> getDataContainer() {

		return data;
	}

	@Override
	public IArithmetic<V> getArithmetic() {

		return arithmetic;
	}

	// -------------------- read-only -------------------- //

	@Override
	public boolean containsKey(K key) {

		return data.containsKey(key);
	}

	@Override
	public V get(K key) {

		return getOrZero(key);
	}

	@Override
	public V getOrZero(K key) {

		V value = data.get(key);
		return value != null? value : arithmetic.getZero();
	}

	@Override
	public V getOrDefault(K key, V defaultValue) {

		V value = data.get(key);
		return value != null? value : defaultValue;
	}

	@Override
	public V getOrNull(K key) {

		return data.get(key);
	}

	@Override
	public V getOrThrow(K key) {

		V value = data.get(key);
		if (value != null) {
			return value;
		} else {
			throw new RuntimeException(String.format("No matching value for key %s.", key));
		}
	}

	@Override
	public boolean isEmpty() {

		return data.isEmpty();
	}

	@Override
	public int size() {

		return data.size();
	}

	/**
	 * Returns a set containing all keys of the map.
	 *
	 * @return a set with all keys
	 */
	@Override
	public Set<K> keySet() {

		return data.keySet();
	}

	/**
	 * Returns a set of all entries.
	 *
	 * @return a set with all entries
	 */
	@Override
	public Set<Map.Entry<K, V>> entrySet() {

		return data.entrySet();
	}

	/**
	 * Returns a collection of all values.
	 *
	 * @return a collection with all values
	 */
	@Override
	public Collection<V> values() {

		return data.values();
	}

	// -------------------- mutating -------------------- //

	@Override
	public void clear() {

		data.clear();
	}

	/**
	 * Sets the value for the specified key.
	 *
	 * @param key
	 *            the key to set the value for
	 * @param value
	 *            the value to be set
	 */
	@Override
	public void put(K key, V value) {

		if (value == null || value.compareTo(arithmetic.getZero()) == 0) {
			data.remove(key);
		} else {
			data.put(key, value);
		}
	}

	@Override
	public void putAll(Map<K, V> map) {

		map.entrySet().forEach(entry -> put(entry.getKey(), entry.getValue()));
	}

	@Override
	public void putAll(INumberMap<K, V> map) {

		map.entrySet().forEach(entry -> put(entry.getKey(), entry.getValue()));
	}

	@Override
	public V remove(K key) {

		return data.remove(key);
	}

	// -------------------- arithmetics -------------------- //

	/**
	 * Adds the other value to the value with the specified key in the map.
	 *
	 * @param key
	 *            the key of the value in this map
	 * @param value
	 *            the other value to add
	 */
	@Override
	public void add(K key, V value) {

		put(key, arithmetic.plus(get(key), value));
	}

	/**
	 * Subtracts the other value from the value with the specified key in the
	 * map.
	 *
	 * @param key
	 *            the key of the value in this map
	 * @param value
	 *            the other value to add
	 */
	@Override
	public void sub(K key, V value) {

		put(key, arithmetic.minus(get(key), value));
	}

	/**
	 * Add the values of the specified map to the values of this map.
	 *
	 * @param other
	 *            the other map to add
	 */
	@Override
	public void add(INumberMap<K, V> other) {

		for (K key: other.keySet()) {
			add(key, other.get(key));
		}
	}

	/**
	 * Subtracts the values of the specified map from the values of this map.
	 *
	 * @param other
	 *            the other map to subtract
	 */
	@Override
	public void sub(INumberMap<K, V> other) {

		for (K key: other.keySet()) {
			sub(key, other.get(key));
		}
	}

	/**
	 * Increments the value with the specified key by one.
	 *
	 * @param key
	 *            the key identifying the value
	 */
	@Override
	public void inc(K key) {

		add(key, arithmetic.getOne());
	}

	/**
	 * Decrements the value with the specified key by one.
	 *
	 * @param key
	 *            the key identifying the value
	 */
	@Override
	public void dec(K key) {

		sub(key, arithmetic.getOne());
	}

	// -------------------- standard methods -------------------- //

	@Override
	public boolean equals(Object otherObject) {

		if (otherObject instanceof NumberMap) {
			NumberMap<?, ?> other = (NumberMap<?, ?>) otherObject;
			return Objects.equals(data, other.data) && Objects.equals(arithmetic, other.arithmetic);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(data, arithmetic);
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (Map.Entry<K, V> entry: data.entrySet()) {
			sb.append(entry.getKey() + ": " + entry.getValue() + "\n");
		}
		return sb.toString();
	}

	public String toStringLine() {

		List<String> entries = new ArrayList<>(data.size());
		for (Map.Entry<K, V> entry: data.entrySet()) {
			entries.add(entry.getKey() + ": " + entry.getValue());
		}
		return "[" + Imploder.implode(entries, ", ") + "]";
	}
}

package com.softicar.platform.common.container.map.set;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Default implementation of {@link ISetMap}.
 *
 * @author Oliver Richers
 */
public class SetMap<K extends Comparable<?>, V extends Comparable<? super V>> implements ISetMap<K, V> {

	private final Map<K, TreeSet<V>> setMap;

	public SetMap() {

		this.setMap = new TreeMap<>();
	}

	public SetMap(Comparator<K> comparator) {

		this.setMap = new TreeMap<>(comparator);
	}

	@Override
	public void addAllToSet(K key, Collection<V> values) {

		TreeSet<V> set = setMap.get(key);
		if (set == null) {
			setMap.put(key, set = new TreeSet<>());
		}
		set.addAll(values);
	}

	@Override
	public boolean addToSet(K key, V value) {

		TreeSet<V> set = setMap.get(key);
		if (set == null) {
			setMap.put(key, set = new TreeSet<>());
		}
		return set.add(value);
	}

	@Override
	public void clear() {

		this.setMap.clear();
	}

	@Override
	public boolean contains(K key, V value) {

		TreeSet<V> set = setMap.get(key);
		return set != null && set.contains(value);
	}

	@Override
	public boolean containsKey(K key) {

		return setMap.containsKey(key);
	}

	public Set<Map.Entry<K, TreeSet<V>>> entrySet() {

		return setMap.entrySet();
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof SetMap) {
			return ((SetMap<?, ?>) object).setMap.equals(this.setMap);
		} else {
			return false;
		}
	}

	@Override
	public TreeSet<V> getSet(K key) {

		TreeSet<V> set = setMap.get(key);
		if (set == null) {
			setMap.put(key, set = new TreeSet<>());
		}
		return set;
	}

	@Override
	public Collection<Set<V>> getSets() {

		return Collections.unmodifiableCollection(setMap.values());
	}

	public Set<V> getSetsContent() {

		Set<V> contentOfAllSets = new TreeSet<>();
		for (TreeSet<V> set: setMap.values()) {
			for (V v: set) {
				contentOfAllSets.add(v);
			}
		}
		return contentOfAllSets;
	}

	@Override
	public int hashCode() {

		return setMap.hashCode();
	}

	@Override
	public boolean isEmpty() {

		return setMap.isEmpty();
	}

	@Override
	public Set<K> keySet() {

		return setMap.keySet();
	}

	@Override
	public void remove(K key) {

		setMap.remove(key);
	}

	@Override
	public boolean remove(K key, V value) {

		TreeSet<V> set = setMap.get(key);
		return set != null && set.remove(value);
	}

	@Override
	public int size() {

		return setMap.size();
	}

	@Override
	public String toString() {

		return setMap.toString();
	}
}

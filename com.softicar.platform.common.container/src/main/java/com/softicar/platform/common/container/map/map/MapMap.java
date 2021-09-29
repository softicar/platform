package com.softicar.platform.common.container.map.map;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Default implementation of {@link IMapMap}.
 * 
 * @param <K1>
 *            the type of the primary keys
 * @param <K2>
 *            the type of the second keys
 * @param <V>
 *            the type of the values
 * @author Oliver Richers
 */
class MapMap<K1, K2, V> implements IMapMap<K1, K2, V> {

	private final Map<K1, Map<K2, V>> mapMap;
	private final Supplier<Map<K2, V>> mapFactory;

	public MapMap(Map<K1, Map<K2, V>> mapMap, Supplier<Map<K2, V>> mapFactory) {

		this.mapMap = mapMap;
		this.mapFactory = mapFactory;
	}

	@Override
	public void clear() {

		this.mapMap.clear();
	}

	@Override
	public boolean contains(K1 primaryKey, K2 secondaryKey) {

		return getMap(primaryKey).containsKey(secondaryKey);
	}

	@Override
	public boolean containsKey(K1 primaryKey) {

		return mapMap.containsKey(primaryKey);
	}

	@Override
	public Set<Entry<K1, Map<K2, V>>> entrySet() {

		return mapMap.entrySet();
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof MapMap) {
			return mapMap.equals(((MapMap<?, ?, ?>) object).mapMap);
		} else {
			return false;
		}
	}

	@Override
	public V get(K1 primaryKey, K2 secondaryKey) {

		return getMap(primaryKey).get(secondaryKey);
	}

	@Override
	public Map<K2, V> getMap(K1 primaryKey) {

		Map<K2, V> map = mapMap.get(primaryKey);
		if (map == null) {
			return Collections.emptyMap();
		}
		return Collections.unmodifiableMap(map);
	}

	@Override
	public int hashCode() {

		return mapMap.hashCode();
	}

	@Override
	public boolean isEmpty() {

		return mapMap.isEmpty();
	}

	@Override
	public Set<K1> keySet() {

		return mapMap.keySet();
	}

	@Override
	public V put(K1 primaryKey, K2 secondaryKey, V value) {

		Map<K2, V> map = mapMap.get(primaryKey);
		if (map == null) {
			mapMap.put(primaryKey, map = mapFactory.get());
		}
		return map.put(secondaryKey, value);
	}

	@Override
	public void putAll(IMapMap<K1, K2, V> mapMap) {

		for (Entry<K1, Map<K2, V>> mapEntry: mapMap.entrySet()) {
			for (Entry<K2, V> valueEntry: mapEntry.getValue().entrySet()) {
				put(mapEntry.getKey(), valueEntry.getKey(), valueEntry.getValue());
			}
		}
	}

	@Override
	public void remove(K1 primaryKey) {

		mapMap.remove(primaryKey);
	}

	@Override
	public V remove(K1 primaryKey, K2 secondaryKey) {

		Map<K2, V> map = mapMap.get(primaryKey);
		if (map != null) {
			V value = map.remove(secondaryKey);
			if (map.isEmpty()) {
				mapMap.remove(primaryKey);
			}
			return value;
		} else {
			return null;
		}
	}

	@Override
	public int size() {

		return mapMap.size();
	}

	@Override
	public String toString() {

		return mapMap.toString();
	}
}

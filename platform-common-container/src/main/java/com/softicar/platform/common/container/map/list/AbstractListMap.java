package com.softicar.platform.common.container.map.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Map from a key to a list of values.
 *
 * @param <K>
 *            the key class
 * @param <V>
 *            the values in the sets
 * @author Boris Schaa
 * @author Oliver Richers
 */
public abstract class AbstractListMap<K, V> implements IListMap<K, V> {

	private final Map<K, List<V>> lists;

	public AbstractListMap(Map<K, List<V>> lists) {

		this.lists = lists;
	}

	// -------------------- read-only -------------------- //

	@Override
	public boolean containsKey(K key) {

		return lists.containsKey(key);
	}

	@Override
	public boolean isEmpty() {

		return lists.isEmpty();
	}

	@Override
	public int size() {

		return lists.size();
	}

	@Override
	public Set<K> keySet() {

		return lists.keySet();
	}

	@Override
	public Set<Map.Entry<K, List<V>>> entrySet() {

		return lists.entrySet();
	}

	@Override
	public Collection<List<V>> values() {

		return lists.values();
	}

	@Override
	public List<V> getList(K key) {

		List<V> list = lists.get(key);
		if (list == null) {
			lists.put(key, list = new ArrayList<>());
		}
		return list;
	}

	@Override
	public List<V> getListsContent() {

		List<V> contentList = new ArrayList<>();

		for (List<V> list: values()) {
			for (V v: list) {
				contentList.add(v);
			}
		}

		return contentList;
	}

	// -------------------- mutating -------------------- //

	@Override
	public void clear() {

		lists.clear();
	}

	@Override
	public List<V> removeKey(K key) {

		if (key == null) {
			return null;
		}
		List<V> result = lists.get(key);
		lists.remove(key);
		return result;
	}

	@Override
	public void addToList(K key, V value) {

		List<V> list = lists.get(key);
		if (list == null) {
			lists.put(key, list = new ArrayList<>());
		}
		list.add(value);
	}

	@Override
	public void addAllToList(K key, Collection<V> values) {

		List<V> list = lists.get(key);
		if (list == null) {
			lists.put(key, new ArrayList<>(values));
		} else {
			list.addAll(values);
		}
	}

	// -------------------- standard methods -------------------- //

	@Override
	public boolean equals(Object object) {

		if (object instanceof AbstractListMap<?, ?>) {
			return Objects.equals(lists, ((AbstractListMap<?, ?>) object).lists);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return lists.hashCode();
	}

	@Override
	public String toString() {

		String result = "";
		int maxLength = 0;
		for (K k: keySet()) {
			maxLength = Math.max(k.toString().length() + ("(h:" + k.hashCode() + ")").length(), maxLength);
		}

		for (K k: keySet()) {
			String line = k.toString();
			while (line.length() < maxLength) {
				line += " ";
			}
			line += "(h:" + k.hashCode() + ")";
			line += ":";
			if (getList(k).size() == 0) {
				result += line + " empty\n";
			} else {
				for (V v: getList(k)) {
					while (line.length() < maxLength + 2) {
						line += " ";
					}
					line += v.toString() + "(hash: " + v.hashCode() + ")\n";
					result += line;
					line = "";
				}
			}
		}

		return result;
	}
}

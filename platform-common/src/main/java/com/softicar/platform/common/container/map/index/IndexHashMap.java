package com.softicar.platform.common.container.map.index;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * Implementation of {@link IIndexMap} using {@link HashMap}.
 *
 * @author Oliver Richers
 */
public class IndexHashMap<T> implements IIndexMap<T> {

	private final HashMap<T, Integer> map;

	public IndexHashMap() {

		this(Collections.emptyList());
	}

	public IndexHashMap(Collection<? extends T> values) {

		this.map = new HashMap<>();

		int index = 0;
		for (T column: values) {
			map.put(column, index);
			index++;
		}
	}

	public int add(T value) {

		var newIndex = map.size();
		var oldIndex = map.putIfAbsent(value, newIndex);
		return oldIndex != null? oldIndex : newIndex;
	}

	@Override
	public int add(T value) {

		var newIndex = map.size();
		var oldIndex = map.putIfAbsent(value, newIndex);
		return oldIndex != null? oldIndex : newIndex;
	}

	@Override
	public Integer getIndex(T value) {

		return map.get(value);
	}

	@Override
	public Set<T> keySet() {

		return Collections.unmodifiableSet(map.keySet());
	}
}

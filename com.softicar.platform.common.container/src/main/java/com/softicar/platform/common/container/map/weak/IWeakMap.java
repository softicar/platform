package com.softicar.platform.common.container.map.weak;

import com.softicar.platform.common.core.annotations.Nullable;

public interface IWeakMap<K extends Comparable<K>, V> {

	@Nullable
	V put(K key, V value);

	@Nullable
	V get(K key);

	@Nullable
	V remove(K key);

	int size();

	void collect();
}

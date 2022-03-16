package com.softicar.platform.common.container.map.weak;

import com.softicar.platform.common.container.map.integer.IntKeySet;

class SubMap<V> extends IntKeySet<WeakIntEntry<V>> {

	public SubMap() {

		super();
	}

	public SubMap(int maxLoadPercentage) {

		super(maxLoadPercentage);
	}

	@Override
	protected int getKey(WeakIntEntry<V> entry) {

		return entry.getKey();
	}
}

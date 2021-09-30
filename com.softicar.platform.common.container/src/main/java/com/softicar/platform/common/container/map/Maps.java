package com.softicar.platform.common.container.map;

import java.util.Map;

/**
 * Utility methods for {@link Map}.
 *
 * @author Oliver Richers
 */
public class Maps {

	// -------------------- double -------------------- //

	public static <K> double getDoubleOrZero(Map<K, Double> map, K key) {

		return Maps.getDoubleOrDefault(map, key, 0.0);
	}

	public static <K> double getDoubleOrDefault(Map<K, Double> map, K key, double defaultValue) {

		Double value = map.get(key);
		return value != null? value : defaultValue;
	}

	public static <K> double addDouble(Map<K, Double> map, K key, double value) {

		Double oldValue = map.put(key, value);

		if (oldValue != null) {
			value += oldValue;
			map.put(key, value);
		}

		return value;
	}
}

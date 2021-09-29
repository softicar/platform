package com.softicar.platform.common.container.map.map;

/**
 * Utility methods for {@link IMapMap}.
 *
 * @author Oliver Richers
 */
public class MapMaps {

	public static <K1, K2> int getIntegerOrZero(IMapMap<K1, K2, Integer> map, K1 key1, K2 key2) {

		Integer value = map.get(key1, key2);
		return value != null? value : 0;
	}

	public static <K1, K2> int addInteger(IMapMap<K1, K2, Integer> map, K1 key1, K2 key2, int value) {

		Integer oldValue = map.put(key1, key2, value);

		if (oldValue != null) {
			value += oldValue;
			map.put(key1, key2, value);
		}

		return value;
	}

}

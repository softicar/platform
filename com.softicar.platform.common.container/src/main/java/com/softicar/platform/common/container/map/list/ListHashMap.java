package com.softicar.platform.common.container.map.list;

import java.util.HashMap;
import java.util.List;

/**
 * Map from a key to a list of values. Unlike {@link ListTreeMap}, here are
 * null-values permitted
 * 
 * @param <K>
 *            the key class
 * @param <V>
 *            the values in the sets
 * @author Boris Schaa
 * @author Oliver Richers
 */
public class ListHashMap<K, V> extends AbstractListMap<K, V> {

	public ListHashMap() {

		super(new HashMap<K, List<V>>());
	}

	@Override
	public String toString() {

		String result = "[";
		for (final K k: keySet()) {
			result += k;
			result += " [";
			for (final V v: getList(k)) {
				result += "[" + v + "]";
			}
			result += "] ";
		}
		result += "] ";

		return result;
	}
}

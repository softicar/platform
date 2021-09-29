package com.softicar.platform.common.container.map.list;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * Map from a key to a list of values.
 * 
 * @param <K>
 *            the key class
 * @param <V>
 *            the values in the sets (should be comparable)
 * @author Boris Schaa
 * @author Oliver Richers
 */
public class ListTreeMap<K extends Comparable<?>, V> extends AbstractListMap<K, V> {

	public ListTreeMap() {

		super(new TreeMap<K, List<V>>());
	}

	public ListTreeMap(Comparator<? super K> comparator) {

		super(new TreeMap<K, List<V>>(comparator));
	}
}

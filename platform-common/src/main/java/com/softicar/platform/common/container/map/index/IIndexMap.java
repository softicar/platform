package com.softicar.platform.common.container.map.index;

import java.util.Comparator;
import java.util.Set;

/**
 * A simple map that assigns an index to every key element.
 *
 * @author Oliver Richers
 */
public interface IIndexMap<T> extends Comparator<T> {

	/**
	 * Returns the index for the given key.
	 *
	 * @param key
	 *            the key element (never <i>null</i>)
	 * @return the index (may be <i>null</i>)
	 */
	Integer getIndex(T key);

	/**
	 * Adds the given key to this map and returns its index.
	 *
	 * @param key
	 *            the key to add (never <i>null</i>)
	 * @return the index of the given key; if the key is already contained, the
	 *         existing index is returned, otherwise a new index is generated
	 *         and returned
	 */
	int add(T key);

	/**
	 * Returns all keys contained in the map.
	 *
	 * @return all keys (never <i>null</i>)
	 */
	Set<T> keySet();

	@Override
	default int compare(T left, T right) {

		return getIndex(left).compareTo(getIndex(right));
	}
}

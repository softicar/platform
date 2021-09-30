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

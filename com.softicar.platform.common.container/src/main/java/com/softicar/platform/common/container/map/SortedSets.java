package com.softicar.platform.common.container.map;

import com.softicar.platform.common.container.collection.CollectionUtils;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

/**
 * Utility methods for {@link SortedSet}.
 *
 * @author Oliver Richers
 */
public class SortedSets {

	/**
	 * Converts the values in the given input collection.
	 *
	 * @param input
	 *            the input collection
	 * @param converter
	 *            the converter function
	 * @return a new {@link SortedSet} will all the converted values
	 */
	public static <T, R extends Comparable<? super R>> SortedSet<R> convertToSortedSet(Collection<T> input, Function<T, R> converter) {

		return CollectionUtils.convert(input, converter, TreeSet::new);
	}

}

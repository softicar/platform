package com.softicar.platform.common.container.collection;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Utility methods for {@link Collection}.
 *
 * @author Oliver Richers
 */
public class CollectionUtils {

	/**
	 * Converts the values in the given input collection.
	 *
	 * @param input
	 *            the input collection
	 * @param converter
	 *            the converter function
	 * @param factory
	 *            the target collection factory
	 * @return a new collection will all the converted values
	 */
	public static <T, R, C extends Collection<R>> C convert(Collection<T> input, Function<T, R> converter, Supplier<C> factory) {

		return input//
			.stream()
			.map(converter)
			.collect(Collectors.toCollection(factory));
	}
}

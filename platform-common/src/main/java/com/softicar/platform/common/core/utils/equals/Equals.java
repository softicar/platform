package com.softicar.platform.common.core.utils.equals;

import java.util.function.Function;

/**
 * This class provides a facility to implement {@link Object#equals}.
 *
 * @author Oliver Richers
 */
public abstract class Equals {

	/**
	 * Creates a new {@link IEqualsComparer} based on the given key extractor.
	 * <p>
	 * The given key extractor need not be null-safe, that is, it can assume
	 * that its parameter will never be null.
	 *
	 * @param keyExtractor
	 *            the key extractor (never null)
	 * @return a new {@link IEqualsComparer} that uses the given key extractor
	 *         (never null)
	 */
	public static <T, K> IEqualsComparer<T> comparing(Function<? super T, K> keyExtractor) {

		return new EqualsKeyExtractor<>(keyExtractor);
	}
}

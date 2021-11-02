package com.softicar.platform.common.core.utils.equals;

import java.util.function.Function;

/**
 * Interface to compare two objects for equality.
 *
 * @author Oliver Richers
 */
public interface IEqualsComparer<T> {

	/**
	 * Compares the two objects for equality.
	 * <p>
	 * If both objects are the same or if both objects are <i>null</i>,
	 * <i>true</i> will be returned without any further evaluation.
	 *
	 * @param first
	 *            the first object (may be null)
	 * @param second
	 *            the second object (may be null)
	 * @return <i>true</i> if both object are equal or both arguments are null;
	 *         <i>false</i> otherwise
	 */
	boolean compare(T first, T second);

	/**
	 * Compares the two objects for equality.
	 * <p>
	 * If both objects are the same or if both objects are <i>null</i>,
	 * <i>true</i> will be returned without any further evaluation.
	 * <p>
	 * This method is helpful when implementing {@link Object#equals(Object)}
	 * methods. The second object can be of arbitrary type. To be considered
	 * equal to the first object, the second object must implement the same
	 * class.
	 *
	 * @param thisObject
	 *            the first object (may be null)
	 * @param otherObject
	 *            the second object (may be null)
	 * @return <i>true</i> if both object are equal or both arguments are null;
	 *         <i>false</i> otherwise
	 */
	boolean compareToObject(T thisObject, Object otherObject);

	/**
	 * Extends this {@link IEqualsComparer} with the comparison of the values
	 * returned by the given key extractor.
	 * <p>
	 * The given key extractor need not be null-safe, that is, it can assume
	 * that its parameter will never be null.
	 *
	 * @param keyExtractor
	 *            the key extractor (never null)
	 * @return the extended {@link IEqualsComparer} (never null)
	 */
	<K> IEqualsComparer<T> comparing(Function<? super T, K> keyExtractor);

	/**
	 * Extends this {@link IEqualsComparer} with the given
	 * {@link IEqualsComparer}.
	 * <p>
	 * This and the given {@link IEqualsComparer} are combined using the logical
	 * <i>and</i> operation.
	 *
	 * @param otherComparer
	 *            the other comparer (never null)
	 * @return the extended comparer (never null)
	 */
	IEqualsComparer<T> comparing(IEqualsComparer<? super T> otherComparer);
}

package com.softicar.platform.common.container.matrix;

/**
 * Interface for matrix traits that specify how the matrix works.
 * 
 * @param <V>
 *            the value type of the matrix
 * @author Oliver Richers
 */
public interface IMatrixTraits<V> {

	/**
	 * Returns the default value, that should be returned by
	 * {@link IMatrix#getValue} if the value is not set.
	 * 
	 * @return the default value
	 */
	V getDefaultValue();

	/**
	 * Returns the sum of the specified values.
	 * 
	 * @return sum of valueA and valueB
	 */
	V plus(V valueA, V valueB);
}

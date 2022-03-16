package com.softicar.platform.common.container.matrix.traits;

import com.softicar.platform.common.container.matrix.IMatrixTraits;

/**
 * Simple matrix traits for {@link Object}.
 * <p>
 * Objects cannot be added and the default value is <i>null</i>.
 *
 * @author Oliver Richers
 */
public class ObjectMatrixTraits<V> implements IMatrixTraits<V> {

	@Override
	public V getDefaultValue() {

		return null;
	}

	@Override
	public V plus(V valueA, V valueB) {

		throw new UnsupportedOperationException();
	}
}

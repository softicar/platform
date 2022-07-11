package com.softicar.platform.db.structure.comparison.helper;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import java.util.Objects;
import java.util.function.Function;

public interface DbStructures {

	/**
	 * Applies the given function to both of the given instances, and checks the
	 * resulting values for equality. Throws an exception if the resulting
	 * values are not equal. Returns the value of the first instance otherwise.
	 *
	 * @param referenceInstance
	 * @param sampleInstance
	 * @param propertyValueGetter
	 * @param propertyName
	 * @return the value of the first instance, if values from both instances
	 *         are equal (may be null)
	 */
	static <I, T> T getEqualPropertyValueOrThrow(I referenceInstance, I sampleInstance, Function<I, T> propertyValueGetter, String propertyName) {

		T value = propertyValueGetter.apply(referenceInstance);
		if (!Objects.equals(value, propertyValueGetter.apply(sampleInstance))) {
			throw new SofticarDeveloperException("Incompatible instances: Expected equal %s.", IDbColumnStructure.class.getSimpleName(), propertyName);
		} else {
			return value;
		}
	}

	static <I, T> void assertEqualPropertyValue(I referenceInstance, I sampleInstance, Function<I, T> propertyValueGetter, String propertyName) {

		getEqualPropertyValueOrThrow(referenceInstance, sampleInstance, propertyValueGetter, propertyName);
	}

	static <I, T> T getUniquePropertyValueOrThrow(I referenceInstance, I sampleInstance, Function<I, T> propertyValueGetter, String propertyName) {

		if (referenceInstance != null && sampleInstance != null) {
			return getEqualPropertyValueOrThrow(referenceInstance, sampleInstance, propertyValueGetter, propertyName);
		} else if (referenceInstance != null) {
			return propertyValueGetter.apply(referenceInstance);
		} else if (sampleInstance != null) {
			return propertyValueGetter.apply(sampleInstance);
		} else {
			throw new IllegalArgumentException();
		}
	}
}

package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.common.container.tuple.Tuple2;
import java.util.Optional;

/**
 * Setter interface for field values of type {@link Optional}.
 *
 * @author Oliver Richers
 * @see AbstractTransientOptionalField
 */
@FunctionalInterface
public interface IOptionalValueSetter<O, V> {

	/**
	 * Sets the field value to the specified value.
	 *
	 * @param object
	 *            the object that the field value belongs to (never null)
	 * @param value
	 *            the {@link Optional} value to set (never null)
	 */
	void set(O object, Optional<V> value);

	/**
	 * Sets the field value to the specified null-able value.
	 * <p>
	 * If the given value is null, this will effectively set the field value to
	 * {@link Optional#empty()}.
	 *
	 * @param object
	 *            the object that the field value belongs to (never null)
	 * @param value
	 *            the value which may be null
	 */
	default void setNullable(O object, V value) {

		set(object, Optional.ofNullable(value));
	}

	default void set(Tuple2<O, V> objectAndValue) {

		setNullable(objectAndValue.get0(), objectAndValue.get1());
	}

	default void set(Pair<O, V> objectAndValue) {

		setNullable(objectAndValue.getFirst(), objectAndValue.getSecond());
	}
}

package com.softicar.platform.db.runtime.transients;

/**
 * Setter interface for field values.
 *
 * @author Oliver Richers
 * @see AbstractTransientObjectField
 */
@FunctionalInterface
public interface IValueSetter<O, V> {

	/**
	 * Sets the field value to the specified value.
	 *
	 * @param object
	 *            the object that the field value belongs to (never null)
	 * @param value
	 *            the value to set (never null)
	 */
	void set(O object, V value);
}

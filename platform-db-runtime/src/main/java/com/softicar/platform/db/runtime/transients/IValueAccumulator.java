package com.softicar.platform.db.runtime.transients;

/**
 * Setter interface for all accumulative field values.
 * <p>
 * Accumulative field values are determined by adding their elements together.
 *
 * @author Oliver Richers
 * @see AbstractTransientAccumulativeField
 */
@FunctionalInterface
public interface IValueAccumulator<O, E> {

	/**
	 * Adds the given element to the field value.
	 *
	 * @param object
	 *            the object that the field value belongs to (never null)
	 * @param element
	 *            the element to add (never null)
	 */
	void add(O object, E element);

	/**
	 * Adds the given elements to the field value.
	 *
	 * @param object
	 *            the object that the field value belongs to (never null)
	 * @param elements
	 *            the elements to add (never null)
	 */
	default void addAll(O object, Iterable<? extends E> elements) {

		for (E element: elements) {
			add(object, element);
		}
	}
}

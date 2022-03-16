package com.softicar.platform.common.core.untranslatable;

/**
 * Represents a string that is not translatable.
 *
 * @author Daniel Klose
 */
@FunctionalInterface
public interface IRawString {

	/**
	 * Returns the string which this {@link IRawString} instance represents.
	 *
	 * @return the raw string (never null)
	 */
	String getString();

	/**
	 * This method is essentially the same as {@link IRawString#getString()}
	 * which only exists to prevent errors when trying to use
	 * {@link IRawString#format(Object...)} without giving any arguments.
	 *
	 * @return the raw string (never null)
	 */
	default String format() {

		return getString();
	}

	/**
	 * If the raw string this {@link IRawString} represents has any wildcards,
	 * format and return it with the given arguments.
	 *
	 * @param arguments
	 *            the arguments to fill the wildcards with
	 * @return the raw string, formatted with filled arguments (never null)
	 */
	default String format(Object...arguments) {

		return String.format(getString(), arguments);
	}
}

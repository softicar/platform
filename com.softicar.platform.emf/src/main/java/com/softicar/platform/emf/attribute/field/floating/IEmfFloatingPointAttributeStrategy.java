package com.softicar.platform.emf.attribute.field.floating;

public interface IEmfFloatingPointAttributeStrategy<V> {

	/**
	 * Formats the given value into a string.
	 *
	 * @param value
	 *            the value (never null)
	 * @return the formatted value (never null)
	 */
	String formatValue(V value);

	/**
	 * Checks if the given text can be parsed into a valid value.
	 *
	 * @param text
	 *            the text to inspect (never null)
	 * @return <i>true</i> if the given text can be parsed; <i>false</i>
	 *         otherwise
	 */
	boolean isParseable(String text);

	/**
	 * Parses the given text into a value.
	 *
	 * @param text
	 *            the text to parse (never null)
	 * @return the parsed value (never null)
	 */
	V parseValue(String text);
}

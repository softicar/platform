package com.softicar.platform.dom.attribute;

/**
 * Interface of all node attributes.
 * <p>
 * The value of an attribute can either be quoted or unquoted. Strings for
 * example are always quoted, while integers, booleans and null values are
 * always unquoted.
 * <p>
 * Instances of this interface are immutable and comparable. The comparison does
 * not only compare the name of the attributes, but also compares the value and
 * quoting flag.
 *
 * @author Oliver Richers
 */
public interface IDomAttribute extends Comparable<IDomAttribute> {

	/**
	 * Returns the name of this attribute.
	 */
	String getName();

	/**
	 * Returns the defined value of this attribute as string.
	 */
	String getValue();

	/**
	 * Returns the value as used in the JavaScript code.
	 * <p>
	 * This returns either the same as {@link #getValue()} or a quoted version
	 * of it, depending on the value of {@link #mustBeQuoted()}.
	 */
	String getValue_JS();

	/**
	 * Returns whether the value of this attribute needs to be quoted in
	 * JavaScript.
	 *
	 * @return true if quoting necessary
	 */
	boolean mustBeQuoted();
}

package com.softicar.platform.common.core.utils;

/**
 * Simple class to ignore values and prevent unnecessary warnings.
 * <p>
 * When writing tests, it is sometimes necessary to instantiate new objects
 * without using them, e.g. when testing that a constructor throws a desired
 * exception.
 *
 * @author Oliver Richers
 */
public class DevNull {

	/**
	 * Swallows and returns the given object.
	 * <p>
	 * The return value can be ignored.
	 *
	 * @param object
	 *            the object to swallow
	 * @return the given object
	 */
	public static <T> T swallow(T object) {

		return object;
	}
}

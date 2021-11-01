package com.softicar.platform.common.string.formatting;

/**
 * This class provides generic formatting methods.
 * 
 * @author Oliver Richers
 */
public class Formatting {

	/**
	 * This method is similar to the format method of the {@link String} class,
	 * but with relaxed requirements.
	 * <p>
	 * If the given list of arguments is empty, no formatting will be attempted
	 * and the format string is returned as is. This behavior is useful for
	 * backwards compatibility when an existing method is extended with
	 * formatting capabilities. Without this check, formatting characters not
	 * intended for formatting will cause an exception to be thrown.
	 * Additionally, if the given format string is <i>null</i> and the argument
	 * list is empty, this method simply returns <i>null</i> again.
	 * 
	 * @param format
	 *            the format string
	 * @param args
	 *            the formatting arguments
	 * @return the formatted string
	 */
	public static String format(String format, Object...args) {

		if (args.length == 0) {
			return format;
		}

		return String.format(format, args);
	}
}

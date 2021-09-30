package com.softicar.platform.common.core.number.parser;

import com.softicar.platform.common.core.utils.DevNull;
import java.util.Optional;

/**
 * Utility methods for parsing {@link Integer}.
 *
 * @author Oliver Richers
 */
public class IntegerParser {

	/**
	 * Checks if the given string is a valid {@link Integer} number.
	 * <p>
	 * If the given string is <i>null</i>, this method returns <i>false</i>.
	 * <p>
	 * This method also returns <i>false</i> if the actual integer cannot be
	 * represented by {@link Integer}, that is, it is less than
	 * {@link Integer#MIN_VALUE} or greater than {@link Integer#MAX_VALUE}.
	 *
	 * @param string
	 *            the string to parse (may be null)
	 * @return true if the given string is a valid {@link Integer} literal
	 */
	public static boolean isInteger(String string) {

		return parse(string).isPresent();
	}

	/**
	 * Tries to parse the given string into an {@link Integer}.
	 * <p>
	 * This method returns {@link Optional#empty()} if the given string cannot
	 * be parsed into a valid integer in the range [{@link Integer#MIN_VALUE},
	 * {@link Integer#MAX_VALUE}].
	 * <p>
	 * If the given string is <i>null</i>, this method also returns
	 * {@link Optional#empty()}.
	 *
	 * @param string
	 *            the string to parse (may be null)
	 * @return a {@link Optional} holding the parsed integer value or not
	 */
	public static Optional<Integer> parse(String string) {

		if (string == null) {
			return Optional.empty();
		}

		try {
			return Optional.of(Integer.parseInt(string));
		} catch (NumberFormatException exception) {
			DevNull.swallow(exception);
			return Optional.empty();
		}
	}

	public static Integer parseInteger(String string) {

		return parse(string).orElse(null);
	}
}

package com.softicar.platform.common.core.number.parser;

import com.softicar.platform.common.core.utils.DevNull;
import java.util.Optional;

/**
 * Utility methods for parsing {@link Long}.
 *
 * @author Oliver Richers
 */
public class LongParser {

	/**
	 * Checks if the given string is a valid {@link Long} number.
	 * <p>
	 * If the given string is <i>null</i>, this method returns <i>false</i>.
	 *
	 * @param string
	 *            the string to parse (may be null)
	 * @return true if the given string is a valid {@link Long} literal
	 */
	public static boolean isLong(String string) {

		return parse(string).isPresent();
	}

	/**
	 * Tries to parse the given string into a {@link Long}.
	 * <p>
	 * This method returns {@link Optional#empty()} if the given string cannot
	 * be parsed into a valid integer value in the range [{@link Long#MIN_VALUE}
	 * , {@link Long#MAX_VALUE}].
	 * <p>
	 * If the given string is <i>null</i>, this method also returns
	 * {@link Optional#empty()}.
	 *
	 * @param string
	 *            the string to parse (may be null)
	 * @return a {@link Optional} holding the parsed integer value or not
	 */
	public static Optional<Long> parse(String string) {

		if (string == null) {
			return Optional.empty();
		}

		try {
			return Optional.of(Long.parseLong(string));
		} catch (NumberFormatException exception) {
			DevNull.swallow(exception);
			return Optional.empty();
		}
	}

	public static Long parseLong(String string) {

		return parse(string).orElse(null);
	}
}

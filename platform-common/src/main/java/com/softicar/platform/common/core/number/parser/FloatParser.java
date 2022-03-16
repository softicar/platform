package com.softicar.platform.common.core.number.parser;

import com.softicar.platform.common.core.utils.DevNull;
import java.util.Optional;

/**
 * Utility methods for parsing {@link Float}.
 *
 * @author Oliver Richers
 */
public class FloatParser {

	/**
	 * Checks if the given string is a valid {@link Float} number.
	 * <p>
	 * If the given string is <i>null</i>, this method returns <i>false</i>.
	 *
	 * @param string
	 *            the string to parse (may be null)
	 * @return true if the given string is a valid floating point literal
	 */
	public static boolean isFloat(String string) {

		return parse(string).isPresent();
	}

	/**
	 * Tries to parse the given string into a {@link Float}.
	 * <p>
	 * This method returns {@link Optional#empty()} if the given string cannot
	 * be parsed into a valid floating-point value.
	 * <p>
	 * If the given string is <i>null</i>, this method also returns
	 * {@link Optional#empty()}.
	 *
	 * @param string
	 *            the string to parse (may be null)
	 * @return a {@link Optional} holding the parsed floating-point value or not
	 */
	public static Optional<Float> parse(String string) {

		if (string == null) {
			return Optional.empty();
		}

		try {
			return Optional.of(Float.parseFloat(string));
		} catch (NumberFormatException exception) {
			DevNull.swallow(exception);
			return Optional.empty();
		}
	}

	public static Float parseFloat(String string) {

		return parse(string).orElse(null);
	}
}

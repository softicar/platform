package com.softicar.platform.common.string;

/**
 * String escaping utility methods.
 * <p>
 * FIXME the list of characters that are escaped is rather arbitrary (#32352)
 *
 * @author Oliver Richers
 */
public class Escaping {

	/**
	 * Replaces control characters in the given string with their escaped
	 * version.
	 *
	 * @param string
	 *            the string to escape (may be null)
	 * @return the escaped string (null if specified string was null)
	 */
	public static String getEscaped(String string) {

		// quick exit if null
		if (string == null) {
			return string;
		}

		StringBuilder result = new StringBuilder();

		for (int i = 0; i != string.length(); ++i) {
			char c = string.charAt(i);
			switch (c) {
			case '\b':
				result.append("\\b");
				break;
			case '\f':
				result.append("\\f");
				break;
			case '\n':
				result.append("\\n");
				break;
			case '\r':
				result.append("\\r");
				break;
			case '\t':
				result.append("\\t");
				break;
			case '\\':
				result.append("\\\\");
				break;
			case '\'':
				result.append("\\'");
				break;
			case '\"':
				result.append("\\\"");
				break;
			case '\u00A3':
				// pound sign
				result.append("\\u00A3");
				break;
			case '\u2028':
				// unicode line separator
				result.append("\\u2028");
				break;
			case '\u2029':
				// unicode paragraph separator
				result.append("\\u2029");
				break;
			case '\u20ac':
				// euro sign
				result.append("\\u20ac");
				break;
			default:
				result.append(c);
				break;
			}
		}
		return result.toString();
	}
}

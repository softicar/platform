package com.softicar.platform.common.string;

import java.util.Objects;

public class Substring {

	/**
	 * Uses the given prefix and suffix to extract a substring from the given
	 * text.
	 * <p>
	 * For the prefix, the first match is found. For the suffix, the last match
	 * is found. Hence, this method extracts the longest possible substring from
	 * the given text.
	 * <p>
	 * If the given text is empty, or if there is no match for either the prefix
	 * or the suffix, or if there is a suffix match before a prefix match, an
	 * empty {@link String} is returned.
	 * <p>
	 * An empty prefix or suffix matches the first or last index in the given
	 * text, respectively. If both affixes are empty, the given text is
	 * returned.
	 *
	 * @param text
	 *            the {@link String} to extract a substring from (never
	 *            <i>null</i>)
	 * @param prefix
	 *            the prefix before the substring to extract (never <i>null</i>)
	 * @param suffix
	 *            the suffix after the substring to extract (never <i>null</i>)
	 * @return the enclosed {@link String} (never <i>null</i>)
	 * @throws NullPointerException
	 *             if either of the given parameters is <i>null</i>
	 */
	public static String between(String text, String prefix, String suffix) {

		Objects.requireNonNull(text);
		Objects.requireNonNull(prefix);
		Objects.requireNonNull(suffix);

		int prefixIndex = text.indexOf(prefix);
		int suffixIndex = text.lastIndexOf(suffix);
		if (prefixIndex >= 0 && suffixIndex >= 0) {
			int begin = prefixIndex + prefix.length();
			int end = suffixIndex;
			if (begin < end) {
				return text.substring(begin, end);
			}
		}
		return "";
	}
}

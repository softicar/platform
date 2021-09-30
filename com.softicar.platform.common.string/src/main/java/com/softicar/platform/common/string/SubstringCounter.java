package com.softicar.platform.common.string;

public class SubstringCounter {

	/**
	 * Determines the number of occurrences of the given "needle" {@link String}
	 * in the given "haystack" {@link String}.
	 * <p>
	 * If either of the parameters is <i>null</i> or empty, 0 is returned.
	 * <p>
	 * This method is case sensitive.
	 *
	 * @param haystack
	 *            the {@link String} to search for occurrences (may be
	 *            <i>null</i>)
	 * @param needle
	 *            the occurring {@link String} (may be <i>null</i>)
	 * @return the number of occurrences
	 */
	public static int countOccurrences(String haystack, String needle) {

		if (isEmpty(haystack) || isEmpty(needle)) {
			return 0;
		}

		int lastIndex = 0;
		int count = 0;
		while (lastIndex != -1) {
			lastIndex = haystack.indexOf(needle, lastIndex);
			if (lastIndex != -1) {
				count++;
				lastIndex += needle.length();
			}
		}
		return count;
	}

	private static boolean isEmpty(String string) {

		return string == null || string.length() == 0;
	}
}

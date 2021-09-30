package com.softicar.platform.common.string.plural;

/**
 * Determines the plural of a single word.
 * <p>
 * Disclaimer: this class does not aim to determine the perfectly correct plural
 * form in any case. Only the most common rules are applied and the most common
 * irregular plural forms are supported.
 * <p>
 * If regular and irregular plural forms are allowed, the regular form is
 * preferred.
 *
 * @author Oliver Richers
 */
public class PluralDeterminer {

	private final String word;

	/**
	 * The singular word.
	 *
	 * @param word
	 *            the word (never null)
	 */
	public PluralDeterminer(String word) {

		this.word = word;
	}

	/**
	 * Returns the plural form of the given word.
	 *
	 * @return the plural form (never null)
	 */
	public String getPlural() {

		String plural = InternalIrregularPluralMap.getInstance().get(word);
		if (plural != null) {
			return plural;
		}

		int length = word.length();
		if (length >= 2) {
			char c1 = word.charAt(length - 2);
			char c2 = word.charAt(length - 1);
			switch (c2) {
			case 'y':
				if (!isVowel(c1)) {
					return word.substring(0, length - 1) + "ies";
				}
				break;
			case 'h':
				if (c1 == 'c' || c1 == 's') {
					return word + "es";
				}
				break;
			case 's':
				return word + "es";
			}
		}
		return word + 's';
	}

	private static boolean isVowel(char c) {

		switch (c) {
		case 'a':
		case 'e':
		case 'i':
		case 'o':
		case 'u':
			return true;
		default:
			return false;
		}
	}
}

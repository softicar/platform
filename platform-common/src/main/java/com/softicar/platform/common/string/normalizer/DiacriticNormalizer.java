package com.softicar.platform.common.string.normalizer;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Removes diacritics (aka. diacritical marks) from text.
 *
 * @author Alexander Schmidt
 */
public class DiacriticNormalizer {

	private static final Pattern ACCENTS_PATTERN = Pattern.compile("\\p{M}");

	/**
	 * Removes diacritics from the characters in the given text.
	 * <p>
	 * Relies on Unicode decomposition mappings.
	 *
	 * @param text
	 *            the text to strip of diacritical marks (never <i>null</i>)
	 * @return the given text, stripped of diacritical marks (never <i>null</i>)
	 */
	public String normalize(String text) {

		return ACCENTS_PATTERN//
			.matcher(Normalizer.normalize(text, Normalizer.Form.NFKD))
			.replaceAll("");
	}
}

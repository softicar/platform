package com.softicar.platform.common.string.normalizer;

import java.util.HashMap;
import java.util.Map;

/**
 * A cache for {@link DiacriticNormalizer}.
 *
 * @author Alexander Schmidt
 */
public class DiacriticNormalizationCache {

	private final DiacriticNormalizer normalizer;
	private final Map<String, String> normalizedTextMap;

	/**
	 * Constructs a new {@link DiacriticNormalizationCache}.
	 */
	public DiacriticNormalizationCache() {

		this.normalizer = new DiacriticNormalizer();
		this.normalizedTextMap = new HashMap<>();
	}

	/**
	 * Uses {@link DiacriticNormalizer#normalize(String)} to remove diacritics
	 * from the characters in the given text.
	 * <p>
	 * The result is cached, so that {@link DiacriticNormalizer} will not be
	 * invoked again for the same text.
	 *
	 * @param text
	 *            the text to strip of diacritical marks (never <i>null</i>)
	 * @return the given text, stripped of diacritical marks (never <i>null</i>)
	 */
	public String normalize(String text) {

		return normalizedTextMap.computeIfAbsent(text, normalizer::normalize);
	}

	/**
	 * Clears all previously cached normalized texts.
	 */
	public void clear() {

		normalizedTextMap.clear();
	}
}

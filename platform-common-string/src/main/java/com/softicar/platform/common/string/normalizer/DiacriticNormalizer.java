package com.softicar.platform.common.string.normalizer;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Map;
import java.util.TreeMap;

public class DiacriticNormalizer {

	/**
	 * Some characters (e.g. 'đ') possess no unicode decomposition mapping entry
	 * (e.g. to 'd'). This map therefore provides manual, explicit decomposition
	 * mappings.
	 */
	private static final Map<Character, Character> DECOMPOSITION_MAP = new TreeMap<>();

	static {
		DECOMPOSITION_MAP.put('đ', 'd');
		DECOMPOSITION_MAP.put('Đ', 'D');
		DECOMPOSITION_MAP.put('ƒ', 'f');
		DECOMPOSITION_MAP.put('Ƒ', 'F');
	}

	public String normalize(String string) {

		return applyCustomDecomposition(removeDiacritics(string));
	}

	private String removeDiacritics(String string) {

		return Normalizer.normalize(string, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	private String applyCustomDecomposition(String string) {

		StringBuilder output = new StringBuilder();
		for (char character: string.toCharArray()) {
			Character replacement = DECOMPOSITION_MAP.get(character);
			output.append(replacement != null? replacement : character);
		}
		return output.toString();
	}
}

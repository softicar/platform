package com.softicar.platform.dom.elements.input.auto.matching;

import com.softicar.platform.common.string.normalizer.DiacriticNormalizer;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

class AutoCompleteMatchEntryComparator<E extends Entry<String, AutoCompleteWordMatches>> implements Comparator<E> {

	private final boolean ignoreDiacritics;
	private final String perfectMatchIdentifier;
	private final DiacriticNormalizer normalizer;

	public AutoCompleteMatchEntryComparator(boolean ignoreDiacritics, String perfectMatchIdentifier) {

		this.ignoreDiacritics = ignoreDiacritics;
		this.perfectMatchIdentifier = perfectMatchIdentifier;
		this.normalizer = new DiacriticNormalizer();
	}

	@Override
	public int compare(E first, E second) {

		return Comparator
			.comparing((E dummy) -> true)
			.thenComparing(Comparator.comparing(this::perfectMatch).reversed())
			.thenComparing(Comparator.comparing(this::identifierStartsWithPattern).reversed())
			.thenComparing(Comparator.comparing(this::identifierAndPatternCommonPrefixLength).reversed())
			.thenComparing(Comparator.comparing(this::getConsecutiveWordMatchCount).reversed())
			.thenComparing(Comparator.comparing(this::getNormalizedIdentifier))
			.compare(first, second);
	}

	private boolean perfectMatch(E entry) {

		String identifier = getNormalizedIdentifier(entry);
		return identifier.equals(perfectMatchIdentifier);
	}

	private boolean identifierStartsWithPattern(E entry) {

		String identifier = getNormalizedIdentifier(entry);
		String pattern = getNormalizedPattern(entry);
		return identifier.startsWith(pattern);
	}

	private int getConsecutiveWordMatchCount(E entry) {

		List<String> searchWords = entry.getValue().getSearchWords();
		List<String> matchedWords = entry.getValue().getConsecutiveMatchedWords();

		int matchCount = 0;
		for (int i = 0; i < Math.min(searchWords.size(), matchedWords.size()); i++) {
			String searchWord = normalize(searchWords.get(i));
			String matchedWord = normalize(matchedWords.get(i));
			if (searchWord.equals(matchedWord)) {
				matchCount++;
			} else {
				break;
			}
		}
		return matchCount;
	}

	private int identifierAndPatternCommonPrefixLength(E entry) {

		String identifier = getNormalizedIdentifier(entry);
		String pattern = getNormalizedPattern(entry);

		int commonPrefixLength = 0;
		for (int i = 0; i < Math.min(identifier.length(), pattern.length()); i++) {
			if (identifier.charAt(i) == pattern.charAt(i)) {
				commonPrefixLength++;
			} else {
				break;
			}
		}
		return commonPrefixLength;
	}

	private String getNormalizedIdentifier(E entry) {

		return normalize(entry.getKey()).toLowerCase();
	}

	private String getNormalizedPattern(E entry) {

		return normalize(entry.getValue().getSearchPattern());
	}

	private String normalize(String text) {

		return ignoreDiacritics? normalizer.normalize(text) : text;
	}
}

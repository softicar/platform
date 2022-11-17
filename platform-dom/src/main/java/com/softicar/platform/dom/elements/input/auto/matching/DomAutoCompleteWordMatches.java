package com.softicar.platform.dom.elements.input.auto.matching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class DomAutoCompleteWordMatches {

	private final String searchPattern;
	private final List<String> searchWords;
	private final Map<String, List<DomAutoCompleteMatchRange>> wordToMatchRanges;
	private final List<String> matchedWords;

	public DomAutoCompleteWordMatches(String searchPattern, List<String> searchWords) {

		this.searchPattern = Objects.requireNonNull(searchPattern);
		this.searchWords = Objects.requireNonNull(searchWords);
		this.wordToMatchRanges = new HashMap<>();
		this.matchedWords = new ArrayList<>();
	}

	public DomAutoCompleteWordMatches put(String word, List<DomAutoCompleteMatchRange> matchRanges) {

		wordToMatchRanges.put(word, matchRanges);
		matchedWords.add(word);
		return this;
	}

	public String getSearchPattern() {

		return searchPattern;
	}

	public List<String> getSearchWords() {

		return searchWords;
	}

	public List<DomAutoCompleteMatchRange> getAllMatchRanges() {

		return wordToMatchRanges//
			.values()
			.stream()
			.flatMap(Collection::stream)
			.sorted(compareByIndexes())
			.collect(Collectors.toList());
	}

	public List<String> getConsecutiveMatchedWords() {

		return matchedWords//
			.stream()
			.sorted(Comparator.comparing(this::getFirstIndex))
			.collect(Collectors.toList());
	}

	public boolean isEmpty() {

		return wordToMatchRanges.isEmpty();
	}

	private Comparator<DomAutoCompleteMatchRange> compareByIndexes() {

		return Comparator//
			.comparing(DomAutoCompleteMatchRange::getLowerIndex)
			.thenComparing(DomAutoCompleteMatchRange::getUpperIndex);
	}

	private int getFirstIndex(String matchedWord) {

		return wordToMatchRanges//
			.get(matchedWord)
			.stream()
			.mapToInt(DomAutoCompleteMatchRange::getLowerIndex)
			.min()
			.getAsInt();
	}
}

package com.softicar.platform.dom.elements.input.auto.matching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class AutoCompleteWordMatches {

	private final String searchPattern;
	private final List<String> searchWords;
	private final Map<String, List<AutoCompleteMatchRange>> wordToMatchRanges;
	private final List<String> matchedWords;

	public AutoCompleteWordMatches(String searchPattern, List<String> searchWords) {

		this.searchPattern = Objects.requireNonNull(searchPattern);
		this.searchWords = Objects.requireNonNull(searchWords);
		this.wordToMatchRanges = new HashMap<>();
		this.matchedWords = new ArrayList<>();
	}

	public AutoCompleteWordMatches put(String word, List<AutoCompleteMatchRange> matchRanges) {

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

	public List<AutoCompleteMatchRange> getAllMatchRanges() {

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

	private Comparator<AutoCompleteMatchRange> compareByIndexes() {

		return Comparator//
			.comparing(AutoCompleteMatchRange::getLowerIndex)
			.thenComparing(AutoCompleteMatchRange::getUpperIndex);
	}

	private int getFirstIndex(String matchedWord) {

		return wordToMatchRanges//
			.get(matchedWord)
			.stream()
			.mapToInt(AutoCompleteMatchRange::getLowerIndex)
			.min()
			.getAsInt();
	}
}

package com.softicar.platform.dom.elements.input.auto.matching;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

class DomAutoCompleteMatchesBuilder<V> {

	private final boolean ignoreDiacritics;
	private final Map<String, V> identifierToValue;
	private final Map<String, DomAutoCompleteWordMatches> identifierToWordMatches;
	private String perfectMatchIdentifier;

	public DomAutoCompleteMatchesBuilder(Map<String, V> identifierToValueMap, boolean ignoreDiacritics) {

		this.ignoreDiacritics = ignoreDiacritics;
		this.identifierToValue = Objects.requireNonNull(identifierToValueMap);
		this.identifierToWordMatches = new HashMap<>();
		this.perfectMatchIdentifier = null;
	}

	public DomAutoCompleteMatchesBuilder<V> addMatches(String identifier, DomAutoCompleteWordMatches wordMatches) {

		Objects.requireNonNull(identifier);
		Objects.requireNonNull(wordMatches);
		assertValueExists(identifier);

		identifierToWordMatches.put(identifier, wordMatches);
		return this;
	}

	public DomAutoCompleteMatchesBuilder<V> setPerfectMatch(String identifier) {

		Objects.requireNonNull(identifier);
		assertValueExists(identifier);

		this.perfectMatchIdentifier = identifier;
		return this;
	}

	public DomAutoCompleteMatches<V> build(int limit) {

		var matches = new DomAutoCompleteMatches<V>();
		// TODO try to pull the comparator out even further, to reuse the cache as much as possible
		var comparator = new DomAutoCompleteMatchEntryComparator<>(ignoreDiacritics, perfectMatchIdentifier);
		identifierToWordMatches//
			.entrySet()
			.stream()
			.sorted(comparator)
			.map(this::createMatch)
			.limit(limit)
			.forEach(matches::add);
		Optional//
			.ofNullable(perfectMatchIdentifier)
			.map(identifierToValue::get)
			.ifPresent(matches::setPerfectMatchValue);
		return matches;
	}

	private void assertValueExists(String identifier) {

		if (!identifierToValue.containsKey(identifier)) {
			throw new IllegalArgumentException();
		}
	}

	private DomAutoCompleteMatch<V> createMatch(Entry<String, DomAutoCompleteWordMatches> entry) {

		V value = identifierToValue.get(entry.getKey());
		var matches = entry.getValue();
		return new DomAutoCompleteMatch<>(value, matches);
	}
}

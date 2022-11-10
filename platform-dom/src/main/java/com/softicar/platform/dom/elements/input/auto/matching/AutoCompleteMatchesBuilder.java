package com.softicar.platform.dom.elements.input.auto.matching;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

class AutoCompleteMatchesBuilder<V> {

	private final boolean ignoreDiacritics;
	private final Map<String, V> identifierToValue;
	private final Map<String, AutoCompleteWordMatches> identifierToWordMatches;
	private String perfectMatchIdentifier;

	public AutoCompleteMatchesBuilder(Map<String, V> identifierToValueMap, boolean ignoreDiacritics) {

		this.ignoreDiacritics = ignoreDiacritics;
		this.identifierToValue = Objects.requireNonNull(identifierToValueMap);
		this.identifierToWordMatches = new HashMap<>();
		this.perfectMatchIdentifier = null;
	}

	public AutoCompleteMatchesBuilder<V> addMatches(String identifier, AutoCompleteWordMatches wordMatches) {

		Objects.requireNonNull(identifier);
		Objects.requireNonNull(wordMatches);
		assertValueExists(identifier);

		identifierToWordMatches.put(identifier, wordMatches);
		return this;
	}

	public AutoCompleteMatchesBuilder<V> setPerfectMatch(String identifier) {

		Objects.requireNonNull(identifier);
		assertValueExists(identifier);

		this.perfectMatchIdentifier = identifier;
		return this;
	}

	public AutoCompleteMatches<V> build(int limit) {

		var matches = new AutoCompleteMatches<V>();
		identifierToWordMatches//
			.entrySet()
			.stream()
			.sorted(new AutoCompleteMatchEntryComparator<>(ignoreDiacritics, perfectMatchIdentifier))
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

	private AutoCompleteMatch<V> createMatch(Entry<String, AutoCompleteWordMatches> entry) {

		V value = identifierToValue.get(entry.getKey());
		var matches = entry.getValue();
		return new AutoCompleteMatch<>(value, matches);
	}
}

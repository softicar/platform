package com.softicar.platform.dom.elements.input.auto.matching;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

class DomAutoCompleteMatchesBuilder<V> {

	private final boolean ignoreDiacritics;
	private final Map<String, V> knownIdentifierToValue;
	private final Set<String> knownIdentifiers;
	private final Map<String, DomAutoCompleteWordMatches> identifierToWordMatches;
	private String perfectMatchIdentifier;

	public DomAutoCompleteMatchesBuilder(Map<String, V> identifierToValueMap, boolean ignoreDiacritics) {

		this.ignoreDiacritics = ignoreDiacritics;
		this.knownIdentifierToValue = Objects.requireNonNull(identifierToValueMap);
		this.knownIdentifiers = createKnownIdentifiersSet(identifierToValueMap);
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
		var comparator = new DomAutoCompleteMatchEntryComparator<>(ignoreDiacritics, perfectMatchIdentifier);
		identifierToWordMatches//
			.entrySet()
			.stream()
			.sorted(comparator)
			.limit(limit)
			.map(this::createMatch)
			.forEach(matches::add);
		Optional//
			.ofNullable(perfectMatchIdentifier)
			.map(knownIdentifierToValue::get)
			.ifPresent(matches::setPerfectMatchValue);
		return matches;
	}

	private void assertValueExists(String identifier) {

		if (!knownIdentifiers.contains(identifier)) {
			throw new IllegalArgumentException();
		}
	}

	private DomAutoCompleteMatch<V> createMatch(Entry<String, DomAutoCompleteWordMatches> entry) {

		V value = knownIdentifierToValue.get(entry.getKey());
		var matches = entry.getValue();
		return new DomAutoCompleteMatch<>(value, matches);
	}

	/**
	 * HERE BE DRAGONS: Depending on the memory footprint of the value type,
	 * checking whether the returned {@link Set} contains a specific key may be
	 * significantly faster than checking the key set of the corresponding
	 * {@link Map}, e.g. by a factor (!) of ~600. This quickly adds up, so the
	 * returned {@link Set} shall be used for such operations, for the sake of
	 * performance.
	 */
	private Set<String> createKnownIdentifiersSet(Map<String, V> identifierToValueMap) {

		return new HashSet<>(identifierToValueMap.keySet());
	}
}

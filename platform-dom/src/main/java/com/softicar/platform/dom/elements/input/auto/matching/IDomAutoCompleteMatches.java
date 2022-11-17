package com.softicar.platform.dom.elements.input.auto.matching;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contains the results of a {@link DomAutoCompleteInput} search operation.
 *
 * @author Alexander Schmidt
 */
public interface IDomAutoCompleteMatches<V> {

	/**
	 * Returns all {@link DomAutoCompleteMatch} instances that resulted from the
	 * {@link DomAutoCompleteInput} search operation.
	 *
	 * @return all matches (never <i>null</i>; may be empty)
	 */
	List<DomAutoCompleteMatch<V>> getAll();

	/**
	 * Returns a {@link List} of all matched values.
	 *
	 * @return all matched values (never <i>null</i>)
	 */
	default List<V> getAllValues() {

		return getAllValuesAsStream().collect(Collectors.toList());
	}

	/**
	 * Returns a {@link Stream} of all matched values.
	 *
	 * @return a {@link Stream} of all matched values (never <i>null</i>)
	 */
	default Stream<V> getAllValuesAsStream() {

		return getAll().stream().map(DomAutoCompleteMatch::getValue);
	}

	/**
	 * Returns the perfectly-matching value of the {@link DomAutoCompleteInput}
	 * search operation, if any.
	 *
	 * @return the perfectly-matching value
	 */
	Optional<V> getPerfectMatchValue();

	/**
	 * Determines whether the {@link DomAutoCompleteInput} search operation
	 * returned an empty result.
	 *
	 * @return <i>true</i> if there are no search results; <i>false</i>
	 *         otherwise
	 */
	boolean isEmpty();

	/**
	 * Returns the number of results of the {@link DomAutoCompleteInput} search
	 * operation.
	 *
	 * @return the number of search results
	 */
	int size();

	/**
	 * Creates an {@link IDomAutoCompleteMatches} instance for a single matching
	 * entry.
	 *
	 * @param <T>
	 * @param pattern
	 *            the search pattern that produces the single match (never
	 *            <i>null</i>)
	 * @param identifier
	 *            the displayed identifier of the auto-complete entry (never
	 *            <i>null</i>)
	 * @param value
	 *            the value that is represented by the auto-complete entry (may
	 *            be <i>null</i>)
	 * @return the created {@link IDomAutoCompleteMatches} instance (never
	 *         <i>null</i>)
	 */
	static <T> IDomAutoCompleteMatches<T> createMatchesForSingleEntry(String pattern, String identifier, T value) {

		Objects.requireNonNull(pattern);
		Objects.requireNonNull(identifier);

		var matchRange = new DomAutoCompleteMatchRange(0, identifier.length());
		var wordMatches = new DomAutoCompleteWordMatches(pattern, List.of(pattern)).put(pattern, List.of(matchRange));
		var match = new DomAutoCompleteMatch<>(value, wordMatches);
		return new DomAutoCompleteMatches<T>().add(match);
	}
}

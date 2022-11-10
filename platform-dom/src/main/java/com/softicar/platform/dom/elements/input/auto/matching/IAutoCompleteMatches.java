package com.softicar.platform.dom.elements.input.auto.matching;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Contains the results of a {@link DomAutoCompleteInput} search operation.
 *
 * @author Alexander Schmidt
 */
public interface IAutoCompleteMatches<V> {

	/**
	 * Returns all {@link AutoCompleteMatch} instances that resulted from the
	 * {@link DomAutoCompleteInput} search operation.
	 *
	 * @return all matches (never <i>null</i>; may be empty)
	 */
	List<AutoCompleteMatch<V>> getAll();

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
	 * Creates an {@link IAutoCompleteMatches} instance for a single matching
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
	 * @return the created {@link IAutoCompleteMatches} instance (never
	 *         <i>null</i>)
	 */
	static <T> IAutoCompleteMatches<T> createForSingleMatch(String pattern, String identifier, T value) {

		Objects.requireNonNull(pattern);
		Objects.requireNonNull(identifier);

		var matchRange = new AutoCompleteMatchRange(0, identifier.length());
		var wordMatches = new AutoCompleteWordMatches(pattern, List.of(pattern)).put(pattern, List.of(matchRange));
		var match = new AutoCompleteMatch<>(value, wordMatches);
		return new AutoCompleteMatches<T>().add(match);
	}
}

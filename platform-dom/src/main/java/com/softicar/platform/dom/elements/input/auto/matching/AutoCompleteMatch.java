package com.softicar.platform.dom.elements.input.auto.matching;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import java.util.List;
import java.util.Objects;

/**
 * Represents a value that was matched in a {@link DomAutoCompleteInput} search
 * operation.
 *
 * @author Alexander Schmidt
 */
public class AutoCompleteMatch<V> {

	private final V value;
	private final AutoCompleteWordMatches wordMatches;

	AutoCompleteMatch(V value, AutoCompleteWordMatches wordMatches) {

		this.value = Objects.requireNonNull(value);
		this.wordMatches = Objects.requireNonNull(wordMatches);
	}

	/**
	 * Returns the matching value.
	 *
	 * @return the matching value (never <i>null</i>)
	 */
	public V getValue() {

		return value;
	}

	/**
	 * Returns all matched {@link AutoCompleteMatchRange} instances.
	 *
	 * @return all matched ranges (never <i>null</i>)
	 */
	public List<AutoCompleteMatchRange> getAllMatchRanges() {

		return wordMatches.getAllMatchRanges();
	}
}

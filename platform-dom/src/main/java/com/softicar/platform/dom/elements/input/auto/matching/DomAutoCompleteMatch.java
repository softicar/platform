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
public class DomAutoCompleteMatch<V> {

	private final V value;
	private final DomAutoCompleteWordMatches wordMatches;

	DomAutoCompleteMatch(V value, DomAutoCompleteWordMatches wordMatches) {

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
	 * Returns all matched {@link DomAutoCompleteMatchRange} instances.
	 *
	 * @return all matched ranges (never <i>null</i>)
	 */
	public List<DomAutoCompleteMatchRange> getAllMatchRanges() {

		return wordMatches.getAllMatchRanges();
	}
}

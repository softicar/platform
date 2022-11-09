package com.softicar.platform.dom.elements.input.auto.pattern;

import java.util.List;
import java.util.Objects;

/**
 * A single match from {@link MultiPatternMatcher}.
 *
 * @author Alexander Schmidt
 */
public class MultiPatternMatch<T> {

	private final String patterns;
	private final List<MultiPatternMatchRange> ranges;
	private final T value;
	private final String identifier;

	MultiPatternMatch(String patterns, List<MultiPatternMatchRange> ranges, T value, String identifier) {

		Objects.requireNonNull(patterns);
		Objects.requireNonNull(ranges);
		if (ranges.isEmpty()) {
			throw new IllegalArgumentException();
		}
		Objects.requireNonNull(identifier);

		this.patterns = patterns;
		this.ranges = ranges;
		this.value = value;
		this.identifier = identifier;
	}

	/**
	 * Returns all matching index ranges.
	 *
	 * @return the matching index ranges (never <i>null</i>; never empty)
	 */
	public List<MultiPatternMatchRange> getRanges() {

		return ranges;
	}

	/**
	 * Returns the number of matching index ranges.
	 *
	 * @return the number of ranges
	 */
	public int getRangeCount() {

		return ranges.size();
	}

	/**
	 * Returns the value of the matched identifier.
	 *
	 * @return the value of the matched identifier (may be <i>null</i>)
	 */
	public T getValue() {

		return value;
	}

	/**
	 * Returns the identifier of the value.
	 *
	 * @return the value identifier (never <i>null</i>)
	 */
	public String getIdentifier() {

		return identifier;
	}

	/**
	 * Determines whether this match is perfect.
	 *
	 * @return <i>true</i> if this match is perfect; <i>false</i> otherwise
	 */
	public boolean isPerfect() {

		return patterns.equalsIgnoreCase(identifier);
	}
}

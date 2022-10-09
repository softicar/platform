package com.softicar.platform.dom.elements.input.auto.pattern;

import java.util.List;
import java.util.Objects;

/**
 * A single match from {@link MultiPatternMatcher}.
 *
 * @author Alexander Schmidt
 */
public class MultiPatternMatch<T> {

	private final List<MultiPatternMatchRange> ranges;
	private final T value;

	MultiPatternMatch(List<MultiPatternMatchRange> ranges, T value) {

		Objects.requireNonNull(ranges);
		if (ranges.isEmpty()) {
			throw new IllegalArgumentException();
		}

		this.ranges = ranges;
		this.value = value;
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
	 * Returns the value of the matched identifier.
	 *
	 * @return the value of the matched identifier (may be <i>null</i>)
	 */
	public T getValue() {

		return value;
	}
}

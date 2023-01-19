package com.softicar.platform.dom.elements.input.auto.matching;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;

/**
 * Represents the index range of a match of a {@link DomAutoCompleteInput}
 * search operation.
 * <p>
 * The lower boundary is inclusive while the upper boundary is exclusive.
 *
 * @author Alexander Schmidt
 */
public class DomAutoCompleteMatchRange {

	private final int lowerIndex;
	private final int upperIndex;

	DomAutoCompleteMatchRange(int lowerIndex, int upperIndex) {

		if (lowerIndex < 0) {
			throw new IllegalArgumentException();
		}
		if (upperIndex < 0) {
			throw new IllegalArgumentException();
		}
		if (upperIndex < lowerIndex) {
			throw new IllegalArgumentException();
		}

		this.lowerIndex = lowerIndex;
		this.upperIndex = upperIndex;
	}

	/**
	 * Returns the lower index boundary.
	 *
	 * @return the lower index (inclusive)
	 */
	public int getLowerIndex() {

		return lowerIndex;
	}

	/**
	 * Returns the upper index boundary.
	 *
	 * @return the upper index (exclusive)
	 */
	public int getUpperIndex() {

		return upperIndex;
	}
}

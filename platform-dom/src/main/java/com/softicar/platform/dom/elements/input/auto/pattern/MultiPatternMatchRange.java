package com.softicar.platform.dom.elements.input.auto.pattern;

import java.util.Comparator;

public class MultiPatternMatchRange implements Comparable<MultiPatternMatchRange> {

	private final int fromIndex;
	private final int toIndex;

	MultiPatternMatchRange(int fromIndex, int toIndex) {

		if (fromIndex < 0) {
			throw new IllegalArgumentException();
		}
		if (toIndex < 0) {
			throw new IllegalArgumentException();
		}
		if (toIndex < fromIndex) {
			throw new IllegalArgumentException();
		}

		this.fromIndex = fromIndex;
		this.toIndex = toIndex;
	}

	@Override
	public int compareTo(MultiPatternMatchRange other) {

		return Comparator//
			.comparing(MultiPatternMatchRange::getFromIndex)
			.thenComparing(MultiPatternMatchRange::getToIndex)
			.compare(this, other);
	}

	public int getFromIndex() {

		return fromIndex;
	}

	public int getToIndex() {

		return toIndex;
	}
}

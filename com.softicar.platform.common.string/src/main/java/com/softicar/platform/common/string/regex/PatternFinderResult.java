package com.softicar.platform.common.string.regex;

public class PatternFinderResult {

	private final String matchingText;
	private final int offset;

	public PatternFinderResult() {

		this(null, -1);
	}

	public PatternFinderResult(String matchingText, Integer offset) {

		this.matchingText = matchingText;
		this.offset = offset;
	}

	public int getOffset() {

		return offset;
	}

	public String getMatchingText() {

		return matchingText;
	}
}

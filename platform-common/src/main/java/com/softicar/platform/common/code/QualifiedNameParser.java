package com.softicar.platform.common.code;

import java.util.ArrayList;
import java.util.List;

public class QualifiedNameParser {

	private final String delimiter;
	private List<String> segments;

	public QualifiedNameParser() {

		this(QualifiedName.DEFAULT_DELIMITER);
	}

	public QualifiedNameParser(String delimiter) {

		this.delimiter = delimiter;

		if (delimiter.isEmpty()) {
			throw new IllegalArgumentException("The delimiter may not be empty.");
		}
	}

	public QualifiedName parse(String canonicalName) {

		return new QualifiedName(parseSegments(canonicalName));
	}

	public List<String> parseSegments(String canonicalName) {

		this.segments = new ArrayList<>();

		int startIndex = 0;
		while (startIndex <= canonicalName.length()) {
			int delimiterIndex = canonicalName.indexOf(delimiter, startIndex);
			if (delimiterIndex < 0) {
				delimiterIndex = canonicalName.length();
			}

			addSegment(canonicalName.substring(startIndex, delimiterIndex));
			startIndex = delimiterIndex + delimiter.length();
		}

		return segments;
	}

	private void addSegment(String segment) {

		segments.add(segment);
	}
}

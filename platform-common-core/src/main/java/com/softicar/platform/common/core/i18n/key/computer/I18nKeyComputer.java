package com.softicar.platform.common.core.i18n.key.computer;

import java.util.Optional;

public class I18nKeyComputer {

	private static final String ESCAPED_PERCENT_REPLACEMENT = " PERCENT ";
	private static final String QUESTION_MARK_REPLACEMENT = " QUESTION ";

	private final String originalIdentifierString;

	public I18nKeyComputer(String originalIdentifierString) {

		this.originalIdentifierString = originalIdentifierString;
	}

	public String compute() {

		return Optional//
			.of(originalIdentifierString)
			.map(this::replaceEscapedPercents)
			.map(this::replaceQuestionMarks)
			.map(this::computeArguments)
			.map(this::replaceInvalidCharacters)
			.map(this::implodeAndTrimWhitespaces)
			.map(this::convertToIdentifier)
			.orElse(null);
	}

	public int computeArgumentCount() {

		return Optional//
			.of(originalIdentifierString)
			.map(this::replaceEscapedPercents)
			.map(this::replaceQuestionMarks)
			.map(this::computeArgumentCount)
			.orElse(0);
	}

	public String getOriginalIdentifierString() {

		return originalIdentifierString;
	}

	private String replaceEscapedPercents(String string) {

		return I18nFormatStringNormalizer.replaceEscapedPercents(string, ESCAPED_PERCENT_REPLACEMENT);
	}

	private String replaceQuestionMarks(String string) {

		return string//
			.replaceAll("\\?+", "?")
			.replace("?", QUESTION_MARK_REPLACEMENT);
	}

	private String replaceInvalidCharacters(String string) {

		return string.replaceAll("[^A-Za-z0-9%]", " ");
	}

	private String computeArguments(String string) {

		StringBuilder output = new StringBuilder();
		IndexRangeList wildcardRanges = extractWildcardRanges(string);
		int argumentNumber = 1;
		int lastEnd = 0;
		for (IndexRange range: wildcardRanges) {
			Integer start = range.getStart();
			Integer end = range.getEnd();

			output.append(string.substring(lastEnd, start));
			output.append("ARG");
			output.append(argumentNumber++);

			lastEnd = end;
		}

		output.append(string.substring(lastEnd));

		return output.toString();
	}

	private int computeArgumentCount(String string) {

		return extractWildcardRanges(string).size();
	}

	private IndexRangeList extractWildcardRanges(String string) {

		return new I18nFormatSpecifierExtractor().extract(string).getLegalWildcardRanges();
	}

	private String implodeAndTrimWhitespaces(String string) {

		return string.replaceAll("\\s+", " ").trim();
	}

	private String convertToIdentifier(String string) {

		string = string.replaceAll(" ", "_");
		if (!string.isEmpty() && Character.isDigit(string.charAt(0))) {
			string = "_" + string;
		}
		return string.toUpperCase();
	}

	public static void main(String[] args) {

		String s = "In the next step you can control the replacement of the variables by the entered variable values.";
		System.out.println(new I18nKeyComputer(s).compute());

	}
}

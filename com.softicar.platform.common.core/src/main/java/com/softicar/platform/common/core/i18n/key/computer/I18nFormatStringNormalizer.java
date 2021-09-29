package com.softicar.platform.common.core.i18n.key.computer;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class I18nFormatStringNormalizer {

	private static final Pattern ESCAPED_PERCENTS_PATTERN = Pattern.compile("(%%)+");

	public static String replaceEscapedPercents(String formatString, String replacement) {

		return replaceEscapedPercents(formatString, match -> replacement);
	}

	public static String replaceEscapedPercentsWithSpaces(String formatString) {

		return replaceEscapedPercents(formatString, match -> generate(' ', match.length()));
	}

	private static String replaceEscapedPercents(String formatString, Function<String, String> replacementFactory) {

		int offset = 0;
		for (IndexRange range: getEscapedPercentRanges(formatString)) {
			IndexRange shiftedRange = range.getShifted(offset);
			String match = formatString.substring(shiftedRange.getStart(), shiftedRange.getEnd());
			String replacement = replacementFactory.apply(match);
			formatString = replaceRange(formatString, shiftedRange, replacement);
			offset += replacement.length() - shiftedRange.getLength();
		}
		return formatString;
	}

	private static IndexRangeList getEscapedPercentRanges(String formatString) {

		IndexRangeList ranges = new IndexRangeList();
		Matcher matcher = ESCAPED_PERCENTS_PATTERN.matcher(formatString);
		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			ranges.add(start, end);
		}
		return ranges;
	}

	private static String replaceRange(String input, IndexRange range, String replacement) {

		String prefix = input.substring(0, range.getStart());
		String suffix = input.substring(range.getEnd());
		return prefix + replacement + suffix;
	}

	private static String generate(char character, int length) {

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append(character);
		}
		return builder.toString();
	}
}

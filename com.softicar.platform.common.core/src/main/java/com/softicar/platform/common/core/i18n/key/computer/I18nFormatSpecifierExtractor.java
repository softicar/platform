package com.softicar.platform.common.core.i18n.key.computer;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extracts and counts format-string wild-cards.
 * <p>
 * Valid wild-card formats are:
 *
 * <pre>
 * %s
 * %f
 * %2f
 * %.2f
 * %3.2f
 * </pre>
 *
 * @author Alexander Schmidt
 */
public class I18nFormatSpecifierExtractor {

	private static final String WILDCARD_REGEX = "(%s)|(%((([1-9][0-9]*)?\\.)?[1-9][0-9]*)?f)";
	private static final Pattern WILDCARD_PATTERN = Pattern.compile(WILDCARD_REGEX);
	private static final Pattern LEADING_WILDCARD_PATTERN = Pattern.compile("(" + WILDCARD_REGEX + ").*", Pattern.DOTALL);
	private int legalWildcardCount;
	private int illegalWildcardCount;
	private final IndexRangeList legalWildcardRanges;

	public I18nFormatSpecifierExtractor() {

		this.legalWildcardCount = 0;
		this.illegalWildcardCount = 0;
		this.legalWildcardRanges = new IndexRangeList();
	}

	public I18nFormatSpecifierExtractor extract(String formatString) {

		String normalizedFormatString = I18nFormatStringNormalizer.replaceEscapedPercentsWithSpaces(formatString);
		this.legalWildcardCount = determineLegalWildcardCount(normalizedFormatString);
		this.illegalWildcardCount = determineIllegalWildcardCount(normalizedFormatString);

		return this;
	}

	public int getLegalWildcardCount() {

		return legalWildcardCount;
	}

	public IndexRangeList getLegalWildcardRanges() {

		return legalWildcardRanges;
	}

	public int getIllegalWildcardCount() {

		return illegalWildcardCount;
	}

	public boolean hasIllegalWildcards() {

		return illegalWildcardCount > 0;
	}

	private int determineLegalWildcardCount(String formatString) {

		int count = 0;
		Matcher matcher = WILDCARD_PATTERN.matcher(formatString);
		while (matcher.find()) {
			legalWildcardRanges.add(matcher.start(), matcher.end());
			count++;
		}
		return count;
	}

	private int determineIllegalWildcardCount(String formatString) {

		int count = 0;
		for (String token: extractTokensWithLeadingPercentSigns(formatString)) {
			if (token.startsWith("%")) {
				if (!LEADING_WILDCARD_PATTERN.matcher(token).find()) {
					count++;
				}
			}
		}
		return count;
	}

	private List<String> extractTokensWithLeadingPercentSigns(String formatString) {

		return Arrays.asList(formatString.split("(?=%)"));
	}
}

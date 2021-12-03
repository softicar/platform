package com.softicar.platform.common.string.csv;

import com.softicar.platform.common.string.CommonStringI18n;
import com.softicar.platform.common.string.regex.PatternFinder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Processes a CSV-formatted {@link String}, in order to identify the positions
 * of non-escaped delimiters (i.e. commas and newlines).
 *
 * @author Alexander Schmidt
 */
class CsvDelimiterFinder {

	private static final Pattern CONTEXT_DELIMITERS = Pattern.compile("[,\n\r\"]");
	private static final Pattern ODD_LENGTH_QUOTE_BLOCK = Pattern.compile("(?<!\")\"(\"\")*(?!\")");

	private boolean quoteContext;
	private boolean afterQuoteContext;

	/**
	 * Finds the positions and types of value delimiters (i.e. non-escaped
	 * commas and non-escaped newlines).
	 *
	 * @param csv
	 *            the CSV-formatted {@link String} to process (never
	 *            <i>null</i>)
	 * @return a {@link List} of value {@link CsvDelimiter} instances (never
	 *         <i>null</i>)
	 */
	public List<CsvDelimiter> findDelimiters(String csv) {

		Objects.requireNonNull(csv);

		this.quoteContext = false;
		this.afterQuoteContext = false;

		var delimiters = new ArrayList<CsvDelimiter>();
		int offset = 0;

		while (true) {
			CsvDelimiter delimiter;

			if (!afterQuoteContext) {
				if (!quoteContext) {
					delimiter = findDelimiter(csv, offset);
					if (delimiter.isQuote()) {
						enterQuoteContext();
					} else if (delimiter.isComma() || delimiter.isNewline()) {
						delimiters.add(delimiter);
					}
				} else {
					delimiter = findQuoteBlockWithOddLength(csv, offset);
					if (delimiter.isQuote()) {
						leaveQuoteContext();
						setAfterQuoteContext();
					}
				}
			} else {
				delimiter = findDelimiter(csv, offset);
				if (delimiter.isComma() || delimiter.isNewline()) {
					delimiters.add(delimiter);
					unsetAfterQuoteContext();
				} else if (offset >= csv.length()) {
					// reached end-of-file
				} else {
					throw new CsvFormatException(CommonStringI18n.AFTER_A_DOUBLE_QUOTED_VALUE_A_COMMA_A_NEWLINE_OR_END_OF_FILE_ARE_EXPECTED, offset);
				}
			}

			if (!delimiter.isNone()) {
				offset = delimiter.getIndex() + delimiter.getLength();
			} else {
				// no further delimiters found - we're done here
				break;
			}
		}

		// add artificial lower and upper delimiters
		delimiters.add(0, CsvDelimiter.newline(-1));
		delimiters.add(CsvDelimiter.newline(csv.length()));
		return delimiters;
	}

	private void enterQuoteContext() {

		this.quoteContext = true;
	}

	private void leaveQuoteContext() {

		this.quoteContext = false;
	}

	private void setAfterQuoteContext() {

		this.afterQuoteContext = true;
	}

	private void unsetAfterQuoteContext() {

		this.afterQuoteContext = false;
	}

	private CsvDelimiter findDelimiter(String text, int offset) {

		if (quoteContext) {
			throw new CsvProcessorIllegalStateException(
				"Tried to find a non-escaped delimiter while in a double quote context, after index %s.".formatted(offset));
		}

		var result = PatternFinder.indexOfPattern(CONTEXT_DELIMITERS, text, offset);
		int index = result.getOffset();
		if (index >= 0) {
			String match = result.getMatchingText();
			switch (match) {
			case ",":
				return CsvDelimiter.comma(index);
			case "\n":
				return CsvDelimiter.newline(index);
			case "\r":
				return CsvDelimiter.newline(index);
			case "\"":
				return CsvDelimiter.quote(index);
			default:
				throw new CsvProcessorIllegalStateException("Unknown delimiter: [%s]".formatted(match));
			}
		} else {
			return CsvDelimiter.none();
		}
	}

	private CsvDelimiter findQuoteBlockWithOddLength(String text, int offset) {

		if (!quoteContext) {
			throw new CsvProcessorIllegalStateException(
				"Tried to find a terminating double quote while not in a double quote context, after index %s.".formatted(offset));
		}

		var result = PatternFinder.indexOfPattern(ODD_LENGTH_QUOTE_BLOCK, text, offset);
		if (result.getOffset() >= 0) {
			return CsvDelimiter
				.quote(//
					result.getOffset(),
					Optional.ofNullable(result.getMatchingText()).orElse("").length());
		} else {
			return CsvDelimiter.none();
		}
	}
}

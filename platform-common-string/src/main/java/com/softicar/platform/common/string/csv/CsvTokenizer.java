package com.softicar.platform.common.string.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Extracts value tokens from a CSV-formatted {@link String}.
 * <p>
 * Assumes a comma ({@code ,}) as value separator.
 * <p>
 * Assumes that escaping is done via:
 * <ol>
 * <li>Enclosing <i>escape-worthy</i> values in quotes ({@code "}), and</li>
 * <li>Duplication of any quote that is contained in a value
 * ({@code " -> ""})</li>
 * </ol>
 * A value is <i>escape-worthy</i> if it contains at least one of the following
 * characters: {@code [,][\r][\n]["]}
 * <p>
 * Tolerates superfluous quotes around values which are not
 * <i>escape-worthy</i>.
 * <p>
 * Retains line breaks in values, as long as the values are quoted. This way, an
 * extracted <i>logical row</i> can emerge from several <i>physical rows</i> in
 * the original CSV {@link String}.
 *
 * @author Alexander Schmidt
 */
public class CsvTokenizer {

	/**
	 * Extracts values from the given CSV-formatted {@link String}.
	 * <p>
	 * In the returned result, each item in the outer {@link List} corresponds
	 * to a logical row, as extracted from one or several physical rows in the
	 * given CSV {@link String}. In the inner {@link List}, each item
	 * corresponds to a value in the respective row.
	 * <p>
	 * Empty physical rows in the given CSV {@link String} are ignored.
	 * <p>
	 * If the given CSV {@link String} is empty, an empty {@link List} is
	 * returned.
	 *
	 * @param csv
	 *            the CSV-formatted {@link String} to process (never
	 *            <i>null</i>)
	 * @return a {@link List} of logical rows of extracted tokens (never
	 *         <i>null</i>)
	 */
	public List<List<String>> tokenize(String csv) {

		Objects.requireNonNull(csv);

		var delimiters = new CsvDelimiterFinder().findDelimiters(csv);
		var rows = new ArrayList<List<String>>();

		List<String> row = new ArrayList<>();
		for (int i = 0; i < delimiters.size() - 1; i++) {
			CsvDelimiter lower = delimiters.get(i);
			CsvDelimiter upper = delimiters.get(i + 1);

			String value = csv.substring(lower.getIndex() + 1, upper.getIndex());
			if (value.startsWith("\"")) {
				if (value.endsWith("\"")) {
					value = value.substring(1, value.length() - 1);
				} else {
					throw new CsvProcessorIllegalStateException("Value '%s' lacks a closing quote.".formatted(value));
				}
			}
			row.add(value.replaceAll("\"\"", "\""));

			if (upper.isNewline()) {
				if (hasContent(row)) {
					rows.add(row);
				}
				row = new ArrayList<>();
			}
		}

		return rows;
	}

	private boolean hasContent(List<String> row) {

		return !row.isEmpty() && !hasSingleEmptyValue(row);
	}

	private boolean hasSingleEmptyValue(List<String> row) {

		return row.size() == 1 && row.get(0).equals("");
	}
}

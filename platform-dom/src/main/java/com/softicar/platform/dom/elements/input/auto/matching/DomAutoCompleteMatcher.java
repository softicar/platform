package com.softicar.platform.dom.elements.input.auto.matching;

import com.softicar.platform.common.string.normalizer.CurrentDiacriticNormalizationCache;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Matches a {@link DomAutoCompleteInput} search pattern against entries, each
 * of which is represented by an identifier, and each of which represents a
 * value.
 *
 * @author Alexander Schmidt
 */
public class DomAutoCompleteMatcher<V> {

	private static final Pattern WHITESPACES = Pattern.compile("\\s+");
	private final Map<String, V> identifierToValueMap;
	private boolean ignoreDiacritics;

	/**
	 * Constructs a new {@link DomAutoCompleteMatcher}.
	 *
	 * @param identifierToValueMap
	 *            a mapping between identifier and value, for all entries (never
	 *            <i>null</i>)
	 */
	public DomAutoCompleteMatcher(Map<String, V> identifierToValueMap) {

		this.identifierToValueMap = Objects.requireNonNull(identifierToValueMap);
		this.ignoreDiacritics = false;
	}

	/**
	 * Specifies whether diacritics (aka. diacritical marks) shall be ignored
	 * when patterns are matched against identifiers.
	 * <p>
	 * Example: If diacritics are ignored, pattern {@code "foo"} will match
	 * identifier {@code "fóô"}. Otherwise, this pattern will not match that
	 * identifier.
	 * <p>
	 * By default, diacritics are not ignored.
	 *
	 * @param ignoreDiacritics
	 *            <i>true</i> if diacritics shall be ignored; <i>false</i>
	 *            otherwise
	 * @return this
	 */
	public DomAutoCompleteMatcher<V> setIgnoreDiacritics(boolean ignoreDiacritics) {

		this.ignoreDiacritics = ignoreDiacritics;
		return this;
	}

	/**
	 * Same as {@link #findMatches(String, int)} but without limit.
	 */
	public DomAutoCompleteMatches<V> findMatches(String pattern) {

		return findMatches(pattern, Integer.MAX_VALUE);
	}

	/**
	 * Finds a limited number of matches for the given search pattern.
	 * <p>
	 * The given search pattern always gets trimmed, and converted to
	 * lower-case.
	 * <p>
	 * If the given search pattern is blank, all identifiers (and hence all
	 * values) will match.
	 *
	 * @param pattern
	 *            a search pattern that consists of one or several words (never
	 *            <i>null</i>; may be blank)
	 * @param limit
	 *            the maximum number of matches to find (must be positive)
	 * @return the matches for the given search pattern (never <i>null</i>)
	 */
	public DomAutoCompleteMatches<V> findMatches(String pattern, int limit) {

		if (limit <= 0) {
			throw new IllegalArgumentException();
		}

		pattern = pattern.trim().toLowerCase();

		var words = splitToWords(pattern);
		var builder = new DomAutoCompleteMatchesBuilder<>(identifierToValueMap, ignoreDiacritics);

		for (var entry: identifierToValueMap.entrySet()) {
			String identifier = entry.getKey();
			var wordMatches = findWordMatches(pattern, words, identifier.toLowerCase());
			if (!wordMatches.isEmpty()) {
				builder.addMatches(identifier, wordMatches);
				if (isPerfectMatch(identifier, pattern)) {
					builder.setPerfectMatch(identifier);
				}
			}
		}

		return builder.build(limit);
	}

	private boolean isPerfectMatch(String identifier, String pattern) {

		return normalize(pattern).equalsIgnoreCase(normalize(identifier));
	}

	private List<String> splitToWords(String pattern) {

		if (pattern.isBlank()) {
			return List.of("");
		} else {
			return List//
				.of(WHITESPACES.split(pattern))
				.stream()
				.filter(it -> !it.isBlank())
				.collect(Collectors.toList());
		}
	}

	private DomAutoCompleteWordMatches findWordMatches(String pattern, List<String> words, String identifier) {

		var wordMatches = new DomAutoCompleteWordMatches(pattern, words);
		for (var word: words) {
			var ranges = findRanges(identifier, word);
			if (!ranges.isEmpty()) {
				wordMatches.put(word, ranges);
			} else {
				// return an empty result if no ranges were found for at least one of the words
				return new DomAutoCompleteWordMatches(pattern, words);
			}
		}
		return wordMatches;
	}

	private ArrayList<DomAutoCompleteMatchRange> findRanges(String identifier, String word) {

		var ranges = new ArrayList<DomAutoCompleteMatchRange>();
		for (var index: getIndexesOf(identifier, word)) {
			ranges.add(new DomAutoCompleteMatchRange(index, index + word.length()));
		}
		return ranges;
	}

	private Set<Integer> getIndexesOf(String haystack, String needle) {

		if (needle.isEmpty()) {
			return Set.of(0);
		}

		haystack = normalize(haystack);
		var indexes = new TreeSet<Integer>();
		int index = 0;
		do {
			index = haystack.indexOf(normalize(needle), index);
			if (index > -1) {
				indexes.add(index);
				index++;
			}
		} while (index > -1 && index < haystack.length());
		return indexes;
	}

	private String normalize(String text) {

		return ignoreDiacritics? CurrentDiacriticNormalizationCache.get().normalize(text) : text;
	}
}

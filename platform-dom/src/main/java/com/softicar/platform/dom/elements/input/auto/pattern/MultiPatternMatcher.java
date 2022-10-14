package com.softicar.platform.dom.elements.input.auto.pattern;

import com.softicar.platform.common.string.normalizer.DiacriticNormalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Matches whitespace-separated patterns against identifiers, each of which maps
 * to a value.
 *
 * @author Alexander Schmidt
 */
public class MultiPatternMatcher<V> {

	private static final Pattern WHITESPACES = Pattern.compile("\\s+");
	private final Map<String, V> identifierToValueMap;
	private final DiacriticNormalizer normalizer;
	private boolean ignoreDiacritics;

	/**
	 * Constructs a new {@link MultiPatternMatcher}.
	 *
	 * @param identifierToValueMap
	 *            a mapping between identifier and value (never <i>null</i>)
	 */
	public MultiPatternMatcher(Map<String, V> identifierToValueMap) {

		this.identifierToValueMap = Objects.requireNonNull(identifierToValueMap);
		this.normalizer = new DiacriticNormalizer();
		this.ignoreDiacritics = false;
	}

	/**
	 * Determines whether diacritics shall be ignored when matching patterns
	 * against identifiers.
	 * <p>
	 * Example: If diacritics are ignored, pattern {@code "foo"} will match
	 * identifier {@code "fóô"}.
	 * <p>
	 * By default, diacritics are not ignored.
	 *
	 * @param ignoreDiacritics
	 *            <i>true</i> if diacritics shall be ignored; <i>false</i>
	 *            otherwise
	 * @return this
	 */
	public MultiPatternMatcher<V> setIgnoreDiacritics(boolean ignoreDiacritics) {

		this.ignoreDiacritics = ignoreDiacritics;
		return this;
	}

	/**
	 * Same as {@link #findMatches(String, int)} but without limit.
	 */
	public List<MultiPatternMatch<V>> findMatches(String patterns) {

		return findMatches(patterns, Integer.MAX_VALUE);
	}

	/**
	 * Finds a limited number of matches (as instances of
	 * {@link MultiPatternMatch}) for the given {@link String} of
	 * whitespace-separated patterns.
	 * <p>
	 * If the given {@link String} is blank, all identifiers (and hence all
	 * values) will match.
	 * <p>
	 * The returned matches are sorted by their respective number of matching
	 * ranges, in descending order.
	 *
	 * @param patterns
	 *            the whitespace-separated patterns (never <i>null</i>; may be
	 *            blank)
	 * @param limit
	 *            the maximum number of matches to return (must be >= 0)
	 * @return all matches for the given patterns (never <i>null</i>)
	 */
	public List<MultiPatternMatch<V>> findMatches(String patterns, int limit) {

		Objects.requireNonNull(patterns);

		var tokens = splitToTokens(patterns);
		var matches = new ArrayList<MultiPatternMatch<V>>();

		for (var entry: identifierToValueMap.entrySet()) {
			String key = entry.getKey().toLowerCase();
			var matchRanges = getMatchRanges(key, tokens);
			if (!matchRanges.isEmpty()) {
				matches.add(new MultiPatternMatch<>(matchRanges, entry.getValue()));
			}
		}

		return matches//
			.stream()
			.sorted(Comparator.comparing(MultiPatternMatch<V>::getRangeCount).reversed())
			.limit(limit)
			.collect(Collectors.toList());
	}

	private List<String> splitToTokens(String patterns) {

		if (patterns.isBlank()) {
			return List.of("");
		} else {
			return List//
				.of(WHITESPACES.split(patterns))
				.stream()
				.filter(it -> !it.isBlank())
				.collect(Collectors.toList());
		}
	}

	private List<MultiPatternMatchRange> getMatchRanges(String haystack, Collection<String> needles) {

		var ranges = new TreeSet<MultiPatternMatchRange>();
		for (var needle: needles) {
			for (var index: getIndexesOf(haystack, needle)) {
				ranges.add(new MultiPatternMatchRange(index, index + needle.length()));
			}
		}
		return new ArrayList<>(ranges);
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

		return ignoreDiacritics? normalizer.normalize(text) : text;
	}
}

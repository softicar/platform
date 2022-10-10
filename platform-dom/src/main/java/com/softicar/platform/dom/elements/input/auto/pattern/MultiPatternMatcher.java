package com.softicar.platform.dom.elements.input.auto.pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
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

	/**
	 * Constructs a new {@link MultiPatternMatcher}.
	 *
	 * @param identifierToValueMap
	 *            a mapping between identifier and value (never <i>null</i>)
	 */
	public MultiPatternMatcher(Map<String, V> identifierToValueMap) {

		this.identifierToValueMap = Objects.requireNonNull(identifierToValueMap);
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
		var matchesByRangeCount = new TreeMap<Integer, List<MultiPatternMatch<V>>>();

		for (var entry: identifierToValueMap.entrySet()) {
			String key = entry.getKey().toLowerCase();
			var matchRanges = getMatchRanges(key, tokens);
			if (!matchRanges.isEmpty()) {
				matchesByRangeCount//
					.computeIfAbsent(matchRanges.size(), dummy -> new ArrayList<>())
					.add(new MultiPatternMatch<>(matchRanges, entry.getValue()));
			}
		}

		return matchesByRangeCount//
			.descendingMap()
			.entrySet()
			.stream()
			.map(Entry::getValue)
			.flatMap(Collection::stream)
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

		var indexes = new TreeSet<Integer>();
		int offset = 0;
		int index;
		do {
			index = haystack.indexOf(needle, offset);
			offset += needle.length();
			if (index > -1) {
				indexes.add(index);
			}
		} while (index > -1);
		return indexes;
	}
}

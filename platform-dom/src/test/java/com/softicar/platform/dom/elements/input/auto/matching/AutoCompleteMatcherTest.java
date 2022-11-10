package com.softicar.platform.dom.elements.input.auto.matching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.softicar.platform.common.container.pair.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;

public class AutoCompleteMatcherTest {

	private static final Pair<Integer, String> FOO_FOO_FOO_1 = new Pair<>(1, "foo foo foo");
	private static final Pair<Integer, String> AAA_FOO_BAR_2 = new Pair<>(2, "aaa foo bar");
	private static final Pair<Integer, String> FOO_BAR_3 = new Pair<>(3, "foo bar");
	private static final Pair<Integer, String> FOO_BAR_AAA_4 = new Pair<>(4, "foo bar aaa");
	private static final Pair<Integer, String> BAR_FOO_5 = new Pair<>(5, "bar foo");
	private static final Pair<Integer, String> BAR_BAR_BAR_6 = new Pair<>(6, "bar bar bar");
	private static final Pair<Integer, String> ZZZ_BAR_FOO_7 = new Pair<>(7, "zzz bar foo");
	private static final Pair<Integer, String> BAR_FOO_ZZZ_8 = new Pair<>(8, "bar foo zzz");

	private final Setup setup;

	public AutoCompleteMatcherTest() {

		setup = new Setup();
		setup.addEntry(FOO_FOO_FOO_1);
		setup.addEntry(AAA_FOO_BAR_2);
		setup.addEntry(FOO_BAR_3);
		setup.addEntry(FOO_BAR_AAA_4);
		setup.addEntry(BAR_FOO_5);
		setup.addEntry(BAR_BAR_BAR_6);
		setup.addEntry(ZZZ_BAR_FOO_7);
		setup.addEntry(BAR_FOO_ZZZ_8);
	}

	@Test
	public void testFindMatchesWithEmptyPattern() {

		var matches = createMatcher().findMatches("");
		new Asserter(matches)//
			.assertMatch(AAA_FOO_BAR_2, "0..0")
			.assertMatch(BAR_BAR_BAR_6, "0..0")
			.assertMatch(BAR_FOO_5, "0..0")
			.assertMatch(BAR_FOO_ZZZ_8, "0..0")
			.assertMatch(FOO_BAR_3, "0..0")
			.assertMatch(FOO_BAR_AAA_4, "0..0")
			.assertMatch(FOO_FOO_FOO_1, "0..0")
			.assertMatch(ZZZ_BAR_FOO_7, "0..0")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithSingleWord() {

		var matches = createMatcher().findMatches("foo");
		new Asserter(matches)//
			.assertMatch(FOO_BAR_3, "0..3")
			.assertMatch(FOO_BAR_AAA_4, "0..3")
			.assertMatch(FOO_FOO_FOO_1, "0..3, 4..7, 8..11")
			.assertMatch(AAA_FOO_BAR_2, "4..7")
			.assertMatch(BAR_FOO_5, "4..7")
			.assertMatch(BAR_FOO_ZZZ_8, "4..7")
			.assertMatch(ZZZ_BAR_FOO_7, "8..11")
			.assertNoMoreMatches();

		matches = createMatcher().findMatches("bar");
		new Asserter(matches)//
			.assertMatch(BAR_BAR_BAR_6, "0..3, 4..7, 8..11")
			.assertMatch(BAR_FOO_5, "0..3")
			.assertMatch(BAR_FOO_ZZZ_8, "0..3")
			.assertMatch(AAA_FOO_BAR_2, "8..11")
			.assertMatch(FOO_BAR_3, "4..7")
			.assertMatch(FOO_BAR_AAA_4, "4..7")
			.assertMatch(ZZZ_BAR_FOO_7, "4..7")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithSingleWordAndLimit() {

		var matches = createMatcher().findMatches("foo", 4);
		new Asserter(matches)//
			.assertMatch(FOO_BAR_3, "0..3")
			.assertMatch(FOO_BAR_AAA_4, "0..3")
			.assertMatch(FOO_FOO_FOO_1, "0..3, 4..7, 8..11")
			.assertMatch(AAA_FOO_BAR_2, "4..7")
			.assertNoMoreMatches();

		matches = createMatcher().findMatches("bar", 4);
		new Asserter(matches)//
			.assertMatch(BAR_BAR_BAR_6, "0..3, 4..7, 8..11")
			.assertMatch(BAR_FOO_5, "0..3")
			.assertMatch(BAR_FOO_ZZZ_8, "0..3")
			.assertMatch(AAA_FOO_BAR_2, "8..11")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithSingleWordAndLeadingWhitespace() {

		var matches = createMatcher().findMatches(" bar");
		new Asserter(matches)//
			.assertMatch(BAR_BAR_BAR_6, "0..3, 4..7, 8..11")
			.assertMatch(BAR_FOO_5, "0..3")
			.assertMatch(BAR_FOO_ZZZ_8, "0..3")
			.assertMatch(AAA_FOO_BAR_2, "8..11")
			.assertMatch(FOO_BAR_3, "4..7")
			.assertMatch(FOO_BAR_AAA_4, "4..7")
			.assertMatch(ZZZ_BAR_FOO_7, "4..7")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithSingleWordAndTailingWhitespace() {

		var matches = createMatcher().findMatches("bar ");
		new Asserter(matches)//
			.assertMatch(BAR_BAR_BAR_6, "0..3, 4..7, 8..11")
			.assertMatch(BAR_FOO_5, "0..3")
			.assertMatch(BAR_FOO_ZZZ_8, "0..3")
			.assertMatch(AAA_FOO_BAR_2, "8..11")
			.assertMatch(FOO_BAR_3, "4..7")
			.assertMatch(FOO_BAR_AAA_4, "4..7")
			.assertMatch(ZZZ_BAR_FOO_7, "4..7")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithSingleWordPrefix() {

		var matches = createMatcher().findMatches("ba");
		new Asserter(matches)//
			.assertMatch(BAR_BAR_BAR_6, "0..2, 4..6, 8..10")
			.assertMatch(BAR_FOO_5, "0..2")
			.assertMatch(BAR_FOO_ZZZ_8, "0..2")
			.assertMatch(AAA_FOO_BAR_2, "8..10")
			.assertMatch(FOO_BAR_3, "4..6")
			.assertMatch(FOO_BAR_AAA_4, "4..6")
			.assertMatch(ZZZ_BAR_FOO_7, "4..6")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithSingleWordSuffix() {

		var matches = createMatcher().findMatches("ar");
		new Asserter(matches)//
			.assertMatch(AAA_FOO_BAR_2, "9..11")
			.assertMatch(BAR_BAR_BAR_6, "1..3, 5..7, 9..11")
			.assertMatch(BAR_FOO_5, "1..3")
			.assertMatch(BAR_FOO_ZZZ_8, "1..3")
			.assertMatch(FOO_BAR_3, "5..7")
			.assertMatch(FOO_BAR_AAA_4, "5..7")
			.assertMatch(ZZZ_BAR_FOO_7, "5..7")
			.assertNoMoreMatches();

		matches = createMatcher().findMatches("oo");
		new Asserter(matches)//
			.assertMatch(AAA_FOO_BAR_2, "5..7")
			.assertMatch(BAR_FOO_5, "5..7")
			.assertMatch(BAR_FOO_ZZZ_8, "5..7")
			.assertMatch(FOO_BAR_3, "1..3")
			.assertMatch(FOO_BAR_AAA_4, "1..3")
			.assertMatch(FOO_FOO_FOO_1, "1..3, 5..7, 9..11")
			.assertMatch(ZZZ_BAR_FOO_7, "9..11")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithSingleCharacterWord() {

		var matches = createMatcher().findMatches("b");
		new Asserter(matches)//
			.assertMatch(BAR_BAR_BAR_6, "0..1, 4..5, 8..9")
			.assertMatch(BAR_FOO_5, "0..1")
			.assertMatch(BAR_FOO_ZZZ_8, "0..1")
			.assertMatch(AAA_FOO_BAR_2, "8..9")
			.assertMatch(FOO_BAR_3, "4..5")
			.assertMatch(FOO_BAR_AAA_4, "4..5")
			.assertMatch(ZZZ_BAR_FOO_7, "4..5")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithMultipleWords() {

		var matches = createMatcher().findMatches("foo bar");
		new Asserter(matches)//
			.assertMatch(FOO_BAR_3, "0..3, 4..7")
			.assertMatch(FOO_BAR_AAA_4, "0..3, 4..7")
			.assertMatch(AAA_FOO_BAR_2, "4..7, 8..11")
			.assertMatch(BAR_FOO_5, "0..3, 4..7")
			.assertMatch(BAR_FOO_ZZZ_8, "0..3, 4..7")
			.assertMatch(ZZZ_BAR_FOO_7, "4..7, 8..11")
			.assertNoMoreMatches();

		matches = createMatcher().findMatches("bar foo");
		new Asserter(matches)//
			.assertMatch(BAR_FOO_5, "0..3, 4..7")
			.assertMatch(BAR_FOO_ZZZ_8, "0..3, 4..7")
			.assertMatch(ZZZ_BAR_FOO_7, "4..7, 8..11")
			.assertMatch(AAA_FOO_BAR_2, "4..7, 8..11")
			.assertMatch(FOO_BAR_3, "0..3, 4..7")
			.assertMatch(FOO_BAR_AAA_4, "0..3, 4..7")
			.assertNoMoreMatches();

		matches = createMatcher().findMatches("zzz foo");
		new Asserter(matches)//
			.assertMatch(ZZZ_BAR_FOO_7, "0..3, 8..11")
			.assertMatch(BAR_FOO_ZZZ_8, "4..7, 8..11")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithMultipleWordsAndFirstWordAsSuffix() {

		var matches = createMatcher().findMatches("oo bar");
		new Asserter(matches)//
			.assertMatch(AAA_FOO_BAR_2, "5..7, 8..11")
			.assertMatch(FOO_BAR_3, "1..3, 4..7")
			.assertMatch(FOO_BAR_AAA_4, "1..3, 4..7")
			.assertMatch(BAR_FOO_5, "0..3, 5..7")
			.assertMatch(BAR_FOO_ZZZ_8, "0..3, 5..7")
			.assertMatch(ZZZ_BAR_FOO_7, "4..7, 9..11")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithMultipleWordsAndSecondWordAsSuffix() {

		var matches = createMatcher().findMatches("bar oo");
		new Asserter(matches)//
			.assertMatch(BAR_FOO_5, "0..3, 5..7")
			.assertMatch(BAR_FOO_ZZZ_8, "0..3, 5..7")
			.assertMatch(ZZZ_BAR_FOO_7, "4..7, 9..11")
			.assertMatch(AAA_FOO_BAR_2, "5..7, 8..11")
			.assertMatch(FOO_BAR_3, "1..3, 4..7")
			.assertMatch(FOO_BAR_AAA_4, "1..3, 4..7")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithMultipleWordsPrefixes() {

		var matches = createMatcher().findMatches("ba fo");
		new Asserter(matches)//
			.assertMatch(BAR_FOO_5, "0..2, 4..6")
			.assertMatch(BAR_FOO_ZZZ_8, "0..2, 4..6")
			.assertMatch(ZZZ_BAR_FOO_7, "4..6, 8..10")
			.assertMatch(AAA_FOO_BAR_2, "4..6, 8..10")
			.assertMatch(FOO_BAR_3, "0..2, 4..6")
			.assertMatch(FOO_BAR_AAA_4, "0..2, 4..6")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithMultipleRedundantWords() {

		var matches = createMatcher().findMatches("foo foo");
		new Asserter(matches)//
			.assertMatch(FOO_FOO_FOO_1, "0..3, 4..7, 8..11")
			.assertMatch(FOO_BAR_3, "0..3")
			.assertMatch(FOO_BAR_AAA_4, "0..3")
			.assertMatch(AAA_FOO_BAR_2, "4..7")
			.assertMatch(BAR_FOO_5, "4..7")
			.assertMatch(BAR_FOO_ZZZ_8, "4..7")
			.assertMatch(ZZZ_BAR_FOO_7, "8..11")
			.assertNoMoreMatches();

		matches = createMatcher().findMatches("bar bar");
		new Asserter(matches)//
			.assertMatch(BAR_BAR_BAR_6, "0..3, 4..7, 8..11")
			.assertMatch(BAR_FOO_5, "0..3")
			.assertMatch(BAR_FOO_ZZZ_8, "0..3")
			.assertMatch(AAA_FOO_BAR_2, "8..11")
			.assertMatch(FOO_BAR_3, "4..7")
			.assertMatch(FOO_BAR_AAA_4, "4..7")
			.assertMatch(ZZZ_BAR_FOO_7, "4..7")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithMultipleRedundantWordPrefixes() {

		var matches = createMatcher().findMatches("ba ba");
		new Asserter(matches)//
			.assertMatch(BAR_BAR_BAR_6, "0..2, 4..6, 8..10")
			.assertMatch(BAR_FOO_5, "0..2")
			.assertMatch(BAR_FOO_ZZZ_8, "0..2")
			.assertMatch(AAA_FOO_BAR_2, "8..10")
			.assertMatch(FOO_BAR_3, "4..6")
			.assertMatch(FOO_BAR_AAA_4, "4..6")
			.assertMatch(ZZZ_BAR_FOO_7, "4..6")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithMultipleWordsAndSubstringAmongWords() {

		var matches = createMatcher().findMatches("ba b");
		new Asserter(matches)//
			.assertMatch(BAR_BAR_BAR_6, "0..1, 0..2, 4..5, 4..6, 8..9, 8..10")
			.assertMatch(BAR_FOO_5, "0..1, 0..2")
			.assertMatch(BAR_FOO_ZZZ_8, "0..1, 0..2")
			.assertMatch(AAA_FOO_BAR_2, "8..9, 8..10")
			.assertMatch(FOO_BAR_3, "4..5, 4..6")
			.assertMatch(FOO_BAR_AAA_4, "4..5, 4..6")
			.assertMatch(ZZZ_BAR_FOO_7, "4..5, 4..6")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithMultipleSingleCharacterWords() {

		var matches = createMatcher().findMatches("z f");
		new Asserter(matches)//
			.assertMatch(ZZZ_BAR_FOO_7, "0..1, 1..2, 2..3, 8..9")
			.assertMatch(BAR_FOO_ZZZ_8, "4..5, 8..9, 9..10, 10..11")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithMultipleRedundantSingleCharacterWords() {

		var matches = createMatcher().findMatches("b b");
		new Asserter(matches)//
			.assertMatch(BAR_BAR_BAR_6, "0..1, 4..5, 8..9")
			.assertMatch(BAR_FOO_5, "0..1")
			.assertMatch(BAR_FOO_ZZZ_8, "0..1")
			.assertMatch(AAA_FOO_BAR_2, "8..9")
			.assertMatch(FOO_BAR_3, "4..5")
			.assertMatch(FOO_BAR_AAA_4, "4..5")
			.assertMatch(ZZZ_BAR_FOO_7, "4..5")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithDiacriticsInWordAndDiacriticsIgnored() {

		var matches = createMatcher().setIgnoreDiacritics(true).findMatches("föô");
		new Asserter(matches)//
			.assertMatch(FOO_BAR_3, "0..3")
			.assertMatch(FOO_BAR_AAA_4, "0..3")
			.assertMatch(FOO_FOO_FOO_1, "0..3, 4..7, 8..11")
			.assertMatch(AAA_FOO_BAR_2, "4..7")
			.assertMatch(BAR_FOO_5, "4..7")
			.assertMatch(BAR_FOO_ZZZ_8, "4..7")
			.assertMatch(ZZZ_BAR_FOO_7, "8..11")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithDiacriticsInWordAndDiacriticsNotIgnored() {

		var matches = createMatcher().setIgnoreDiacritics(false).findMatches("föô");
		new Asserter(matches)//
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithDiacriticsInIdentifierAndDiacriticsIgnored() {

		setup.replaceIdentifiers("bar", "bár");

		var matches = createMatcher().setIgnoreDiacritics(true).findMatches("a");
		new Asserter(matches)//
			.assertMatch(AAA_FOO_BAR_2, "0..1, 1..2, 2..3, 9..10")
			.assertMatch(BAR_BAR_BAR_6, "1..2, 5..6, 9..10")
			.assertMatch(BAR_FOO_5, "1..2")
			.assertMatch(BAR_FOO_ZZZ_8, "1..2")
			.assertMatch(FOO_BAR_3, "5..6")
			.assertMatch(FOO_BAR_AAA_4, "5..6, 8..9, 9..10, 10..11")
			.assertMatch(ZZZ_BAR_FOO_7, "5..6")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithDiacriticsInIdentifierAndDiacriticsNotIgnored() {

		setup.replaceIdentifiers("bar", "bár");

		var matches = createMatcher().setIgnoreDiacritics(false).findMatches("a");
		new Asserter(matches)//
			.assertMatch(AAA_FOO_BAR_2, "0..1, 1..2, 2..3")
			.assertMatch(FOO_BAR_AAA_4, "8..9, 9..10, 10..11")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithDiacriticsInIdentifierAndInWordAndDiacriticsIgnored() {

		setup.replaceIdentifiers("bar", "bár");

		var matches = createMatcher().setIgnoreDiacritics(true).findMatches("á");
		new Asserter(matches)//
			.assertMatch(AAA_FOO_BAR_2, "0..1, 1..2, 2..3, 9..10")
			.assertMatch(BAR_BAR_BAR_6, "1..2, 5..6, 9..10")
			.assertMatch(BAR_FOO_5, "1..2")
			.assertMatch(BAR_FOO_ZZZ_8, "1..2")
			.assertMatch(FOO_BAR_3, "5..6")
			.assertMatch(FOO_BAR_AAA_4, "5..6, 8..9, 9..10, 10..11")
			.assertMatch(ZZZ_BAR_FOO_7, "5..6")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithDiacriticsInIdentifierAndInWordAndDiacriticsNotIgnored() {

		setup.replaceIdentifiers("bar", "bár");

		var matches = createMatcher().setIgnoreDiacritics(false).findMatches("á");
		new Asserter(matches)//
			.assertMatch(AAA_FOO_BAR_2, "9..10")
			.assertMatch(BAR_BAR_BAR_6, "1..2, 5..6, 9..10")
			.assertMatch(BAR_FOO_5, "1..2")
			.assertMatch(BAR_FOO_ZZZ_8, "1..2")
			.assertMatch(FOO_BAR_3, "5..6")
			.assertMatch(FOO_BAR_AAA_4, "5..6")
			.assertMatch(ZZZ_BAR_FOO_7, "5..6")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithUpperCaseInIdentifier() {

		setup.replaceIdentifiers("bar", "BAR");

		var matches = createMatcher().findMatches("bar");
		new Asserter(matches)//
			.assertMatch(BAR_BAR_BAR_6, "0..3, 4..7, 8..11")
			.assertMatch(BAR_FOO_5, "0..3")
			.assertMatch(BAR_FOO_ZZZ_8, "0..3")
			.assertMatch(AAA_FOO_BAR_2, "8..11")
			.assertMatch(FOO_BAR_3, "4..7")
			.assertMatch(FOO_BAR_AAA_4, "4..7")
			.assertMatch(ZZZ_BAR_FOO_7, "4..7")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithUpperCaseInIdentifierAndInWord() {

		setup.replaceIdentifiers("bar", "BAR");

		var matches = createMatcher().findMatches("BAR");
		new Asserter(matches)//
			.assertMatch(BAR_BAR_BAR_6, "0..3, 4..7, 8..11")
			.assertMatch(BAR_FOO_5, "0..3")
			.assertMatch(BAR_FOO_ZZZ_8, "0..3")
			.assertMatch(AAA_FOO_BAR_2, "8..11")
			.assertMatch(FOO_BAR_3, "4..7")
			.assertMatch(FOO_BAR_AAA_4, "4..7")
			.assertMatch(ZZZ_BAR_FOO_7, "4..7")
			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithUpperCaseInWord() {

		var matches = createMatcher().findMatches("FOO");
		new Asserter(matches)//
			.assertMatch(FOO_BAR_3, "0..3")
			.assertMatch(FOO_BAR_AAA_4, "0..3")
			.assertMatch(FOO_FOO_FOO_1, "0..3, 4..7, 8..11")
			.assertMatch(AAA_FOO_BAR_2, "4..7")
			.assertMatch(BAR_FOO_5, "4..7")
			.assertMatch(BAR_FOO_ZZZ_8, "4..7")
			.assertMatch(ZZZ_BAR_FOO_7, "8..11")
			.assertNoMoreMatches();
	}

	private AutoCompleteMatcher<Integer> createMatcher() {

		return new AutoCompleteMatcher<>(setup.getEntryMap());
	}

	private class Setup {

		private final Map<String, Integer> entryMap;

		public Setup() {

			entryMap = new TreeMap<>();
		}

		public Map<String, Integer> getEntryMap() {

			return entryMap;
		}

		public void addEntry(Pair<Integer, String> entry) {

			entryMap.put(entry.getSecond(), entry.getFirst());
		}

		public void replaceIdentifiers(String keySubstring, String keySubstringReplacement) {

			var newEntryMap = new TreeMap<String, Integer>();
			for (var entry: entryMap.entrySet()) {
				String modifiedKey = entry.getKey().replaceAll(keySubstring, keySubstringReplacement);
				newEntryMap.put(modifiedKey, entry.getValue());
			}

			entryMap.clear();
			entryMap.putAll(newEntryMap);
		}
	}

	private class Asserter {

		private final AutoCompleteMatches<Integer> matches;
		private int matchIndex;

		public Asserter(AutoCompleteMatches<Integer> matches) {

			this.matches = matches;
			this.matchIndex = -1;
		}

		public Asserter assertMatch(Pair<Integer, String> entry, String expectedRangeDefinitions) {

			assertTrue("Found fewer matches than expected.", matchIndex + 1 < matches.size());
			this.matchIndex++;

			// assert value
			var match = matches.getAll().get(matchIndex);
			assertEquals("Unexpected value.", entry.getFirst(), match.getValue());

			// assert ranges
			var expectedRangePairs = extractRangePairs(expectedRangeDefinitions);
			var matchRanges = match.getAllMatchRanges();
			int matchRangeIndex = 0;
			for (; matchRangeIndex < expectedRangePairs.size(); matchRangeIndex++) {
				var expectedRange = expectedRangePairs.get(matchRangeIndex);
				assertTrue("Found fewer match ranges than expected.", matchRangeIndex < matchRanges.size());
				var matchRange = matchRanges.get(matchRangeIndex);
				assertEquals("Unexpected range start.", (int) expectedRange.getFirst(), matchRange.getLowerIndex());
				assertEquals("Unexpected range end.", (int) expectedRange.getSecond(), matchRange.getUpperIndex());
			}
			assertEquals("Unexpected number of match ranges.", matchRangeIndex, matchRanges.size());

			return this;
		}

		public void assertNoMoreMatches() {

			assertTrue(matchIndex == matches.size() - 1);
		}

		private List<Pair<Integer, Integer>> extractRangePairs(String rangeDefinitions) {

			var expectedRanges = new ArrayList<Pair<Integer, Integer>>();
			for (String rangeDefinition: rangeDefinitions.split(",")) {
				String[] indexes = rangeDefinition.trim().split("\\.\\.");
				assertEquals(2, indexes.length);
				expectedRanges.add(new Pair<>(Integer.parseInt(indexes[0]), Integer.parseInt(indexes[1])));
			}
			return expectedRanges;
		}
	}
}

package com.softicar.platform.dom.elements.input.auto.pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;

public class MultiPatternMatcherTest {

	private static final Integer VALUE1 = 1;
	private static final Integer VALUE2 = 2;
	private static final Integer VALUE3 = 3;
	private static final Integer VALUE4 = 4;

	private final Map<String, Integer> map;

	public MultiPatternMatcherTest() {

		map = new TreeMap<>();
	}

	@Test
	public void testFindMatchesWithEmptyPattern() {

		addTestValue("foo bar", VALUE1);
		addTestValue("foo xxx", VALUE2);
		addTestValue("foo bar baz", VALUE3);

		var matches = createMatcher().findMatches("");

		new Asserter(matches)//
			.nextMatch(VALUE1)
			.nextRange(0, 0)
			.assertNoMoreRanges()

			.nextMatch(VALUE3)
			.nextRange(0, 0)
			.assertNoMoreRanges()

			.nextMatch(VALUE2)
			.nextRange(0, 0)
			.assertNoMoreRanges()

			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithSinglePattern() {

		addTestValue("foo bar", VALUE1);
		addTestValue("foo xxx", VALUE2);
		addTestValue("foo bar baz", VALUE3);

		var matches = createMatcher().findMatches("ba");

		new Asserter(matches)//
			.nextMatch(VALUE3)
			.nextRange(4, 6)
			.nextRange(8, 10)
			.assertNoMoreRanges()

			.nextMatch(VALUE1)
			.nextRange(4, 6)
			.assertNoMoreRanges()

			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithSinglePatternAndTailingWhitespace() {

		addTestValue("foo bar", VALUE1);
		addTestValue("foo xxx", VALUE2);
		addTestValue("foo bar baz", VALUE3);

		var matches = createMatcher().findMatches("ba ");

		new Asserter(matches)//
			.nextMatch(VALUE3)
			.nextRange(4, 6)
			.nextRange(8, 10)
			.assertNoMoreRanges()

			.nextMatch(VALUE1)
			.nextRange(4, 6)
			.assertNoMoreRanges()

			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithSinglePatternRedundant() {

		addTestValue("foo bar", VALUE1);
		addTestValue("foo xxx", VALUE2);
		addTestValue("foo bar baz", VALUE3);

		var matches = createMatcher().findMatches("ba ba");

		new Asserter(matches)//
			.nextMatch(VALUE3)
			.nextRange(4, 6)
			.nextRange(8, 10)
			.assertNoMoreRanges()

			.nextMatch(VALUE1)
			.nextRange(4, 6)
			.assertNoMoreRanges()

			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithMultiplePatterns() {

		addTestValue("foo bar", VALUE1);
		addTestValue("foo xxx", VALUE2);
		addTestValue("foo bar baz", VALUE3);

		var matches = createMatcher().findMatches("ba foo");

		new Asserter(matches)//
			.nextMatch(VALUE3)
			.nextRange(0, 3)
			.nextRange(4, 6)
			.nextRange(8, 10)
			.assertNoMoreRanges()

			.nextMatch(VALUE1)
			.nextRange(0, 3)
			.nextRange(4, 6)
			.assertNoMoreRanges()

			.nextMatch(VALUE2)
			.nextRange(0, 3)
			.assertNoMoreRanges()

			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithMultiplePatternsAndSubstring() {

		addTestValue("foo bar", VALUE1);
		addTestValue("foo xxx", VALUE2);
		addTestValue("foo bar baz", VALUE3);

		// "b" is a substring of "ba"
		var matches = createMatcher().findMatches("ba b");

		new Asserter(matches)//
			.nextMatch(VALUE3)
			.nextRange(4, 5)
			.nextRange(4, 6)
			.nextRange(8, 9)
			.nextRange(8, 10)
			.assertNoMoreRanges()

			.nextMatch(VALUE1)
			.nextRange(4, 5)
			.nextRange(4, 6)
			.assertNoMoreRanges()

			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithSingleCharacterPattern() {

		addTestValue("cXxXz", VALUE1);
		addTestValue("bXxXz", VALUE2);
		addTestValue("aXXz", VALUE3);
		addTestValue("dddd", VALUE4);

		var matches = createMatcher().findMatches("x");

		new Asserter(matches)//
			.nextMatch(VALUE2)
			.nextRange(1, 2)
			.nextRange(2, 3)
			.nextRange(3, 4)
			.assertNoMoreRanges()

			.nextMatch(VALUE1)
			.nextRange(1, 2)
			.nextRange(2, 3)
			.nextRange(3, 4)
			.assertNoMoreRanges()

			.nextMatch(VALUE3)
			.nextRange(1, 2)
			.nextRange(2, 3)
			.assertNoMoreRanges()

			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithSingleCharacterPatternAndLimit() {

		addTestValue("cXxXz", VALUE1);
		addTestValue("bXxXz", VALUE2);
		addTestValue("aXXz", VALUE3);
		addTestValue("dddd", VALUE4);

		var matches = createMatcher().findMatches("x", 2);

		new Asserter(matches)//
			.nextMatch(VALUE2)
			.nextRange(1, 2)
			.nextRange(2, 3)
			.nextRange(3, 4)
			.assertNoMoreRanges()

			.nextMatch(VALUE1)
			.nextRange(1, 2)
			.nextRange(2, 3)
			.nextRange(3, 4)
			.assertNoMoreRanges()

			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithSingleCharacterPatternRedundant() {

		addTestValue("cXxXz", VALUE1);
		addTestValue("bXxXz", VALUE2);
		addTestValue("aXXz", VALUE3);
		addTestValue("dddd", VALUE4);

		var matches = createMatcher().findMatches("x x");

		new Asserter(matches)//
			.nextMatch(VALUE2)
			.nextRange(1, 2)
			.nextRange(2, 3)
			.nextRange(3, 4)
			.assertNoMoreRanges()

			.nextMatch(VALUE1)
			.nextRange(1, 2)
			.nextRange(2, 3)
			.nextRange(3, 4)
			.assertNoMoreRanges()

			.nextMatch(VALUE3)
			.nextRange(1, 2)
			.nextRange(2, 3)
			.assertNoMoreRanges()

			.assertNoMoreMatches();
	}

	@Test
	public void testFindMatchesWithMultipleSingleCharacterPatterns() {

		addTestValue("cXxXz", VALUE1);
		addTestValue("bXxXz", VALUE2);
		addTestValue("aXXz", VALUE3);
		addTestValue("dddd", VALUE4);

		var matches = createMatcher().findMatches("x d");

		new Asserter(matches)//
			.nextMatch(VALUE4)
			.nextRange(0, 1)
			.nextRange(1, 2)
			.nextRange(2, 3)
			.nextRange(3, 4)
			.assertNoMoreRanges()

			.nextMatch(VALUE2)
			.nextRange(1, 2)
			.nextRange(2, 3)
			.nextRange(3, 4)
			.assertNoMoreRanges()

			.nextMatch(VALUE1)
			.nextRange(1, 2)
			.nextRange(2, 3)
			.nextRange(3, 4)
			.assertNoMoreRanges()

			.nextMatch(VALUE3)
			.nextRange(1, 2)
			.nextRange(2, 3)
			.assertNoMoreRanges()

			.assertNoMoreMatches();
	}

	private void addTestValue(String pattern, Integer value) {

		map.put(pattern, value);
	}

	private MultiPatternMatcher<Integer> createMatcher() {

		return new MultiPatternMatcher<>(map);
	}

	private class Asserter {

		private final List<MultiPatternMatch<Integer>> matches;
		private int matchIndex;
		private int matchRangeIndex;
		private MultiPatternMatch<Integer> match;
		private List<MultiPatternMatchRange> matchRanges;

		public Asserter(List<MultiPatternMatch<Integer>> matches) {

			this.matches = matches;
			this.matchIndex = -1;
			this.matchRangeIndex = -1;
			this.match = null;
			this.matchRanges = null;
		}

		public Asserter nextMatch(Integer value) {

			assertTrue(matchIndex + 1 < matches.size());
			this.matchIndex++;
			this.matchRangeIndex = -1;
			this.match = matches.get(matchIndex);
			this.matchRanges = match.getRanges();
			assertEquals(value, match.getValue());
			return this;
		}

		public Asserter nextRange(int fromIndex, int toIndex) {

			assertTrue(matchRangeIndex + 1 < matchRanges.size());
			this.matchRangeIndex++;
			var matchRange = matchRanges.get(matchRangeIndex);
			assertEquals(fromIndex, matchRange.getFromIndex());
			assertEquals(toIndex, matchRange.getToIndex());
			return this;
		}

		public Asserter assertNoMoreRanges() {

			assertTrue(matchRangeIndex == matchRanges.size() - 1);
			return this;
		}

		public void assertNoMoreMatches() {

			assertTrue(matchIndex == matches.size() - 1);
		}
	}
}

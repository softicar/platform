package com.softicar.platform.common.string;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class SubstringCounterTest extends AbstractTest {

	@Test
	public void testCountOccurrences() {

		String haystack = "FooBarBaz";
		String needle = "Ba";
		int occurrences = SubstringCounter.countOccurrences(haystack, needle);
		assertEquals(2, occurrences);
	}

	@Test
	public void testCountOccurrencesWithCaseDifference() {

		String haystack = "FooBarbaz";
		String needle = "Ba";
		int occurrences = SubstringCounter.countOccurrences(haystack, needle);
		assertEquals(1, occurrences);
	}

	@Test
	public void testCountOccurrencesWithEscapeSequences() {

		String haystack = "Foo\nBar\nbaz";
		String needle = "\n";
		int occurrences = SubstringCounter.countOccurrences(haystack, needle);
		assertEquals(2, occurrences);
	}

	@Test
	public void testCountOccurrencesWithoutMatch() {

		String haystack = "FooBarBaz";
		String needle = "cantFindMe";
		int occurrences = SubstringCounter.countOccurrences(haystack, needle);
		assertEquals(0, occurrences);
	}

	@Test
	public void testCountOccurrencesWithEmptyhaystack() {

		String haystack = "";
		String needle = "Ba";
		int occurrences = SubstringCounter.countOccurrences(haystack, needle);
		assertEquals(0, occurrences);
	}

	@Test
	public void testCountOccurrencesWithEmptyNeedle() {

		String haystack = "FooBarBaz";
		String needle = "";
		int occurrences = SubstringCounter.countOccurrences(haystack, needle);
		assertEquals(0, occurrences);
	}

	@Test
	public void testCountOccurrencesWithNullHaystack() {

		String haystack = null;
		String needle = "Foo";
		int occurrences = SubstringCounter.countOccurrences(haystack, needle);
		assertEquals(0, occurrences);
	}

	@Test
	public void testCountOccurrencesWithNullNeedle() {

		String haystack = "FooBarBaz";
		String needle = null;
		int occurrences = SubstringCounter.countOccurrences(haystack, needle);
		assertEquals(0, occurrences);
	}
}

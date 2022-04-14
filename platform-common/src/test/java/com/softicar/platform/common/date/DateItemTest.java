package com.softicar.platform.common.date;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

public class DateItemTest extends AbstractTest {

	@Test
	public void testMaxWithCollection() {

		Day max = Day.max(Arrays.asList(Day.fromYMD(2020, 1, 10), Day.fromYMD(2020, 1, 30), Day.fromYMD(2020, 1, 20)));

		assertEquals(Day.fromYMD(2020, 1, 30), max);
	}

	@Test
	public void testMaxWithCollectionAndNullArgument() {

		Day max = Day.max(Arrays.asList(Day.fromYMD(2020, 1, 10), Day.fromYMD(2020, 1, 30), null));

		assertEquals(Day.fromYMD(2020, 1, 30), max);
	}

	@Test
	public void testMaxWithCollectionAndOnlyNullArguments() {

		Day max = Day.max(Arrays.<Day> asList(null, null));

		assertNull(max);
	}

	@Test
	public void testMaxWithEmptyCollection() {

		Day max = Day.max(Collections.<Day> emptyList());

		assertNull(max);
	}

	@Test
	public void testMaxWithVarargs() {

		Day max = Day.max(Day.fromYMD(2020, 1, 10), Day.fromYMD(2020, 1, 30), Day.fromYMD(2020, 1, 20));

		assertEquals(Day.fromYMD(2020, 1, 30), max);
	}

	@Test
	public void testMaxWithVarargsAndNullArgument() {

		Day max = Day.max(Day.fromYMD(2020, 1, 10), Day.fromYMD(2020, 1, 30), null);

		assertEquals(Day.fromYMD(2020, 1, 30), max);
	}

	@Test
	public void testMaxWithVarargsAndOnlyNullArguments() {

		Day max = Day.max(null, null);

		assertNull(max);
	}

	@Test
	public void testMaxWithEmptyVarargs() {

		Day max = Day.max();

		assertNull(max);
	}

	@Test
	public void testMinWithCollection() {

		Day min = Day.min(Arrays.asList(Day.fromYMD(2020, 1, 30), Day.fromYMD(2020, 1, 10), Day.fromYMD(2020, 1, 20)));

		assertEquals(Day.fromYMD(2020, 1, 10), min);
	}

	@Test
	public void testMinWithCollectionAndNullArgument() {

		Day min = Day.min(Arrays.asList(Day.fromYMD(2020, 1, 30), Day.fromYMD(2020, 1, 10), null));

		assertEquals(Day.fromYMD(2020, 1, 10), min);
	}

	@Test
	public void testMinWithCollectionAndOnlyNullArguments() {

		Day min = Day.min(Arrays.<Day> asList(null, null));

		assertNull(min);
	}

	@Test
	public void testMinWithEmptyCollection() {

		Day min = Day.min(Collections.<Day> emptyList());

		assertNull(min);
	}

	@Test
	public void testMinWithVarargs() {

		Day min = Day.min(Day.fromYMD(2020, 1, 30), Day.fromYMD(2020, 1, 10), Day.fromYMD(2020, 1, 20));

		assertEquals(Day.fromYMD(2020, 1, 10), min);
	}

	@Test
	public void testMinWithVarargsAndNullArgument() {

		Day min = Day.min(Day.fromYMD(2020, 1, 30), Day.fromYMD(2020, 1, 10), null);

		assertEquals(Day.fromYMD(2020, 1, 10), min);
	}

	@Test
	public void testMinWithVarargsAndOnlyNullArguments() {

		Day min = Day.min(null, null);

		assertNull(min);
	}

	@Test
	public void testMinWithEmptyVarargs() {

		Day min = Day.min();

		assertNull(min);
	}

	@Test
	public void testIsBetween() {

		Day candidate = Day.fromYMD(2020, 1, 20);
		Day first = Day.fromYMD(2020, 1, 10);
		Day last = Day.fromYMD(2020, 1, 30);

		assertTrue(candidate.isBetween(first, last));
	}

	@Test
	public void testIsBetweenAtLowerBoundary() {

		Day candidate = Day.fromYMD(2020, 1, 10);
		Day first = Day.fromYMD(2020, 1, 10);
		Day last = Day.fromYMD(2020, 1, 30);

		assertTrue(candidate.isBetween(first, last));
	}

	@Test
	public void testIsBetweenBelowLowerBoundary() {

		Day candidate = Day.fromYMD(2020, 1, 5);
		Day first = Day.fromYMD(2020, 1, 10);
		Day last = Day.fromYMD(2020, 1, 30);

		assertFalse(candidate.isBetween(first, last));
	}

	@Test
	public void testIsBetweenAtUpperBoundary() {

		Day candidate = Day.fromYMD(2020, 1, 30);
		Day first = Day.fromYMD(2020, 1, 10);
		Day last = Day.fromYMD(2020, 1, 30);

		assertTrue(candidate.isBetween(first, last));
	}

	@Test
	public void testIsBetweenAboveUpperBoundary() {

		Day candidate = Day.fromYMD(2020, 1, 31);
		Day first = Day.fromYMD(2020, 1, 10);
		Day last = Day.fromYMD(2020, 1, 30);

		assertFalse(candidate.isBetween(first, last));
	}

	@Test
	public void testIsBetweenWithNegativePeriod() {

		Day candidate = Day.fromYMD(2020, 1, 20);
		Day first = Day.fromYMD(2020, 1, 30);
		Day last = Day.fromYMD(2020, 1, 10);

		assertFalse(candidate.isBetween(first, last));
	}

	@Test
	public void testIsBetweenWithTrivialPeriod() {

		Day candidate = Day.fromYMD(2020, 1, 20);
		Day first = Day.fromYMD(2020, 1, 20);
		Day last = Day.fromYMD(2020, 1, 20);

		assertTrue(candidate.isBetween(first, last));
	}
}

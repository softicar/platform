package com.softicar.platform.common.core.utils;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class CompareUtilsTest extends AbstractTest {

	private static final int SOME_NUMBER = 1337;

	@Test
	public void compareNullFirst() {

		assertEquals(0, CompareUtils.compareNullFirst(null, null));
		assertEquals(-1, CompareUtils.compareNullFirst(null, 1));
		assertEquals(+1, CompareUtils.compareNullFirst(1, null));

		assertEquals(0, CompareUtils.compareNullFirst(1, 1));
		assertEquals(-1, CompareUtils.compareNullFirst(1, 2));
		assertEquals(+1, CompareUtils.compareNullFirst(2, 1));
	}

	@Test
	public void comparesCorrectly() {

		assertTrue(CompareUtils.compareTo(SOME_NUMBER, SOME_NUMBER) == 0);
		assertTrue(CompareUtils.compareTo(SOME_NUMBER, SOME_NUMBER + 1) < 0);
		assertTrue(CompareUtils.compareTo(SOME_NUMBER, SOME_NUMBER - 1) > 0);
		assertTrue(CompareUtils.compareTo("1337", "1337") == 0);
		assertTrue(CompareUtils.compareTo("1336", "1337") < 0);
		assertTrue(CompareUtils.compareTo("1337", "1336") > 0);
		assertTrue(CompareUtils.compareTo(new Base(SOME_NUMBER), new Derived(SOME_NUMBER)) == 0);
		assertTrue(CompareUtils.compareTo(new Base(SOME_NUMBER), new Derived(SOME_NUMBER + 1)) < 0);
		assertTrue(CompareUtils.compareTo(new Base(SOME_NUMBER), new Derived(SOME_NUMBER - 1)) > 0);
	}

	@Test(expected = SofticarDeveloperException.class)
	public void throwsOnIncompatibleTypes() {

		CompareUtils.compareTo(1, "1336");
	}

	@Test(expected = SofticarDeveloperException.class)
	public void throwsOnNotComparable() {

		CompareUtils.compareTo(new int[1], 1);
	}

	private static class Base implements Comparable<Base> {

		private final int value;

		public Base(int value) {

			this.value = value;
		}

		@Override
		public int compareTo(Base o) {

			return value - o.value;
		}
	}

	private static class Derived extends Base {

		Derived(int value) {

			super(value);
		}
	}
}

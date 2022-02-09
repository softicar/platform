package com.softicar.platform.common.testing;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class AssertsTest {

	@Test
	public void testAssertThrows() {

		Asserts.assertException(NumberFormatException.class, () -> Integer.parseInt("foo"));
	}

	@Test
	public void assertAll() {

		Iterable<Integer> items = Arrays.asList(5, 6, 7);
		Asserts.assertAll(items, item -> item > 3);
	}

	@Test
	public void assertAllWithEmptyIterable() {

		Iterable<Integer> items = new ArrayList<>();
		Asserts.assertAll(items, item -> item > 3);
	}

	@Test(expected = AssertionError.class)
	public void assertAllWithViolation() {

		Iterable<Integer> items = Arrays.asList(5, 6, 7);
		Asserts.assertAll(items, item -> item > 5);
	}

	@Test
	public void assertAny() {

		Iterable<Integer> items = Arrays.asList(5, 6, 7);
		Asserts.assertAny(items, item -> item > 6);
	}

	@Test(expected = AssertionError.class)
	public void assertAnyWithEmptyIterable() {

		Iterable<Integer> items = Arrays.asList(5, 6, 7);
		Asserts.assertAny(items, item -> item > 7);
	}

	@Test(expected = AssertionError.class)
	public void assertAnyWithViolation() {

		Iterable<Integer> items = Arrays.asList(5, 6, 7);
		Asserts.assertAny(items, item -> item > 10);
	}
}

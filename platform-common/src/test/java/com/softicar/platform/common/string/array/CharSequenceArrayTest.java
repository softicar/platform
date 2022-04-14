package com.softicar.platform.common.string.array;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class CharSequenceArrayTest extends AbstractTest {

	private static final char[] ARRAY = "FooBarBaz".toCharArray();

	@Test
	public void testConstructorWithSize() {

		CharSequenceArray array = new CharSequenceArray(3);

		assertEquals(3, array.length());
		assertEquals(0, array.charAt(0));
		assertEquals(0, array.charAt(1));
		assertEquals(0, array.charAt(2));
	}

	@Test
	public void testConstructorWithArray() {

		assertAll("", new CharSequenceArray(new char[0]));
		assertAll("FooBarBaz", new CharSequenceArray(ARRAY));
	}

	@Test
	public void testConstructorWithArrayAndIndex() {

		assertAll("", new CharSequenceArray(ARRAY, 0, 0));
		assertAll("Foo", new CharSequenceArray(ARRAY, 0, 3));
		assertAll("Bar", new CharSequenceArray(ARRAY, 3, 6));
		assertAll("Baz", new CharSequenceArray(ARRAY, 6, 9));
		assertAll("FooBarBaz", new CharSequenceArray(ARRAY, 0, 9));
	}

	@Test
	public void testSubSequence() {

		assertAll("Bar", new CharSequenceArray(ARRAY).subSequence(3, 6));
		assertAll("a", new CharSequenceArray(ARRAY).subSequence(3, 6).subSequence(1, 2));
	}

	private static void assertAll(String expected, CharSequence actualSequence) {

		// test toString()
		assertEquals(expected, actualSequence.toString());

		// test length()
		assertEquals(expected.length(), actualSequence.length());

		// test charAt()
		for (int i = 0; i < expected.length(); i++) {
			assertEquals(expected.charAt(i), actualSequence.charAt(i));
		}
	}
}

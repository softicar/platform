package com.softicar.platform.common.string;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;

/**
 * Test cases for {@link Padding}.
 *
 * @author Oliver Richers
 */
public class PaddingTest {

	// -------------------- generate -------------------- //

	@Test(expected = IllegalArgumentException.class)
	public void testThrowsOnGenerateNegative() {

		Padding.generate(' ', -3);
	}

	@Test
	public void testGenerate() {

		assertEquals("", Padding.generate('0', 0));
		assertEquals("", Padding.generate('-', 0));
		assertEquals("000", Padding.generate('0', 3));
		assertEquals("-----", Padding.generate('-', 5));
		assertEquals("      ", Padding.generate(' ', 6));
	}

	@Test
	public void testGenerateStringBuilder() {

		StringBuilder builder = new StringBuilder();
		assertSame(builder, Padding.generate(builder, 'x', 20));
	}

	// -------------------- padCenter -------------------- //

	@Test
	public void testPadCenter() {

		assertEquals("Hello", Padding.padCenter("Hello", 'x', 0));
		assertEquals("Hello", Padding.padCenter("Hello", 'x', 2));
		assertEquals("Hello", Padding.padCenter("Hello", 'x', 5));
		assertEquals("Hellox", Padding.padCenter("Hello", 'x', 6));
		assertEquals("xHellox", Padding.padCenter("Hello", 'x', 7));
		assertEquals("xHelloxx", Padding.padCenter("Hello", 'x', 8));
		assertEquals("xxHelloxx", Padding.padCenter("Hello", 'x', 9));
	}

	// -------------------- padLeft -------------------- //

	@Test
	public void testPadLeftWithChar() {

		assertEquals("Hello", Padding.padLeft("Hello", 'x', 0));
		assertEquals("Hello", Padding.padLeft("Hello", 'x', 2));
		assertEquals("Hello", Padding.padLeft("Hello", 'x', 5));
		assertEquals("xHello", Padding.padLeft("Hello", 'x', 6));
		assertEquals("xxHello", Padding.padLeft("Hello", 'x', 7));
		assertEquals("xxxHello", Padding.padLeft("Hello", 'x', 8));
	}

	@Test
	public void testPadLeftWithString() {

		assertEquals("Hello", Padding.padLeft("Hello", "ABC", 0));
		assertEquals("Hello", Padding.padLeft("Hello", "ABC", 2));
		assertEquals("Hello", Padding.padLeft("Hello", "ABC", 5));
		assertEquals("ABCHello", Padding.padLeft("Hello", "ABC", 6));
		assertEquals("ABCHello", Padding.padLeft("Hello", "ABC", 7));
		assertEquals("ABCHello", Padding.padLeft("Hello", "ABC", 8));
		assertEquals("ABCABCHello", Padding.padLeft("Hello", "ABC", 9));
	}

	// -------------------- padRight -------------------- //

	@Test
	public void testPadRight() {

		assertEquals("Hello", Padding.padRight("Hello", 'x', 0));
		assertEquals("Hello", Padding.padRight("Hello", 'x', 2));
		assertEquals("Hello", Padding.padRight("Hello", 'x', 5));
		assertEquals("Hellox", Padding.padRight("Hello", 'x', 6));
		assertEquals("Helloxx", Padding.padRight("Hello", 'x', 7));
		assertEquals("Helloxxx", Padding.padRight("Hello", 'x', 8));
	}

	@Test
	public void testPadRightWithString() {

		assertEquals("Hello", Padding.padRight("Hello", "ABC", 0));
		assertEquals("Hello", Padding.padRight("Hello", "ABC", 2));
		assertEquals("Hello", Padding.padRight("Hello", "ABC", 5));
		assertEquals("HelloABC", Padding.padRight("Hello", "ABC", 6));
		assertEquals("HelloABC", Padding.padRight("Hello", "ABC", 7));
		assertEquals("HelloABC", Padding.padRight("Hello", "ABC", 8));
		assertEquals("HelloABCABC", Padding.padRight("Hello", "ABC", 9));
	}
}

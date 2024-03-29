package com.softicar.platform.common.string.ascii;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class AsciiTest extends AbstractTest {

	@Test
	public void testIsAsciiWithAsciiCharacter() {

		assertTrue(Ascii.isAscii('a'));
		assertTrue(Ascii.isAscii('z'));
		assertTrue(Ascii.isAscii(' '));
		assertTrue(Ascii.isAscii('\n'));
	}

	@Test
	public void testIsAsciiWithNonAsciiCharacter() {

		assertFalse(Ascii.isAscii('ä'));
		assertFalse(Ascii.isAscii('ã'));
		assertFalse(Ascii.isAscii('ü'));
		assertFalse(Ascii.isAscii('ć'));
	}

	@Test
	public void testIsAsciiWithAsciiText() {

		assertTrue(Ascii.isAscii("foo"));
	}

	@Test
	public void testIsAsciiWithNonAsciiText() {

		assertFalse(Ascii.isAscii("föõ"));
	}
}

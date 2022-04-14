package com.softicar.platform.common.string.unicode;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class UtfNonBmpCharacterReplacerTest extends AbstractTest {

	@Test
	public void testAscii() {

		assertEquals("abcde", getReplaced("abcde"));
	}

	@Test
	public void testNonAscii() {

		assertEquals("ÁÂÃÄÅÆÈÉÊËÒÓÔÕÖ", getReplaced("ÁÂÃÄÅÆÈÉÊËÒÓÔÕÖ"));
		assertEquals("£¢©ǣ§", getReplaced("£¢©ǣ§"));
	}

	@Test
	public void testNonBasicMultilingualPlane() {

		assertEquals("??", getReplaced("\ud83d\udca9"));
		assertEquals("??", getReplaced("\ud83d\ude01"));
		assertEquals("??", getReplaced("\ud83d\ude1d"));
	}

	private String getReplaced(String input) {

		return new UtfNonBmpCharacterReplacer().getReplaced(input);
	}
}

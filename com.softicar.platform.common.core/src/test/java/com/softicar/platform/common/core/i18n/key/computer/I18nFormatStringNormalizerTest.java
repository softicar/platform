package com.softicar.platform.common.core.i18n.key.computer;

import org.junit.Assert;
import org.junit.Test;

public class I18nFormatStringNormalizerTest extends Assert {

	private static final String REPLACEMENT = "XXXX";

	// ---------------- No Percent-Signs ---------------- //

	@Test
	public void testReplaceEscapedPercentsWithEmptyString() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercents("", REPLACEMENT);
		assertEquals("", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithoutSign() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercents("qwe asd", REPLACEMENT);
		assertEquals("qwe asd", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithSpacesWithEmptyString() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercentsWithSpaces("");
		assertEquals("", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithSpacesWithoutSign() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercentsWithSpaces("qwe asd");
		assertEquals("qwe asd", output);
	}

	// ---------------- Adjacent Percent-Signs ---------------- //

	@Test
	public void testReplaceEscapedPercentsWithOneSign() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercents("qwe %asd", REPLACEMENT);
		assertEquals("qwe %asd", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithTwoSigns() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercents("qwe %%asd", REPLACEMENT);
		assertEquals("qwe XXXXasd", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithThreeSigns() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercents("qwe %%%asd", REPLACEMENT);
		assertEquals("qwe XXXX%asd", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithFourSigns() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercents("qwe %%%%asd", REPLACEMENT);
		assertEquals("qwe XXXXasd", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithFiveSigns() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercents("qwe %%%%%asd", REPLACEMENT);
		assertEquals("qwe XXXX%asd", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithSpacesWithOneSign() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercentsWithSpaces("qwe %asd");
		assertEquals("qwe %asd", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithSpacesWithTwoSigns() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercentsWithSpaces("qwe %%asd");
		assertEquals("qwe   asd", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithSpacesWithThreeSigns() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercentsWithSpaces("qwe %%%asd");
		assertEquals("qwe   %asd", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithSpacesWithFourSigns() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercentsWithSpaces("qwe %%%%asd");
		assertEquals("qwe     asd", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithSpacesWithFiveSigns() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercentsWithSpaces("qwe %%%%%asd");
		assertEquals("qwe     %asd", output);
	}

	// ---------------- Tailing Percent-Signs ---------------- //

	@Test
	public void testReplaceEscapedPercentsWithOneTailingSign() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercents("qwe %", REPLACEMENT);
		assertEquals("qwe %", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithTwoTailingSigns() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercents("qwe %%", REPLACEMENT);
		assertEquals("qwe XXXX", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithSpacesWithOneTailingSign() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercentsWithSpaces("qwe %");
		assertEquals("qwe %", output);
	}

	@Test
	public void testReplaceEscapedPercentsWithSpacesWithTwoTailingSigns() {

		String output = I18nFormatStringNormalizer.replaceEscapedPercentsWithSpaces("qwe %%");
		assertEquals("qwe   ", output);
	}
}

package com.softicar.platform.common.core.number.formatter;

import org.junit.Assert;
import org.junit.Test;

public class DigitGroupFormatterTest extends Assert {

	@Test
	public void test() {

		assertFormattedString("0", "0", ",");
		assertFormattedString("1", "1", ",");
		assertFormattedString("123", "123", ",");
		assertFormattedString("1,123", "1123", ",");
		assertFormattedString("123,123", "123123", ",");
		assertFormattedString("1,123,123", "1123123", ",");
		assertFormattedString("123,123,123", "123123123", ",");
		assertFormattedString("123.123.123", "123123123", ".");

		assertFormattedString("123x123x123", "123123123", "x");
		assertFormattedString("123xyz123xyz123", "123123123", "xyz");
	}

	private void assertFormattedString(String expectedOutput, String input, String separator) {

		assertEquals(expectedOutput, new DigitGroupFormatter(input, separator).format());
	}
}

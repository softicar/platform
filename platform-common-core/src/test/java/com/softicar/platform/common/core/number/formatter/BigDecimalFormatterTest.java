package com.softicar.platform.common.core.number.formatter;

import com.softicar.platform.common.core.locale.ILocale;
import com.softicar.platform.common.core.locale.Locale;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

public class BigDecimalFormatterTest extends Assert {

	private ILocale locale;

	public BigDecimalFormatterTest() {

		this.locale = new Locale()//
			.setDecimalSeparator(",")
			.setDigitGroupSeparator(".");
	}

	@Test
	public void testWithZeroValues() {

		assertFormattedString("0", "0");
		assertFormattedString("0,0", ".0");
		assertFormattedString("0,0", "0.0");

		assertFormattedString("0", "-0");
		assertFormattedString("0,0", "-.0");
		assertFormattedString("0,0", "-0.0");
	}

	@Test
	public void testWithPositiveValues() {

		assertFormattedString("0,0001", "0.0001");
		assertFormattedString("0,1", "0.1");
		assertFormattedString("0,123456", "0.123456");

		assertFormattedString("1", "1");
		assertFormattedString("123", "123");
		assertFormattedString("1.234", "1234");
		assertFormattedString("1.234,12", "1234.12");
		assertFormattedString("123.456,123456", "123456.123456");
	}

	@Test
	public void testWithNegativeValues() {

		assertFormattedString("-0,0001", "-0.0001");
		assertFormattedString("-0,1", "-0.1");
		assertFormattedString("-0,123456", "-0.123456");

		assertFormattedString("-1", "-1");
		assertFormattedString("-123", "-123");
		assertFormattedString("-1.234", "-1234");
		assertFormattedString("-1.234,12", "-1234.12");
		assertFormattedString("-123.456,123456", "-123456.123456");
	}

	@Test
	public void testWithVariousSeparators() {

		this.locale = new Locale().setDecimalSeparator(",").setDigitGroupSeparator(" ");
		assertFormattedString("-123 456,1234", "-123456.1234");

		this.locale = new Locale().setDecimalSeparator(".").setDigitGroupSeparator("'");
		assertFormattedString("-123'456.1234", "-123456.1234");

		this.locale = new Locale().setDecimalSeparator("abc").setDigitGroupSeparator("xyz");
		assertFormattedString("-123xyz456abc1234", "-123456.1234");
	}

	private void assertFormattedString(String expectedOutput, String input) {

		assertEquals(expectedOutput, new BigDecimalFormatter(new BigDecimal(input)).setLocale(locale).format());
	}
}

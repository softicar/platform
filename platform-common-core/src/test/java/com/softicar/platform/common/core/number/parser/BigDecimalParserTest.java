package com.softicar.platform.common.core.number.parser;

import com.softicar.platform.common.core.CommonCoreI18n;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.locale.ILocale;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.common.core.locale.LocaleScope;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

public class BigDecimalParserTest extends Assert {

	private final ILocale locale;

	public BigDecimalParserTest() {

		this.locale = new Locale().setDecimalSeparator(",").setDigitGroupSeparator(".");
	}

	@Test
	public void test() {

		assertParsed("0", "0");
		assertParsed("0.0", "0,0");
		assertParsed("1.23", "1,23");
		assertParsed("1234.567", "1234,567");
		assertParsed("1234.567", "1.234,567");
		assertParsed("1234567.89", "1234567,89");
		assertParsed("1234567.89", "1.234.567,89");

		assertParsed("0", "-0");
		assertParsed("0.0", "-0,0");
		assertParsed("-1.23", "-1,23");
		assertParsed("-1234.567", "-1234,567");
		assertParsed("-1234.567", "-1.234,567");
		assertParsed("-1234567.89", "-1234567,89");
		assertParsed("-1234567.89", "-1.234.567,89");
	}

	@Test
	public void testIllegalInput() {

		assertException("-1.234567,89", CommonCoreI18n.DIGIT_GROUP_TOO_LONG);
		assertException("-1.23.45.67,89", CommonCoreI18n.DIGIT_GROUP_TOO_SHORT);
		assertException("-123,456.789", CommonCoreI18n.THE_DECIMAL_PART_MUST_NOT_CONTAIN_DIGIT_GROUP_SEPARATORS);
	}

	@Test
	public void testWithLocaleScope() {

		var newLocale = new Locale().setDecimalSeparator("_").setDigitGroupSeparator("'");
		try (var scope = new LocaleScope(newLocale)) {
			assertEquals(new BigDecimal("1234567.89"), new BigDecimalParser("1'234'567_89").parseOrThrow());
		}
	}

	private void assertParsed(String expected, String input) {

		assertEquals(new BigDecimal(expected), new BigDecimalParser(input).setLocale(locale).parseOrThrow());
	}

	private void assertException(String input, IDisplayString expectedMessage) {

		try {
			new BigDecimalParser(input).setLocale(locale).parseOrThrow();
		} catch (SofticarUserException exception) {
			assertEquals(expectedMessage.toString(), exception.getMessage());
		}
	}
}

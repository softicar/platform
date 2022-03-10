package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.common.core.locale.LocaleScope;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.input.AbstractDomValueInputTest;
import java.math.BigDecimal;
import org.junit.Test;

public class DomBigDecimalInputTest extends AbstractDomValueInputTest<BigDecimal> {

	public DomBigDecimalInputTest() {

		super(DomBigDecimalInput::new);
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}

	@Test
	public void testGetValue() {

		// test blank input
		assertEmptyResultForGetValue("");
		assertEmptyResultForGetValue(" ");

		// test valid inputs
		assertResultForGetValue("0", "0");
		assertResultForGetValue("123", "123");
		assertResultForGetValue("-123.45", "-123.45");
		assertResultForGetValue("3.12345678", "3.12345678");

		// test illegal inputs
		assertExceptionForGetValue(DomI18n.INVALID_DECIMAL_NUMBER, "3,2");
		assertExceptionForGetValue(DomI18n.INVALID_DECIMAL_NUMBER, "foo");
		assertExceptionForGetValue(DomI18n.INVALID_DECIMAL_NUMBER, "3.4.2");
		assertExceptionForGetValue(DomI18n.INVALID_DECIMAL_NUMBER, "3;2");
		assertExceptionForGetValue(DomI18n.INVALID_DECIMAL_NUMBER, "3 2");
	}

	@Test
	public void testGetValueWithCustomLocale() {

		Locale locale = new Locale()//
			.setDecimalSeparator(",")
			.setDigitGroupSeparator(".");

		try (var scope = new LocaleScope(locale)) {
			// test valid inputs
			assertResultForGetValue("0", "0");
			assertResultForGetValue("123", "123");
			assertResultForGetValue("-1234.56", "-1234,56");
			assertResultForGetValue("-1234.56", "-1.234,56");
			assertResultForGetValue("3.12345678", "3,12345678");

			// test illegal inputs
			assertExceptionForGetValue(DomI18n.INVALID_DECIMAL_NUMBER, "3.2");
			assertExceptionForGetValue(DomI18n.INVALID_DECIMAL_NUMBER, "foo");
			assertExceptionForGetValue(DomI18n.INVALID_DECIMAL_NUMBER, "3,4,2");
			assertExceptionForGetValue(DomI18n.INVALID_DECIMAL_NUMBER, "3;2");
			assertExceptionForGetValue(DomI18n.INVALID_DECIMAL_NUMBER, "3 2");
		}
	}

	private void assertResultForGetValue(String expectedValue, String inputValue) {

		super.assertResultForGetValue(new BigDecimal(expectedValue), inputValue);
	}
}

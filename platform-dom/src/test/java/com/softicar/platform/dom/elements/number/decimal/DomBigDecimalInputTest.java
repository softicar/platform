package com.softicar.platform.dom.elements.number.decimal;

import com.softicar.platform.common.core.CommonCoreI18n;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.common.core.locale.LocaleScope;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.input.AbstractDomValueInputTest;
import com.softicar.platform.dom.input.IDomTextualInput;
import java.math.BigDecimal;
import org.junit.Test;

public class DomBigDecimalInputTest extends AbstractDomValueInputTest<BigDecimal> {

	private static final Locale SPECIAL_LOCALE = new Locale()//
		.setDecimalSeparator(",")
		.setDigitGroupSeparator(".");

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
		assertExceptionForGetValue(DomI18n.INVALID_DECIMAL_NUMBER, "3.4.2");
		assertExceptionForGetValue(DomI18n.ILLEGAL_CHARACTERS_FOR_DECIMAL_NUMBER_ARG1.toDisplay(","), "3,2");
		assertExceptionForGetValue(DomI18n.ILLEGAL_CHARACTERS_FOR_DECIMAL_NUMBER_ARG1.toDisplay("fo"), "foo");
		assertExceptionForGetValue(DomI18n.ILLEGAL_CHARACTERS_FOR_DECIMAL_NUMBER_ARG1.toDisplay(";"), "3;2");
		assertExceptionForGetValue(DomI18n.ILLEGAL_CHARACTERS_FOR_DECIMAL_NUMBER_ARG1.toDisplay(" "), "3 2");
	}

	@Test
	public void testGetValueWithCustomLocale() {

		try (var scope = new LocaleScope(SPECIAL_LOCALE)) {
			// test valid inputs
			assertResultForGetValue("0", "0");
			assertResultForGetValue("123", "123");
			assertResultForGetValue("-1234.56", "-1234,56");
			assertResultForGetValue("3.12345678", "3,12345678");

			// test illegal inputs
			assertExceptionForGetValue(DomI18n.INVALID_DECIMAL_NUMBER, "3,4,2");
			assertExceptionForGetValue(CommonCoreI18n.ILLEGAL_CHARACTERS_FOR_DECIMAL_NUMBER_ARG1.toDisplay("."), "3.2");
			assertExceptionForGetValue(CommonCoreI18n.ILLEGAL_CHARACTERS_FOR_DECIMAL_NUMBER_ARG1.toDisplay("."), "-1.234,56");
			assertExceptionForGetValue(CommonCoreI18n.ILLEGAL_CHARACTERS_FOR_DECIMAL_NUMBER_ARG1.toDisplay("fo"), "foo");
			assertExceptionForGetValue(CommonCoreI18n.ILLEGAL_CHARACTERS_FOR_DECIMAL_NUMBER_ARG1.toDisplay(";"), "3;2");
			assertExceptionForGetValue(CommonCoreI18n.ILLEGAL_CHARACTERS_FOR_DECIMAL_NUMBER_ARG1.toDisplay(" "), "3 2");
		}
	}

	@Test
	public void testGetValueWithScale() {

		((DomBigDecimalInput) input).setScale(2);

		// scale increase
		assertResultForGetValue("10.00", "10");
		assertResultForGetValue("12.00", "12");
		assertResultForGetValue("12.30", "12.3");

		// scale retention
		assertResultForGetValue("10.00", "10.00");
		assertResultForGetValue("12.00", "12.00");
		assertResultForGetValue("12.34", "12.34");

		// scale reduction
		assertResultForGetValue("12.00", "12.000");
		assertExceptionForGetValue(DomI18n.NO_MORE_THAN_ARG1_DECIMAL_PLACES_ALLOWED.toDisplay(2), "12.123");
		assertExceptionForGetValue(DomI18n.NO_MORE_THAN_ARG1_DECIMAL_PLACES_ALLOWED.toDisplay(2), "12.1230");
		assertResultForGetValue("12.12", "12.1200");
	}

	@Test
	public void testSetValueWithoutScale() {

		setValue("12").assertInputValue("12");
		setValue("12.6").assertInputValue("12.6");
		setValue("12.60").assertInputValue("12.60");
	}

	@Test
	public void testSetValueWithScale() {

		((DomBigDecimalInput) input).setScale(2);

		// scale increase
		setValue("12").assertInputValue("12.00");
		setValue("12.0").assertInputValue("12.00");
		setValue("12.6").assertInputValue("12.60");

		// scale retention
		setValue("12.00").assertInputValue("12.00");
		setValue("12.60").assertInputValue("12.60");
		setValue("12.67").assertInputValue("12.67");

		// scale reduction
		setValue("12.600").assertInputValue("12.60");
		setValue("12.123").assertInputValue("12.123");
		setValue("12.1230").assertInputValue("12.123");
	}

	@Test
	public void testSetValueWithLocale() {

		try (var scope = new LocaleScope(SPECIAL_LOCALE)) {
			setValue("1234567.89").assertInputValue("1234567,89");
		}
	}

	private DomBigDecimalInputTest setValue(String value) {

		input.setValue(new BigDecimal(value));
		return this;
	}

	private DomBigDecimalInputTest assertInputValue(String expectedValue) {

		findNode(IDomTextualInput.class).assertInputValue(expectedValue);
		return this;
	}

	private void assertResultForGetValue(String expectedValue, String inputValue) {

		super.assertResultForGetValue(new BigDecimal(expectedValue), inputValue);
	}
}

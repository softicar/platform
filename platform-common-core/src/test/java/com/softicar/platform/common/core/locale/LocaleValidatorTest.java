package com.softicar.platform.common.core.locale;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import org.junit.Assert;
import org.junit.Test;

public class LocaleValidatorTest extends Assert {

	@Test(expected = SofticarUserException.class)
	public void testEmptyDecimalSeparator() {

		Locale locale = new Locale().setDecimalSeparator("");

		new LocaleValidator(locale).validate();
	}

	@Test(expected = SofticarUserException.class)
	public void testEqualDecimalSeparatorAndDigitGroupSeparator() {

		Locale locale = new Locale()//
			.setDecimalSeparator(".")
			.setDigitGroupSeparator(".");

		new LocaleValidator(locale).validate();
	}

	@Test(expected = SofticarUserException.class)
	public void testDecimalSeparatorWithDigits() {

		Locale locale = new Locale()//
			.setDecimalSeparator("123");

		new LocaleValidator(locale).validate();
	}

	@Test(expected = SofticarUserException.class)
	public void testDigitGroupSeparatorWithDigits() {

		Locale locale = new Locale()//
			.setDigitGroupSeparator("123");

		new LocaleValidator(locale).validate();
	}
}

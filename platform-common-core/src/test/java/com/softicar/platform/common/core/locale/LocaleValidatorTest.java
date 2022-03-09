package com.softicar.platform.common.core.locale;

import com.softicar.platform.common.core.CommonCoreI18n;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class LocaleValidatorTest extends Assert {

	@Test
	public void testEmptyDecimalSeparator() {

		Locale locale = new Locale().setDecimalSeparator("");

		assertValidationException(CommonCoreI18n.THE_DECIMAL_SEPARATOR_MAY_NOT_BE_EMPTY, locale);
	}

	@Test
	public void testEqualDecimalSeparatorAndDigitGroupSeparator() {

		Locale locale = new Locale()//
			.setDecimalSeparator(".")
			.setDigitGroupSeparator(".");

		assertValidationException(CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_BE_DIFFERENT_FROM_THE_DIGIT_GROUP_SEPARATOR, locale);
	}

	@Test
	public void testSeparatorsWithDigits() {

		assertValidationException(//
			CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_DIGITS,
			new Locale().setDecimalSeparator("123"));
		assertValidationException(//
			CommonCoreI18n.THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_DIGITS,
			new Locale().setDigitGroupSeparator("123"));
	}

	@Test
	public void testSeparatorsWithIllegalCharacters() {

		for (var character: List.of("+", "-", "e", "E")) {
			assertValidationException(//
				CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_THESE_CHARACTERS_ARG1.toDisplay("+-eE"),
				new Locale().setDecimalSeparator(character));
			assertValidationException(//
				CommonCoreI18n.THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_THESE_CHARACTERS_ARG1.toDisplay("+-eE"),
				new Locale().setDigitGroupSeparator(character));
		}
	}

	private static void assertValidationException(IDisplayString expectedMessage, Locale locale) {

		assertException(expectedMessage, () -> new LocaleValidator(locale).validate());
	}

	private static void assertException(IDisplayString expectedMessage, INullaryVoidFunction thrower) {

		try {
			thrower.apply();
			fail("Expected exception.");
		} catch (SofticarUserException exception) {
			assertEquals(expectedMessage.toString(), exception.getMessage());
		}
	}
}

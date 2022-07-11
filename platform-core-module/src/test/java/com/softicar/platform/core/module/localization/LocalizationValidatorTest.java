package com.softicar.platform.core.module.localization;

import com.softicar.platform.common.core.CommonCoreI18n;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.validation.result.EmfValidationResult;
import java.util.List;
import org.junit.Test;

public class LocalizationValidatorTest extends AbstractDbTest {

	@Test
	public void testEmptyDecimalSeparator() {

		var localization = new AGLocalization().setDecimalSeparator("");

		assertValidationException(CommonCoreI18n.THE_DECIMAL_SEPARATOR_MAY_NOT_BE_EMPTY, localization);
	}

	@Test
	public void testEqualDecimalSeparatorAndDigitGroupSeparator() {

		var localization = new AGLocalization()//
			.setDecimalSeparator(".")
			.setDigitGroupSeparator(".");

		assertValidationException(CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_BE_DIFFERENT_FROM_THE_DIGIT_GROUP_SEPARATOR, localization);
	}

	@Test
	public void testSeparatorsWithDigits() {

		assertValidationException(//
			CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_DIGITS,
			new AGLocalization().setDecimalSeparator("123"));
		assertValidationException(//
			CommonCoreI18n.THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_DIGITS,
			new AGLocalization().setDecimalSeparator(".").setDigitGroupSeparator("123"));
	}

	@Test
	public void testSeparatorsWithIllegalCharacters() {

		for (var character: List.of("+", "-", "e", "E")) {
			assertValidationException(//
				CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_ANY_OF_THE_FOLLOWING_CHARACTERS_ARG1.toDisplay("+-eE"),
				new AGLocalization().setDecimalSeparator(character));
			assertValidationException(//
				CommonCoreI18n.THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_ANY_OF_THE_FOLLOWING_CHARACTERS_ARG1.toDisplay("+-eE"),
				new AGLocalization().setDecimalSeparator(".").setDigitGroupSeparator(character));
		}
	}

	private static void assertValidationException(IDisplayString expectedMessage, AGLocalization localization) {

		assertExceptionMessage(expectedMessage, () -> new LocalizationValidator().validate(localization, new EmfValidationResult()));
	}
}

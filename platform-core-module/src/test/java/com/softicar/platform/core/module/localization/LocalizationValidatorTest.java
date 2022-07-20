package com.softicar.platform.core.module.localization;

import com.softicar.platform.common.core.CommonCoreI18n;
import com.softicar.platform.core.module.language.AGCoreLanguage;
import com.softicar.platform.core.module.language.AGCoreLanguageEnum;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import java.util.List;
import org.junit.Test;

public class LocalizationValidatorTest extends AbstractDbTest {

	public LocalizationValidatorTest() {

		// TODO Remove this workaround as soon as PLAT-1050 is done.
		AGCoreLanguage language = AGCoreLanguageEnum.ENGLISH.getRecord();
		language.reload();
		language.save();
	}

	@Test
	public void test() {

		createLocalization().save();
		// expect no exception
	}

	@Test
	public void testEmptyDecimalSeparator() {

		assertExceptionMessageContains(//
			CommonCoreI18n.THE_DECIMAL_SEPARATOR_MAY_NOT_BE_EMPTY,
			() -> createLocalization().setDecimalSeparator("").save());
	}

	@Test
	public void testEqualDecimalSeparatorAndDigitGroupSeparator() {

		assertExceptionMessageContains(//
			CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_BE_DIFFERENT_FROM_THE_DIGIT_GROUP_SEPARATOR,
			() -> createLocalization().setDigitGroupSeparator(".").save());
	}

	@Test
	public void testSeparatorsWithDigitsInSeparator() {

		assertExceptionMessageContains(//
			CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_DIGITS,
			() -> createLocalization().setDecimalSeparator("123").save());
		assertExceptionMessageContains(//
			CommonCoreI18n.THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_DIGITS,
			() -> createLocalization().setDigitGroupSeparator("123").save());
	}

	@Test
	public void testSeparatorsWithIllegalCharacters() {

		for (var character: List.of("+", "-", "e", "E")) {
			assertExceptionMessageContains(//
				CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_ANY_OF_THE_FOLLOWING_CHARACTERS_ARG1.toDisplay("+-eE"),
				() -> createLocalization().setDecimalSeparator(character).save());
			assertExceptionMessageContains(//
				CommonCoreI18n.THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_ANY_OF_THE_FOLLOWING_CHARACTERS_ARG1.toDisplay("+-eE"),
				() -> createLocalization().setDigitGroupSeparator(character).save());
		}
	}

	@Test
	public void testInvalidDateFormat() {

		assertExceptionMessageContains(//
			CommonCoreI18n.INVALID_DATE_FORMAT,
			() -> createLocalization().setDateFormat("x").save());
		assertExceptionMessageContains(//
			CommonCoreI18n.INVALID_DATE_FORMAT,
			() -> createLocalization().setDateFormat("").save());
		assertExceptionMessageContains(//
			CommonCoreI18n.INVALID_DATE_FORMAT,
			() -> createLocalization().setDateFormat(" ").save());
	}

	private AGLocalization createLocalization() {

		return new AGLocalization()
			.setName("default")
			.setLanguage(AGCoreLanguageEnum.ENGLISH.getRecord())
			.setDecimalSeparator(".")
			.setDigitGroupSeparator(",")
			.setDateFormat("yyyy-MM-dd");
	}
}

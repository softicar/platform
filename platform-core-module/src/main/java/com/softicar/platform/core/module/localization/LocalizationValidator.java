package com.softicar.platform.core.module.localization;

import com.softicar.platform.common.core.CommonCoreI18n;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.emf.validation.AbstractEmfValidator;

public class LocalizationValidator extends AbstractEmfValidator<AGLocalization> {

	private final static String ILLEGAL_SEPARATOR_CHARACTERS = "+-eE";

	@Override
	protected void validate() {

		if (tableRow.getDecimalSeparator().isEmpty()) {
			throw new SofticarUserException(CommonCoreI18n.THE_DECIMAL_SEPARATOR_MAY_NOT_BE_EMPTY);
		}

		if (tableRow.getDecimalSeparator().equals(tableRow.getDigitGroupSeparator())) {
			throw new SofticarUserException(CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_BE_DIFFERENT_FROM_THE_DIGIT_GROUP_SEPARATOR);
		}

		if (containsNumbers(tableRow.getDecimalSeparator())) {
			throw new SofticarUserException(CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_DIGITS);
		}

		if (containsNumbers(tableRow.getDigitGroupSeparator())) {
			throw new SofticarUserException(CommonCoreI18n.THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_DIGITS);
		}

		if (containsIllegalCharacters(tableRow.getDecimalSeparator())) {
			throw new SofticarUserException(
				CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_ANY_OF_THE_FOLLOWING_CHARACTERS_ARG1.toDisplay(ILLEGAL_SEPARATOR_CHARACTERS));
		}

		if (containsIllegalCharacters(tableRow.getDigitGroupSeparator())) {
			throw new SofticarUserException(
				CommonCoreI18n.THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_ANY_OF_THE_FOLLOWING_CHARACTERS_ARG1.toDisplay(ILLEGAL_SEPARATOR_CHARACTERS));
		}
	}

	private boolean containsNumbers(String text) {

		for (int i = 0; i < text.length(); i++) {
			if (Character.isDigit(text.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	private boolean containsIllegalCharacters(String text) {

		for (int i = 0; i < text.length(); i++) {
			if (ILLEGAL_SEPARATOR_CHARACTERS.contains("" + text.charAt(i))) {
				return true;
			}
		}
		return false;
	}
}

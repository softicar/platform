package com.softicar.platform.core.module.localization;

import com.softicar.platform.common.core.CommonCoreI18n;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.emf.validation.AbstractEmfValidator;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalizationValidator extends AbstractEmfValidator<AGLocalization> {

	private final static String ILLEGAL_SEPARATOR_CHARACTERS = "+-eE";

	@Override
	protected void validate() {

		if (tableRow.getDecimalSeparator().isEmpty()) {
			addError(AGLocalization.DECIMAL_SEPARATOR, CommonCoreI18n.THE_DECIMAL_SEPARATOR_MAY_NOT_BE_EMPTY);
		}

		if (containsNumbers(tableRow.getDecimalSeparator())) {
			addError(AGLocalization.DECIMAL_SEPARATOR, CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_DIGITS);
		}

		if (containsIllegalCharacters(tableRow.getDecimalSeparator())) {
			addError(
				AGLocalization.DECIMAL_SEPARATOR,
				CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_ANY_OF_THE_FOLLOWING_CHARACTERS_ARG1.toDisplay(ILLEGAL_SEPARATOR_CHARACTERS));
		}

		if (tableRow.getDecimalSeparator().equals(tableRow.getDigitGroupSeparator())) {
			addError(AGLocalization.DECIMAL_SEPARATOR, CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_BE_DIFFERENT_FROM_THE_DIGIT_GROUP_SEPARATOR);
			addError(AGLocalization.DIGIT_GROUP_SEPARATOR, CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_BE_DIFFERENT_FROM_THE_DIGIT_GROUP_SEPARATOR);
		}

		if (containsNumbers(tableRow.getDigitGroupSeparator())) {
			addError(AGLocalization.DIGIT_GROUP_SEPARATOR, CommonCoreI18n.THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_DIGITS);
		}

		if (containsIllegalCharacters(tableRow.getDigitGroupSeparator())) {
			addError(
				AGLocalization.DIGIT_GROUP_SEPARATOR,
				CommonCoreI18n.THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_ANY_OF_THE_FOLLOWING_CHARACTERS_ARG1.toDisplay(ILLEGAL_SEPARATOR_CHARACTERS));
		}

		if (isInvalidDateFormat(tableRow.getDateFormat())) {
			addError(AGLocalization.DATE_FORMAT, CommonCoreI18n.INVALID_DATE_FORMAT);
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

	private boolean isInvalidDateFormat(String dateFormat) {

		if (dateFormat.isBlank()) {
			return true;
		}

		try {
			new SimpleDateFormat(dateFormat).format(new Date());
			return false;
		} catch (Exception exception) {
			DevNull.swallow(exception);
			return true;
		}
	}
}

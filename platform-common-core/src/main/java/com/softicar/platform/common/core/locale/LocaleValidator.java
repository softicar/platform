package com.softicar.platform.common.core.locale;

import com.softicar.platform.common.core.CommonCoreI18n;
import com.softicar.platform.common.core.exceptions.SofticarUserException;

public class LocaleValidator {

	private final ILocale locale;

	public LocaleValidator(ILocale locale) {

		this.locale = locale;
	}

	public void validate() {

		if (locale.getDecimalSeparator().isEmpty()) {
			throw new SofticarUserException(CommonCoreI18n.THE_DECIMAL_SEPARATOR_MAY_NOT_BE_EMPTY);
		}

		if (locale.getDecimalSeparator().equals(locale.getDigitGroupSeparator())) {
			throw new SofticarUserException(CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_BE_DIFFERENT_FROM_THE_DIGIT_GROUP_SEPARATOR);
		}

		if (containsNumbers(locale.getDecimalSeparator())) {
			throw new SofticarUserException(CommonCoreI18n.THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_DIGITS);
		}

		if (containsNumbers(locale.getDigitGroupSeparator())) {
			throw new SofticarUserException(CommonCoreI18n.THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_DIGITS);
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
}

package com.softicar.platform.common.core;

import com.softicar.platform.common.core.i18n.I18n0;

public interface CommonCoreI18n {

	I18n0 THE_DECIMAL_SEPARATOR_MAY_NOT_BE_EMPTY = new I18n0("The decimal separator may not be empty.")//
		.de("Das Dezimaltrennzeichen darf nicht leer sein.");
	I18n0 THE_DECIMAL_SEPARATOR_MUST_BE_DIFFERENT_FROM_THE_DIGIT_GROUP_SEPARATOR =
			new I18n0("The decimal separator must be different from the digit group separator.")//
				.de("Das Dezimaltrennzeichen muss sich vom Zifferngruppentrennzeichen unterscheiden.");
	I18n0 THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_DIGITS = new I18n0("The decimal separator must not contain digits.")//
		.de("Das Dezimaltrennzeichen darf keine Ziffern beinhalten.");
	I18n0 THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_DIGITS = new I18n0("The digit group separator must not contain digits.")//
		.de("Das Zifferngruppentrennzeichen darf keine Ziffern beinhalten.");
}

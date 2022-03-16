package com.softicar.platform.common.core;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;

public interface CommonCoreI18n {

	I18n0 DECIMAL_SEPARATOR = new I18n0("Decimal Separator")//
		.de("Dezimaltrennzeichen");
	I18n0 DIGIT_GROUP_SEPARATOR = new I18n0("Digit Group Separator")//
		.de("Zifferngruppentrennzeichen");
	I18n0 DIGIT_GROUP_TOO_LONG = new I18n0("Digit group too long.")//
		.de("Zifferngruppe zu lang.");
	I18n0 DIGIT_GROUP_TOO_SHORT = new I18n0("Digit group too short.")//
		.de("Zifferngruppe zu kurz.");
	I18n0 THE_DECIMAL_PART_MUST_NOT_CONTAIN_DIGIT_GROUP_SEPARATORS = new I18n0("The decimal part must not contain digit group separators.")//
		.de("Der Dezimalteil darf keine Zifferngruppentrennzeichen enthalten.");
	I18n0 THE_DECIMAL_SEPARATOR_MAY_NOT_BE_EMPTY = new I18n0("The decimal separator may not be empty.")//
		.de("Das Dezimaltrennzeichen darf nicht leer sein.");
	I18n0 THE_DECIMAL_SEPARATOR_MUST_BE_DIFFERENT_FROM_THE_DIGIT_GROUP_SEPARATOR =
			new I18n0("The decimal separator must be different from the digit group separator.")//
				.de("Das Dezimaltrennzeichen muss sich vom Zifferngruppentrennzeichen unterscheiden.");
	I18n1 THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_ANY_OF_THE_FOLLOWING_CHARACTERS_ARG1 =
			new I18n1("The decimal separator must not contain any of the following characters: %s")//
				.de("Das Dezimaltrennzeichen darf keines der folgenden Zeichen enthalten: %s");
	I18n0 THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_DIGITS = new I18n0("The decimal separator must not contain digits.")//
		.de("Das Dezimaltrennzeichen darf keine Ziffern beinhalten.");
	I18n1 THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_ANY_OF_THE_FOLLOWING_CHARACTERS_ARG1 =
			new I18n1("The digit group separator must not contain any of the following characters: %s")//
				.de("Das Zifferngruppentrennzeichen darf keines der folgenden Zeichen enthalten: %s");
	I18n0 THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_DIGITS = new I18n0("The digit group separator must not contain digits.")//
		.de("Das Zifferngruppentrennzeichen darf keine Ziffern beinhalten.");
}

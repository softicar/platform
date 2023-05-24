package com.softicar.platform.common.core;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;

public interface CommonCoreI18n {

	I18n0 DECIMAL_SEPARATOR = new I18n0("Decimal Separator")//
		.de("Dezimaltrennzeichen")
		.bs("Decimalni separator")
		.sr("Decimalni separator")
		.hr("Decimalni razdjelnik");
	I18n0 DIGIT_GROUP_SEPARATOR = new I18n0("Digit Group Separator")//
		.de("Zifferngruppentrennzeichen")
		.bs("Separator grupisanja cifara")
		.sr("Separator grupisanja cifara")
		.hr("Razdjelnik grupiranja znamenki");
	I18n0 DIGIT_GROUP_TOO_LONG = new I18n0("Digit group too long.")//
		.de("Zifferngruppe zu lang.")
		.bs("Grupa cifara je predugačka.")
		.sr("Grupa cifara je predugačka.")
		.hr("Grupa znamenki je predugačka.");
	I18n0 DIGIT_GROUP_TOO_SHORT = new I18n0("Digit group too short.")//
		.de("Zifferngruppe zu kurz.")
		.bs("Grupa cifara je prekratka.")
		.sr("Grupa cifara je prekratka.")
		.hr("Grupa znamenki je prekratka.");
	I18n1 ILLEGAL_CHARACTERS_FOR_DECIMAL_NUMBER_ARG1 = new I18n1("Illegal characters for decimal number: %s")//
		.de("Unzulässige Zeichen für Dezimalzahl: %s")
		.bs("Nedozvoljeni znakovi za decimalni broj: %s")
		.sr("Nedozvoljeni znakovi za decimalni broj: %s")
		.hr("Nedozvoljeni karakteri za decimalni broj: %s");
	I18n0 INVALID_DATE_FORMAT = new I18n0("Invalid date format.")//
		.de("Ungültiges Datumsformat.")
		.bs("Nevažeći format datuma.")
		.sr("Nevažeći format datuma.")
		.hr("Nevažeći format datuma.");
	I18n0 RETURN_TO_LOGIN = new I18n0("Return to Login")//
		.de("Zurück zum Login")
		.bs("Povratak na prijavu")
		.sr("Povratak na prijavu")
		.hr("Povratak na prijavu");
	I18n0 THE_DECIMAL_PART_MUST_NOT_CONTAIN_DIGIT_GROUP_SEPARATORS = new I18n0("The decimal part must not contain digit group separators.")//
		.de("Der Dezimalteil darf keine Zifferngruppentrennzeichen enthalten.")
		.bs("Decimalni dio ne smije sadržavati separator grupisanja cifara.")
		.sr("Decimalni dio ne sme sadržavati separator grupisanja cifara.")
		.hr("Decimalni dio ne smije sadržavati razdjelnik grupisanja znamenki.");
	I18n0 THE_DECIMAL_SEPARATOR_MAY_NOT_BE_EMPTY = new I18n0("The decimal separator may not be empty.")//
		.de("Das Dezimaltrennzeichen darf nicht leer sein.")
		.bs("Decimalni separator ne smije biti prazan.")
		.sr("Decimalni separator ne sme biti prazan.")
		.hr("Decimalni razdjelnik ne smije biti prazan.");
	I18n0 THE_DECIMAL_SEPARATOR_MUST_BE_DIFFERENT_FROM_THE_DIGIT_GROUP_SEPARATOR =
			new I18n0("The decimal separator must be different from the digit group separator.")//
				.de("Das Dezimaltrennzeichen muss sich vom Zifferngruppentrennzeichen unterscheiden.")
				.bs("Decimalni separator mora biti različit od separatora grupisanja cifara.")
				.sr("Decimalni separator mora biti različit od separatora grupisanja cifara.")
				.hr("Decimalni razdjelnik mora biti različit od razdjelnika grupisanja znamenki.");
	I18n1 THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_ANY_OF_THE_FOLLOWING_CHARACTERS_ARG1 =
			new I18n1("The decimal separator must not contain any of the following characters: %s")//
				.de("Das Dezimaltrennzeichen darf keines der folgenden Zeichen enthalten: %s")
				.bs("Decimalni separator ne smije sadržavati nijedan of sljedećih znakova: %s")
				.sr("Decimalni separator ne sme sadržavati nijedan of sledećih znakova: %s")
				.hr("Decimalni razdjelnik ne smije sadržavati nijedan of sljedećih karaktera: %s");
	I18n0 THE_DECIMAL_SEPARATOR_MUST_NOT_CONTAIN_DIGITS = new I18n0("The decimal separator must not contain digits.")//
		.de("Das Dezimaltrennzeichen darf keine Ziffern beinhalten.")
		.bs("Decimalni separator ne smije sadržavati cifre.")
		.sr("Decimalni separator ne sme sadržavati cifre.")
		.hr("Decimalni razdjelnik ne smije sadržavati znamenke.");
	I18n1 THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_ANY_OF_THE_FOLLOWING_CHARACTERS_ARG1 =
			new I18n1("The digit group separator must not contain any of the following characters: %s")//
				.de("Das Zifferngruppentrennzeichen darf keines der folgenden Zeichen enthalten: %s")
				.bs("Separator grupisanja cifara ne smije sadržavati nijedan of sljedećih znakova: %s")
				.sr("Separator grupisanja cifara ne sme sadržavati nijedan of sledećih znakova: %s")
				.hr("Razdjelnik grupiranja znamenki ne smije sadržavati nijedan of sljedećih karaktera: %s");
	I18n0 THE_DIGIT_GROUP_SEPARATOR_MUST_NOT_CONTAIN_DIGITS = new I18n0("The digit group separator must not contain digits.")//
		.de("Das Zifferngruppentrennzeichen darf keine Ziffern beinhalten.")
		.bs("Separator grupisanja cifara ne smije sadržavati cifre.")
		.sr("Separator grupisanja cifara ne sme sadržavati cifre.")
		.hr("Razdjelnik grupiranja znamenki ne smije sadržavati znamenke.");
}

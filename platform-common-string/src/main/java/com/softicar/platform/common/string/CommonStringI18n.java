package com.softicar.platform.common.string;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;

public interface CommonStringI18n {

	I18n0 AFTER_A_DOUBLE_QUOTED_VALUE_A_COMMA_A_NEWLINE_OR_END_OF_FILE_ARE_EXPECTED =
			new I18n0("After a double quoted value, a comma, a newline or end-of-file are expected.")//
				.de("Nach einem Wert in doppelten Anführungszeichen wird ein Komma, eine neue Zeile oder das Ende der Datei erwartet.");
	I18n0 EXPECTED_A_DOUBLE_QUOTE_AT_THE_END_OF_THE_VALUE = new I18n0("Expected a double quote at the end of the value.")//
		.de("Am Ende des Wertes wurde ein doppeltes Anführungszeichen erwartet.");
	I18n1 INVALID_CSV_FORMAT_AFTER_CHARACTER_ARG1 = new I18n1("Invalid CSV format after character %s")//
		.de("Ungültiges CSV-Format nach Zeichen %s");
}

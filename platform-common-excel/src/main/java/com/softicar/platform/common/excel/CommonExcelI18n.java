package com.softicar.platform.common.excel;

import com.softicar.platform.common.core.i18n.I18n1;

public interface CommonExcelI18n {

	I18n1 FAILED_TO_PARSE_EXCEL_FILE_ARG1 = new I18n1("Failed to parse Excel file: %s")//
		.de("Einlesen der Excel-Datei fehlgeschlagen: %s")
		.bs("Nije uspjelo čitanje Excel fajla: %s")
		.sr("Nije uspelo čitanje Excel fajla: %s")
		.hr("Nije uspjelo čitanje Excel fajla: %s");
}

package com.softicar.platform.common.date;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.common.core.i18n.I18n2;
import com.softicar.platform.common.core.i18n.I18n3;
import com.softicar.platform.common.core.i18n.I18n4;

public interface CommonDateI18n extends CommonMonthI18n, CommonWeekdayI18n {

	I18n0 _2_DAYS_AGO = new I18n0("2 days ago")//
		.de("vor 2 Tagen")
		.bs("Prije 2 dana")
		.hr("Prije 2 dana")
		.sr("Prije 2 dana");
	I18n1 ARG1_SECONDS = new I18n1("%s seconds")//
		.de("%s Sekunden")
		.bs("%s sekunde")
		.hr("%s sekunde")
		.sr("%s sekunde");
	I18n4 ARG1D_ARG2H_ARG3MIN_ARG4S = new I18n4("%sd %sh %smin %ss")//
		.de("%sT %sh %smin %ss")
		.bs("%sd %sh %smin %ss")
		.hr("%sd %sh %smin %ss")
		.sr("%sd %sh %smin %ss");
	I18n3 ARG1H_ARG2MIN_ARG3S = new I18n3("%sh %smin %ss")//
		.de("%sh %smin %ss")
		.bs("%sh %smin %ss")
		.hr("%sh %smin %ss")
		.sr("%sh %smin %ss");
	I18n2 ARG1MIN_ARG2S = new I18n2("%smin %ss")//
		.de("%smin %ss")
		.bs("%smin %ss")
		.hr("%smin %ss")
		.sr("%smin %ss");
	I18n1 ILLEGAL_DATE_SPECIFICATION_ARG1 = new I18n1("Illegal date specification: %s")//
		.de("Ungültige Datumsangabe: %s")
		.bs("Ilegalna specifikacija datuma: %s")
		.hr("Ilegalna specifikacija datuma: %s")
		.sr("Ilegalna specifikacija datuma: %s");
	I18n1 ILLEGAL_TIME_SPECIFICATION_ARG1 = new I18n1("Illegal time specification: %s")//
		.de("Ungültige Zeitangabe: %s")
		.bs("Ilegalna specifikacija vremena: %s")
		.hr("Ilegalna specifikacija vremena: %s")
		.sr("Ilegalna specifikacija vremena: %s");
	I18n0 MISSING_DATE_SPECIFICATION = new I18n0("Missing date specification.")//
		.de("Fehlende Datumsangabe.")
		.bs("Nedostaje specifikacija datuma")
		.hr("Nedostaje specifikacija datuma")
		.sr("Nedostaje specifikacija datuma");
	I18n0 MISSING_TIME_SPECIFICATION = new I18n0("Missing time specification.")//
		.de("Fehlende Zeitangabe.")
		.bs("Nedostaje specifikacija vremena")
		.hr("Nedostaje specifikacija vremena")
		.sr("Nedostaje specifikacija vremena");
	I18n0 TODAY = new I18n0("Today")//
		.de("Heute")
		.bs("Danas")
		.hr("Danas")
		.sr("Danas");
	I18n0 TOMORROW = new I18n0("Tomorrow")//
		.de("Morgen")
		.bs("Sutra")
		.hr("Sutra")
		.sr("Sutra");
	I18n0 YESTERDAY = new I18n0("Yesterday")//
		.de("Gestern")
		.bs("Jučer")
		.hr("Jučer")
		.sr("Jučer");
}

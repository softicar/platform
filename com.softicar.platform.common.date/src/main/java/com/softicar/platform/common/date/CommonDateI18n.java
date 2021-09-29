package com.softicar.platform.common.date;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.common.core.i18n.I18n2;
import com.softicar.platform.common.core.i18n.I18n3;
import com.softicar.platform.common.core.i18n.I18n4;

public interface CommonDateI18n extends CommonMonthI18n, CommonWeekdayI18n {

	I18n0 _2_DAYS_AGO = new I18n0("2 days ago")//
		.de("vor 2 Tagen");
	I18n1 ARG1_SECONDS = new I18n1("%s seconds")//
		.de("%s Sekunden");
	I18n4 ARG1D_ARG2H_ARG3MIN_ARG4S = new I18n4("%sd %sh %smin %ss")//
		.de("%sT %sh %smin %ss");
	I18n3 ARG1H_ARG2MIN_ARG3S = new I18n3("%sh %smin %ss")//
		.de("%sh %smin %ss");
	I18n2 ARG1MIN_ARG2S = new I18n2("%smin %ss")//
		.de("%smin %ss");
	I18n0 TODAY = new I18n0("Today")//
		.de("Heute");
	I18n0 TOMORROW = new I18n0("Tomorrow")//
		.de("Morgen");
	I18n0 TUESDAY = new I18n0("Tuesday")//
		.de("Dienstag");
	I18n0 YESTERDAY = new I18n0("Yesterday")//
		.de("Gestern");
}

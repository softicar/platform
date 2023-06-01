package com.softicar.platform.common.container;

import com.softicar.platform.common.core.i18n.I18n0;

public interface CommonContainerI18n {

	I18n0 AND = new I18n0("and")//
		.de("und")
		.bs("i")
		.sr("i")
		.hr("i");
	I18n0 ASCENDING = new I18n0("Ascending")//
		.de("Aufsteigend")
		.bs("Uzlazno")
		.sr("Uzlazno")
		.hr("Uzlazno");
	I18n0 DESCENDING = new I18n0("Descending")//
		.de("Absteigend")
		.bs("Silazno")
		.sr("Silazno")
		.hr("Silazno");
	I18n0 EMPTY = new I18n0("empty")//
		.de("leer")
		.bs("prazno")
		.sr("prazno")
		.hr("prazno");
	I18n0 NOT_EMPTY = new I18n0("not empty")//
		.de("nicht leer")
		.bs("nije prazno")
		.sr("nije prazno")
		.hr("nije prazno");
	I18n0 OR = new I18n0("or")//
		.de("oder")
		.bs("ili")
		.sr("ili")
		.hr("ili");
}

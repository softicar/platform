package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.i18n.I18n0;

interface I18nKeyTestContainerWithIllegalFieldOrder {

	// A must come before B
	I18n0 B = new I18n0("b");
	I18n0 A = new I18n0("a");

	// X_X must come before XXX (underscore is treated as space)
	I18n0 XXX = new I18n0("xxx");
	I18n0 X_Y = new I18n0("x y");
}

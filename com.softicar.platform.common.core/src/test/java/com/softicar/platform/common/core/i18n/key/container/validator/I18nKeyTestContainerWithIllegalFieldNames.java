package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.i18n.I18n0;

interface I18nKeyTestContainerWithIllegalFieldNames {

	I18n0 _LEADING_UNSERSCORE = new I18n0("leading underscore");
	I18n0 DOUBLE__UNSERSCORE = new I18n0("double underscore");
	I18n0 TRAILING_UNSERSCORE_ = new I18n0("trailing underscore");
	I18n0 lower_case = new I18n0("lower case");
}

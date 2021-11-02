package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;

interface I18nKeyTestContainerWithUnexpectedFieldNames {

	I18n0 BAR = new I18n0("foo bar");
	I18n1 FOO = new I18n1("foo %s");
}

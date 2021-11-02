package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;

interface I18nKeyTestContainerWithIllegalFieldTypes {

	I18n0 THIS_IS_ARG1 = new I18n0("This is %s.");
	I18n1 THIS_IS_WITHOUT_ARGUMENT = new I18n1("This is without argument.");
}

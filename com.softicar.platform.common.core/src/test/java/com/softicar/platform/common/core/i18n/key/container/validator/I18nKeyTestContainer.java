package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.common.core.i18n.I18n2;

interface I18nKeyTestContainer extends I18nKeyTestContainerBase {

	I18n0 _1 = new I18n0("1");

	I18n0 ARE_YOU_SURE_QUESTION = new I18n0("Are you sure?");
	I18n1 ARG1_IS_THE_BEST = new I18n1("%s is the best.");
	I18n2 THIS_IS_ARG1_OF_ARG2 = new I18n2("This is %s of %s.");
}

package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.i18n.I18n0;

interface I18nKeyTestContainerWithIllegalKeyStrings {

	I18n0 EMPTY = new I18n0("");
	I18n0 FIRST_SENTENCE_SECOND_SENTENCE = new I18n0("First sentence. Second sentence.");
	I18n0 LEADING_WHITESPACE = new I18n0(" leading whitespace");
	I18n0 TRAILING_WHITESPACE = new I18n0("trailing whitespace ");
}

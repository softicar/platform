package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.i18n.I18n0;

@SuppressWarnings("unused")
class I18nKeyTestContainerWithIllegalFieldModifiers {

	public static I18n0 NON_FINAL = new I18n0("non final");
	public final I18n0 NON_STATIC = new I18n0("non static");

	static final I18n0 VISIBILITY_DEFAULT = new I18n0("visibility default");
	private static final I18n0 VISIBILITY_PRIVATE = new I18n0("visibility private");
	private static final I18n0 VISIBILITY_PROTECTED = new I18n0("visibility protected");
}

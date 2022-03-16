package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.i18n.I18n0;
import java.util.ArrayList;
import java.util.function.Supplier;

class I18nKeyTestContainerWithExtendsAndImplements extends ArrayList<Void> implements Supplier<Object> {

	public static final I18n0 FOO = new I18n0("foo");

	@Override
	public Object get() {

		throw new UnsupportedOperationException();
	}
}

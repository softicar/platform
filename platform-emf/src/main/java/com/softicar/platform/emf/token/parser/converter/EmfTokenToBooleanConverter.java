package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.DbBooleanField;
import com.softicar.platform.emf.EmfI18n;

class EmfTokenToBooleanConverter extends AbstractEmfTokenConverter<Boolean, DbBooleanField<?>> {

	@Override
	public EmfTokenConverterResult<Boolean> convertToken(DbBooleanField<?> targetField, String token) {

		switch (token) {
		case "1":
			return EmfTokenConverterResult.okay(true);
		case "0":
			return EmfTokenConverterResult.okay(false);
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public IDisplayString getTypeDescription() {

		return EmfI18n.BOOLEAN.concatSpace().concatInParentheses(IDisplayString.format("1=%s, 0=%s", EmfI18n.YES, EmfI18n.NO));
	}
}

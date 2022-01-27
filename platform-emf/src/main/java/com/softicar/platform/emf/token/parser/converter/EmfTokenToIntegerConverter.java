package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.DbIntegerField;
import com.softicar.platform.emf.EmfI18n;
import java.math.BigDecimal;

class EmfTokenToIntegerConverter extends AbstractEmfTokenConverter<Integer, DbIntegerField<?>> {

	@Override
	public EmfTokenConverterResult<Integer> convertToken(DbIntegerField<?> targetField, String token) {

		if (isInRange(token)) {
			int value = Integer.parseInt(token);
			return EmfTokenConverterResult.okay(value);
		} else {
			return EmfTokenConverterResult.failed(createValueOutOfRangeMessage(token, "-2147483647", "2147483647"));
		}
	}

	@Override
	public IDisplayString getTypeDescription() {

		return createTypeDescriptionWithExample(EmfI18n.INTEGER, "128");
	}

	private boolean isInRange(String token) {

		return new BigDecimal(token).abs().compareTo(new BigDecimal(Integer.MAX_VALUE)) <= 0;
	}
}

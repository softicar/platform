package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.DbIdField;
import com.softicar.platform.emf.EmfI18n;
import java.math.BigDecimal;

class EmfTokenToIdConverter extends AbstractEmfTokenConverter<Integer, DbIdField<?>> {

	@Override
	public EmfTokenConverterResult<Integer> convertToken(DbIdField<?> targetField, String token) {

		if (isInRange(token)) {
			int value = Integer.parseInt(token);
			return EmfTokenConverterResult.okay(value);
		} else {
			return EmfTokenConverterResult.failed(createValueOutOfRangeMessage(token, "1", "2147483647"));
		}
	}

	@Override
	public IDisplayString getTypeDescription() {

		return createTypeDescriptionWithExample(EmfI18n.ID, "128");
	}

	private boolean isInRange(String token) {

		var decimalToken = new BigDecimal(token);
		return decimalToken.signum() == 1 && decimalToken.compareTo(new BigDecimal(Integer.MAX_VALUE)) <= 0;
	}
}

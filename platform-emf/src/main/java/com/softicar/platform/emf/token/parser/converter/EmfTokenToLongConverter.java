package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.DbLongField;
import com.softicar.platform.emf.EmfI18n;
import java.math.BigDecimal;

class EmfTokenToLongConverter extends AbstractEmfTokenConverter<Long, DbLongField<?>> {

	@Override
	public EmfTokenConverterResult<Long> convertToken(DbLongField<?> targetField, String token) {

		if (isInRange(token)) {
			long value = Long.parseLong(token);
			return EmfTokenConverterResult.okay(value);
		} else {
			return EmfTokenConverterResult.failed(createValueOutOfRangeMessage(token, "-9223372036854775807", "9223372036854775807"));
		}
	}

	@Override
	public IDisplayString getTypeDescription() {

		return createTypeDescriptionWithExample(EmfI18n.LONG_INTEGER, "128");
	}

	private boolean isInRange(String token) {

		return new BigDecimal(token).abs().compareTo(new BigDecimal(Long.MAX_VALUE)) <= 0;
	}
}

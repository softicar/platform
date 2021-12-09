package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.DbBigDecimalField;
import com.softicar.platform.emf.EmfI18n;
import java.math.BigDecimal;

class EmfTokenToBigDecimalConverter extends AbstractEmfTokenConverter<BigDecimal, DbBigDecimalField<?>> {

	@Override
	public EmfTokenConverterResult<BigDecimal> convertToken(DbBigDecimalField<?> field, String token) {

		var value = new BigDecimal(token);
		if (field.getPrecision() < value.precision()) {
			IDisplayString message = EmfI18n.NUMBER_OF_DIGITS_EXCEEDED
				.concatSpace()
				.concatInParentheses(EmfI18n.MAX_POSSIBLE_ARG1_ENCOUNTERED_ARG2.toDisplay(field.getPrecision(), value.precision()));
			return EmfTokenConverterResult.failed(message);
		} else if (field.getScale() < value.scale()) {
			IDisplayString message = EmfI18n.NUMBER_OF_DECIMAL_PLACES_EXCEEDED
				.concatSpace()
				.concatInParentheses(EmfI18n.MAX_POSSIBLE_ARG1_ENCOUNTERED_ARG2.toDisplay(field.getScale(), value.scale()));
			return EmfTokenConverterResult.failed(message);
		} else {
			return EmfTokenConverterResult.okay(value);
		}
	}

	@Override
	public IDisplayString getTypeDescription() {

		return createTypeDescriptionWithExample(EmfI18n.DECIMAL_NUMBER, "123.456");
	}
}

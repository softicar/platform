package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.DbDoubleField;
import com.softicar.platform.emf.EmfI18n;

class EmfTokenToDoubleConverter extends AbstractEmfTokenConverter<Double, DbDoubleField<?>> {

	@Override
	public EmfTokenConverterResult<Double> convertToken(DbDoubleField<?> targetField, String token) {

		double value = Double.parseDouble(token);
		return EmfTokenConverterResult.okay(value);
	}

	@Override
	public IDisplayString getTypeDescription() {

		return createTypeDescriptionWithExample(EmfI18n.DOUBLE_PRECISION_FLOATING_POINT_NUMBER, "123.456");
	}
}

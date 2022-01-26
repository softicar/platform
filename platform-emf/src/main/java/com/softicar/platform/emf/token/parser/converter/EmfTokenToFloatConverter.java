package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.DbFloatField;
import com.softicar.platform.emf.EmfI18n;

class EmfTokenToFloatConverter extends AbstractEmfTokenConverter<Float, DbFloatField<?>> {

	@Override
	public EmfTokenConverterResult<Float> convertToken(DbFloatField<?> targetField, String token) {

		float value = Float.parseFloat(token);
		return EmfTokenConverterResult.okay(value);
	}

	@Override
	public IDisplayString getTypeDescription() {

		return createTypeDescriptionWithExample(EmfI18n.FLOATING_POINT_NUMBER, "123.456");
	}
}

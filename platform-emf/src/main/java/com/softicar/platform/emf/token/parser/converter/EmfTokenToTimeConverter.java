package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.runtime.field.DbTimeField;
import com.softicar.platform.emf.EmfI18n;

class EmfTokenToTimeConverter extends AbstractEmfTokenConverter<Time, DbTimeField<?>> {

	@Override
	public EmfTokenConverterResult<Time> convertToken(DbTimeField<?> targetField, String token) {

		return EmfTokenConverterResult.okay(Time.parseTime(token));
	}

	@Override
	public IDisplayString getTypeDescription() {

		return createTypeDescriptionWithExample(EmfI18n.TIME, "14:30:00");
	}
}

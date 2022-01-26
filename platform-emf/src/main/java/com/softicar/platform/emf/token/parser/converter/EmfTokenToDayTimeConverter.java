package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.runtime.field.DbDayTimeField;
import com.softicar.platform.emf.EmfI18n;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

class EmfTokenToDayTimeConverter extends AbstractEmfTokenConverter<DayTime, DbDayTimeField<?>> {

	private static final Pattern ISO_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");

	@Override
	public EmfTokenConverterResult<DayTime> convertToken(DbDayTimeField<?> targetField, String token) throws Exception {

		var format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setLenient(false);
		try {
			DayTime value = DayTime.fromDate(format.parse(token));
			return EmfTokenConverterResult.okay(value);
		} catch (Exception exception) {
			if (ISO_PATTERN.matcher(token).matches()) {
				return EmfTokenConverterResult.failed(EmfI18n.POINT_IN_TIME_ARG1_DOES_NOT_EXIST.toDisplay(token));
			} else {
				throw exception;
			}
		}
	}

	@Override
	public IDisplayString getTypeDescription() {

		return createTypeDescriptionWithExample(EmfI18n.DAYTIME, "2000-12-31 14:59:59");
	}
}

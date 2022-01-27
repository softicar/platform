package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.field.DbDayField;
import com.softicar.platform.emf.EmfI18n;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

class EmfTokenToDayConverter extends AbstractEmfTokenConverter<Day, DbDayField<?>> {

	private static final Pattern ISO_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

	@Override
	public EmfTokenConverterResult<Day> convertToken(DbDayField<?> targetField, String token) throws Exception {

		var format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		try {
			Day value = Day.fromDate(format.parse(token));
			return EmfTokenConverterResult.okay(value);
		} catch (Exception exception) {
			if (ISO_PATTERN.matcher(token).matches()) {
				return EmfTokenConverterResult.failed(EmfI18n.CALENDAR_DAY_ARG1_DOES_NOT_EXIST.toDisplay(token));
			} else {
				throw exception;
			}
		}
	}

	@Override
	public IDisplayString getTypeDescription() {

		return createTypeDescriptionWithExample(EmfI18n.DAY, "2000-12-31");
	}
}

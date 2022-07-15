package com.softicar.platform.db.structure.comparison.enumeration;

import com.softicar.platform.db.structure.enums.IDbEnumTableRowValueConverter;
import java.math.BigDecimal;

public class DbEnumTableRowValueToObjectConverter implements IDbEnumTableRowValueConverter<Object> {

	@Override
	public Object convertBigDecimal(BigDecimal value) {

		return value;
	}

	@Override
	public Object convertBoolean(Boolean value) {

		return value;
	}

	@Override
	public Object convertDouble(Double value) {

		return value;
	}

	@Override
	public Object convertFloat(Float value) {

		return value;
	}

	@Override
	public Object convertInteger(Integer value) {

		return value;
	}

	@Override
	public Object convertLong(Long value) {

		return value;
	}

	@Override
	public Object convertString(String value) {

		return value;
	}

	@Override
	public Object convertNull() {

		return null;
	}
}

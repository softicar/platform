package com.softicar.platform.db.structure.enums.value.converter;

import com.softicar.platform.db.structure.enums.IDbEnumTableRowValue;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowValueConverter;
import java.math.BigDecimal;

/**
 * Converts an {@link IDbEnumTableRowValue} into a {@link String}.
 *
 * @author Alexander Schmidt
 */
public class DbEnumTableRowValueToStringConverter implements IDbEnumTableRowValueConverter<String> {

	@Override
	public String convertBigDecimal(BigDecimal value) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String convertBoolean(Boolean value) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String convertDouble(Double value) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String convertFloat(Float value) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String convertInteger(Integer value) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String convertLong(Long value) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String convertString(String value) {

		return value;
	}

	@Override
	public String convertNull() {

		return null;
	}
}

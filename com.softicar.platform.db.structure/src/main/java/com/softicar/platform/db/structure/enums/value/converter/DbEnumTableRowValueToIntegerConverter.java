package com.softicar.platform.db.structure.enums.value.converter;

import com.softicar.platform.db.structure.enums.IDbEnumTableRowValue;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowValueConverter;
import java.math.BigDecimal;

/**
 * Converts an {@link IDbEnumTableRowValue} into an {@link Integer}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbEnumTableRowValueToIntegerConverter implements IDbEnumTableRowValueConverter<Integer> {

	@Override
	public Integer convertBigDecimal(BigDecimal value) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Integer convertBoolean(Boolean value) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Integer convertDouble(Double value) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Integer convertFloat(Float value) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Integer convertInteger(Integer value) {

		return value;
	}

	@Override
	public Integer convertLong(Long value) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Integer convertString(String value) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Integer convertNull() {

		return null;
	}
}

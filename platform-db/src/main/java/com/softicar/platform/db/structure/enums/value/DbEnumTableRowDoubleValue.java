package com.softicar.platform.db.structure.enums.value;

import com.softicar.platform.db.structure.enums.IDbEnumTableRowValueConverter;

public class DbEnumTableRowDoubleValue extends AbstractDbEnumTableRowValue<Double> {

	public DbEnumTableRowDoubleValue(Double value) {

		super(value);
	}

	@Override
	public <T> T convert(IDbEnumTableRowValueConverter<T> converter) {

		return converter.convertDouble(value);
	}
}

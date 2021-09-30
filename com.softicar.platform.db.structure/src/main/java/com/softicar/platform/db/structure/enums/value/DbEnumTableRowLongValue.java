package com.softicar.platform.db.structure.enums.value;

import com.softicar.platform.db.structure.enums.IDbEnumTableRowValueConverter;

public class DbEnumTableRowLongValue extends AbstractDbEnumTableRowValue<Long> {

	public DbEnumTableRowLongValue(Long value) {

		super(value);
	}

	@Override
	public <T> T convert(IDbEnumTableRowValueConverter<T> converter) {

		return converter.convertLong(value);
	}
}

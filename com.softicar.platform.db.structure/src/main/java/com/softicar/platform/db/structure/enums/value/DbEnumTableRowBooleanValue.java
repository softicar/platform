package com.softicar.platform.db.structure.enums.value;

import com.softicar.platform.db.structure.enums.IDbEnumTableRowValueConverter;

public class DbEnumTableRowBooleanValue extends AbstractDbEnumTableRowValue<Boolean> {

	public DbEnumTableRowBooleanValue(Boolean value) {

		super(value);
	}

	@Override
	public <T> T convert(IDbEnumTableRowValueConverter<T> converter) {

		return converter.convertBoolean(value);
	}
}

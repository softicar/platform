package com.softicar.platform.db.structure.enums.value;

import com.softicar.platform.db.structure.enums.IDbEnumTableRowValueConverter;

public class DbEnumTableRowIntegerValue extends AbstractDbEnumTableRowValue<Integer> {

	public DbEnumTableRowIntegerValue(Integer value) {

		super(value);
	}

	@Override
	public <T> T convert(IDbEnumTableRowValueConverter<T> converter) {

		return converter.convertInteger(value);
	}
}

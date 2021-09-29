package com.softicar.platform.db.structure.enums.value;

import com.softicar.platform.db.structure.enums.IDbEnumTableRowValue;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowValueConverter;

public class DbEnumTableRowNullValue implements IDbEnumTableRowValue {

	private static final DbEnumTableRowNullValue INSTANCE = new DbEnumTableRowNullValue();

	public static DbEnumTableRowNullValue getInstance() {

		return INSTANCE;
	}

	@Override
	public <T> T convert(IDbEnumTableRowValueConverter<T> converter) {

		return converter.convertNull();
	}

	@Override
	public String toString() {

		return "null";
	}
}

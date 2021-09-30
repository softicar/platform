package com.softicar.platform.db.structure.enums.value;

import com.softicar.platform.db.structure.enums.IDbEnumTableRowValueConverter;

public class DbEnumTableRowFloatValue extends AbstractDbEnumTableRowValue<Float> {

	public DbEnumTableRowFloatValue(Float value) {

		super(value);
	}

	@Override
	public <T> T convert(IDbEnumTableRowValueConverter<T> converter) {

		return converter.convertFloat(value);
	}
}

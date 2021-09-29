package com.softicar.platform.db.structure.enums.value;

import com.softicar.platform.db.structure.enums.IDbEnumTableRowValueConverter;
import java.math.BigDecimal;

public class DbEnumTableRowBigDecimalValue extends AbstractDbEnumTableRowValue<BigDecimal> {

	public DbEnumTableRowBigDecimalValue(BigDecimal value) {

		super(value);
	}

	@Override
	public <T> T convert(IDbEnumTableRowValueConverter<T> converter) {

		return converter.convertBigDecimal(value);
	}
}

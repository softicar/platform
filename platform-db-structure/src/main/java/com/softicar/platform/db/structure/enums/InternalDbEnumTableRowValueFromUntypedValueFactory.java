package com.softicar.platform.db.structure.enums;

import com.softicar.platform.db.structure.enums.value.DbEnumTableRowBigDecimalValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowBooleanValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowDoubleValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowFloatValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowIntegerValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowLongValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowNullValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowStringValue;
import java.math.BigDecimal;

class InternalDbEnumTableRowValueFromUntypedValueFactory {

	private final Object untypedValue;

	public InternalDbEnumTableRowValueFromUntypedValueFactory(Object untypedValue) {

		this.untypedValue = untypedValue;
	}

	public IDbEnumTableRowValue create() {

		if (untypedValue == null) {
			return DbEnumTableRowNullValue.getInstance();
		} else if (untypedValue instanceof BigDecimal) {
			return new DbEnumTableRowBigDecimalValue((BigDecimal) untypedValue);
		} else if (untypedValue instanceof Boolean) {
			return new DbEnumTableRowBooleanValue((Boolean) untypedValue);
		} else if (untypedValue instanceof Double) {
			return new DbEnumTableRowDoubleValue((Double) untypedValue);
		} else if (untypedValue instanceof Float) {
			return new DbEnumTableRowFloatValue((Float) untypedValue);
		} else if (untypedValue instanceof Integer) {
			return new DbEnumTableRowIntegerValue((Integer) untypedValue);
		} else if (untypedValue instanceof Long) {
			return new DbEnumTableRowLongValue((Long) untypedValue);
		} else if (untypedValue instanceof String) {
			return new DbEnumTableRowStringValue((String) untypedValue);
		} else {
			throw new DbIllegalEnumTableRowValueClassException(untypedValue.getClass());
		}
	}
}

package com.softicar.platform.db.structure.mysql.parser;

import com.softicar.platform.common.core.number.parser.DoubleParser;
import com.softicar.platform.common.core.number.parser.FloatParser;
import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.common.core.number.parser.LongParser;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.enums.DbIllegalEnumTableRowValueFieldTypeException;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowBigDecimalValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowBooleanValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowDoubleValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowFloatValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowIntegerValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowLongValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowNullValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowStringValue;
import java.math.BigDecimal;

class InternalDbEnumTableRowValueFromSqlLiteralFactory {

	private final SqlFieldType fieldType;
	private final String columnValue;

	public InternalDbEnumTableRowValueFromSqlLiteralFactory(SqlFieldType fieldType, String columnValue) {

		this.fieldType = fieldType;
		this.columnValue = columnValue;
	}

	public IDbEnumTableRowValue create() {

		if (columnValue == null) {
			return DbEnumTableRowNullValue.getInstance();
		} else if (fieldType == SqlFieldType.BIG_DECIMAL) {
			return new DbEnumTableRowBigDecimalValue(new BigDecimal(columnValue));
		} else if (fieldType == SqlFieldType.BOOLEAN) {
			return new DbEnumTableRowBooleanValue(columnValue.equals("1"));
		} else if (fieldType == SqlFieldType.DOUBLE) {
			return new DbEnumTableRowDoubleValue(DoubleParser.parseDouble(columnValue));
		} else if (fieldType == SqlFieldType.FLOAT) {
			return new DbEnumTableRowFloatValue(FloatParser.parseFloat(columnValue));
		} else if (fieldType == SqlFieldType.INTEGER) {
			return new DbEnumTableRowIntegerValue(IntegerParser.parseInteger(columnValue));
		} else if (fieldType == SqlFieldType.LONG) {
			return new DbEnumTableRowLongValue(LongParser.parseLong(columnValue));
		} else if (fieldType == SqlFieldType.STRING) {
			return new DbEnumTableRowStringValue(columnValue);
		} else {
			throw new DbIllegalEnumTableRowValueFieldTypeException(fieldType);
		}
	}
}

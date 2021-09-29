package com.softicar.platform.db.structure.enums;

import com.softicar.platform.db.sql.type.SqlFieldType;

public class DbIllegalEnumTableRowValueFieldTypeException extends IllegalArgumentException {

	public DbIllegalEnumTableRowValueFieldTypeException(SqlFieldType fieldType) {

		super(String.format("Illegal enum table row value of type %s.", fieldType));
	}
}

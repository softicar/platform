package com.softicar.platform.db.sql.fields;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.type.SqlFieldType;

public class SqlBooleanTestField<T> extends SqlTestField<T, Boolean> implements ISqlBooleanField<T> {

	public SqlBooleanTestField(ISqlTable<T> table, String sqlName, int index) {

		super(() -> table, SqlFieldType.BOOLEAN, sqlName, index);
	}
}

package com.softicar.platform.db.sql.fields;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.type.SqlFieldType;

public class SqlStringTestField<T> extends SqlTestField<T, String> implements ISqlStringField<T> {

	public SqlStringTestField(ISqlTable<T> table, String sqlName, int index) {

		super(() -> table, SqlFieldType.STRING, sqlName, index);
	}
}

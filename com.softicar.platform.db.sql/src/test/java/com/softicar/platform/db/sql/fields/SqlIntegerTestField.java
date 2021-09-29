package com.softicar.platform.db.sql.fields;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.type.SqlFieldType;

public class SqlIntegerTestField<T> extends SqlTestField<T, Integer> implements ISqlIntegerField<T> {

	public SqlIntegerTestField(ISqlTable<T> table, String sqlName, int index) {

		super(() -> table, SqlFieldType.INTEGER, sqlName, index);
	}
}

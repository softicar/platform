package com.softicar.platform.db.sql.fields;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.type.SqlFieldType;

public class SqlIdTestField<T extends IBasicItem> extends AbstractSqlField<T, Integer> implements ISqlIntegerField<T> {

	public SqlIdTestField(ISqlTable<T> table, String sqlName, int index) {

		super(() -> table, SqlFieldType.INTEGER, sqlName, index);
	}

	@Override
	public Integer getValue(T row) {

		return row.getId();
	}

	@Override
	public boolean setValue(T row, Integer value) {

		throw new UnsupportedOperationException();
	}
}

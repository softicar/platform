package com.softicar.platform.db.sql.fields;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.function.Supplier;

public class SqlTestField<R, V> extends AbstractSqlField<R, V> {

	public SqlTestField(Supplier<ISqlTable<R>> tableGetter, SqlFieldType fieldType, String sqlName, int index) {

		super(tableGetter, fieldType, sqlName, index);
	}

	@Override
	public V getValue(R row) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean setValue(R row, V value) {

		throw new UnsupportedOperationException();
	}
}

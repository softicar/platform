package com.softicar.platform.db.sql.fields;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.field.ISqlValueField;

public class SqlForeignRowTestField<R, F, FP> extends AbstractSqlForeignRowField<R, F, FP> {

	public SqlForeignRowTestField(ISqlTable<R> table, ISqlValueField<F, FP> targetField, String sqlName, int index) {

		super(() -> table, targetField, sqlName, index);
	}

	@Override
	public F getValue(R row) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean setValue(R row, F value) {

		throw new UnsupportedOperationException();
	}
}

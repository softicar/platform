package com.softicar.platform.db.runtime.query;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.sql.type.ISqlValueType;

public class DbQueryStubValueType<T> implements ISqlValueType<T> {

	private final IDbTable<T, ?> table;

	public DbQueryStubValueType(IDbTable<T, ?> table) {

		this.table = table;
	}

	public IDbTable<T, ?> getTable() {

		return table;
	}

	@Override
	public int getColumnCount() {

		return 1;
	}

	@Override
	public int compare(T left, T right) {

		return table.compare(left, right);
	}

	@Override
	public T getValue(DbResultSet resultSet, int index) {

		return table.getStub(resultSet, index);
	}

	@Override
	public Class<T> getValueClass() {

		return table.getValueClass();
	}
}

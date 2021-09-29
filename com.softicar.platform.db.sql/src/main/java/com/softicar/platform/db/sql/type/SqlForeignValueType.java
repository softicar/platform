package com.softicar.platform.db.sql.type;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.sql.ISqlTable;
import java.util.function.Supplier;

/**
 * Implementation of {@link ISqlValueType} for foreign fields.
 *
 * @author Oliver Richers
 */
final class SqlForeignValueType<F> extends PrimitiveSqlValueType<F> {

	private final Supplier<ISqlTable<F>> tableGetter;

	public SqlForeignValueType(Supplier<ISqlTable<F>> tableGetter) {

		this.tableGetter = tableGetter;
	}

	@Override
	public F getValue(DbResultSet resultSet, int index) {

		return tableGetter.get().getStub(resultSet, index);
	}

	@Override
	public Class<F> getValueClass() {

		return tableGetter.get().getValueClass();
	}

	@Override
	public int compare(F left, F right) {

		return tableGetter.get().compare(left, right);
	}
}

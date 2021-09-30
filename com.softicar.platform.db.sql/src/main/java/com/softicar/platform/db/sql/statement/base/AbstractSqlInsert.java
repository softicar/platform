package com.softicar.platform.db.sql.statement.base;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.statement.ISqlInsert;
import com.softicar.platform.db.sql.statement.builder.SqlInsertBuilder;
import java.util.Collection;
import java.util.Set;

public abstract class AbstractSqlInsert<R> implements ISqlInsert<R> {

	private final SqlInsertBuilder builder;

	protected AbstractSqlInsert(ISqlTable<R> table) {

		this.builder = new SqlInsertBuilder(table);
	}

	@Override
	public <V> void consumeFieldValue(ISqlField<R, V> field, V value) {

		set(field, value);
	}

	@Override
	public <V> ISqlInsert<R> set(ISqlField<R, V> field, V value) {

		builder.addValue(field, value);
		return this;
	}

	@Override
	public ISqlInsert<R> nextRow() {

		builder.finishTableRow();
		return this;
	}

	// -------------------- protected -------------------- //

	protected Set<ISqlTable<?>> getTables() {

		return builder.getTables();
	}

	protected int getRowCount() {

		return builder.getRowCount();
	}

	protected String getSqlText() {

		builder.finishTableRow();
		return builder.getText().toString();
	}

	protected Collection<Object> getSqlParameters() {

		return builder.getParameters();
	}
}

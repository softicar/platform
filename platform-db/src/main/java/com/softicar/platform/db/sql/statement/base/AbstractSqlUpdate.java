package com.softicar.platform.db.sql.statement.base;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.statement.ISqlUpdate;
import com.softicar.platform.db.sql.statement.builder.SqlUpdateBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlWhereClauseBuilder;

public abstract class AbstractSqlUpdate<R> extends AbstractSqlConditionalStatement<R, ISqlUpdate<R>> implements ISqlUpdate<R> {

	private final SqlUpdateBuilder builder;

	protected AbstractSqlUpdate(ISqlTable<R> table) {

		this.builder = new SqlUpdateBuilder(table);
	}

	// -------------------- public -------------------- //

	@Override
	public <V> void consumeFieldValue(ISqlField<R, V> field, V value) {

		set(field, value);
	}

	@Override
	public <V> ISqlUpdate<R> set(ISqlField<R, V> field, V value) {

		builder.getSetBuilder().set(field, value);
		return this;
	}

	@Override
	public <V extends Number> ISqlUpdate<R> increment(ISqlField<R, V> field, V value) {

		builder.getSetBuilder().increment(field, value);
		return this;
	}

	@Override
	public ISqlUpdate<R> orderBy(ISqlField<R, ?> field) {

		builder.getOrderByBuilder().orderBy(field);
		return this;
	}

	@Override
	public ISqlUpdate<R> orderDescendingBy(ISqlField<R, ?> field) {

		builder.getOrderByBuilder().orderDescendingBy(field);
		return this;
	}

	@Override
	public int execute() {

		return execute(0);
	}

	@Override
	public String toString() {

		return builder.getText().toString();
	}

	// -------------------- abstract -------------------- //

	@Override
	public abstract int execute(int count);

	// -------------------- protected -------------------- //

	@Override
	protected ISqlUpdate<R> getThis() {

		return this;
	}

	protected SqlUpdateBuilder getBuilder() {

		return builder;
	}

	@Override
	protected SqlWhereClauseBuilder getWhereBuilder() {

		return builder.getWhereBuilder();
	}
}

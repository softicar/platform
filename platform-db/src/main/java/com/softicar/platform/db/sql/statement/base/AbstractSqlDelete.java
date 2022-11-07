package com.softicar.platform.db.sql.statement.base;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.statement.ISqlDelete;
import com.softicar.platform.db.sql.statement.builder.SqlDeleteBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.SqlWhereClauseBuilder;

public abstract class AbstractSqlDelete<R> extends AbstractSqlConditionalStatement<R, ISqlDelete<R>> implements ISqlDelete<R> {

	private final SqlDeleteBuilder builder;

	protected AbstractSqlDelete(ISqlTable<R> table) {

		this.builder = new SqlDeleteBuilder(table);
	}

	protected SqlDeleteBuilder getBuilder() {

		return builder;
	}

	@Override
	public String toString() {

		return builder.getText().toString();
	}

	@Override
	public int execute() {

		return execute(0);
	}

	@Override
	public AbstractSqlDelete<R> orderBy(ISqlField<R, ?> field) {

		builder.getOrderByBuilder().orderBy(field);
		return this;
	}

	@Override
	public AbstractSqlDelete<R> orderDescendingBy(ISqlField<R, ?> field) {

		builder.getOrderByBuilder().orderDescendingBy(field);
		return this;
	}

	@Override
	protected ISqlDelete<R> getThis() {

		return this;
	}

	@Override
	protected SqlWhereClauseBuilder getWhereBuilder() {

		return builder.getWhereBuilder();
	}
}

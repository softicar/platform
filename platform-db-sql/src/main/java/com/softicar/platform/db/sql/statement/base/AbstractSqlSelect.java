package com.softicar.platform.db.sql.statement.base;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.ISqlJoin;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.db.sql.statement.builder.SqlSelectBuilder;

public abstract class AbstractSqlSelect<R>//
		extends AbstractSqlSelectOrJoin<R, R, ISqlSelect<R>>
		implements ISqlSelect<R> {

	protected AbstractSqlSelect(ISqlTable<R> table) {

		super("t", new SqlSelectBuilder(table));
	}

	@Override
	protected ISqlSelect<R> getThis() {

		return this;
	}

	@Override
	protected <X> ISqlJoin<R, X> createJoin(String joinAlias) {

		return new SqlJoin<>(joinAlias, this);
	}
}

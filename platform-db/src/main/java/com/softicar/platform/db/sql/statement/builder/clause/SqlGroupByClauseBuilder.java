package com.softicar.platform.db.sql.statement.builder.clause;

import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.statement.builder.SqlStatementBuilder;

public class SqlGroupByClauseBuilder extends SqlClauseBuilder {

	public SqlGroupByClauseBuilder(SqlStatementBuilder statementBuilder) {

		super(statementBuilder);
	}

	public void groupBy(ISqlField<?, ?> field) {

		addText(isEmpty()? " GROUP BY " : ", ");
		addField(field);
	}
}

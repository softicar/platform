package com.softicar.platform.db.sql.statement.builder.clause;

import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.statement.builder.SqlStatementBuilder;

public class SqlOrderByClauseBuilder extends SqlClauseBuilder {

	public SqlOrderByClauseBuilder(SqlStatementBuilder statementBuilder) {

		super(statementBuilder);
	}

	public void orderBy(ISqlField<?, ?> field) {

		addText(isEmpty()? " ORDER BY " : ", ");
		addField(field);
	}

	public void orderDescendingBy(ISqlField<?, ?> field) {

		addText(isEmpty()? " ORDER BY " : ", ");
		addField(field);
		addText(" DESC");
	}
}

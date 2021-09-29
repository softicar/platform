package com.softicar.platform.db.sql.statement.builder.clause;

import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.statement.builder.SqlStatementBuilder;

public class SqlUpdateSetClauseBuilder extends SqlClauseBuilder {

	public SqlUpdateSetClauseBuilder(SqlStatementBuilder statementBuilder) {

		super(statementBuilder);
	}

	public <V> void set(ISqlField<?, V> field, V value) {

		addText(isEmpty()? " SET " : ", ");
		addField(field).addText(" = ").addParameter(value);
	}

	public <V extends Number> void increment(ISqlField<?, V> field, V value) {

		addText(isEmpty()? " SET " : ", ");
		addField(field).addText(" = ").addField(field).addText(" + ").addParameter(value);
	}
}

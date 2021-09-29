package com.softicar.platform.db.sql.operations;

import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;

public class SqlTuple extends SqlImploder {

	public SqlTuple() {

		super(",");
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		builder.addText("(");
		super.build(builder);
		builder.addText(")");
	}
}

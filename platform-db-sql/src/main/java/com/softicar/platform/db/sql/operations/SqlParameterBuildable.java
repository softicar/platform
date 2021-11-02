package com.softicar.platform.db.sql.operations;

import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuildable;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;

public class SqlParameterBuildable implements ISqlClauseBuildable {

	private final Object parameter;

	public SqlParameterBuildable(Object parameter) {

		this.parameter = parameter;
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		builder.addParameter(parameter);
	}
}

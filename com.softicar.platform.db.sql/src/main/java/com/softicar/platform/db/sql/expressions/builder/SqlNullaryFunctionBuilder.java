package com.softicar.platform.db.sql.expressions.builder;

import com.softicar.platform.db.sql.expressions.builder.bases.AbstractSqlNullaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;

/**
 * Builds a nullary function expression in the form function().
 * 
 * @author Oliver Richers
 */
public class SqlNullaryFunctionBuilder extends AbstractSqlNullaryOperationBuilder {

	private final String function;

	public SqlNullaryFunctionBuilder(String function) {

		this.function = function;
	}

	@Override
	public final void build(ISqlClauseBuilder builder) {

		builder.addText(function);
	}
}

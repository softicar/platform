package com.softicar.platform.db.sql.expressions.builder;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.bases.AbstractSqlUnaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;

/**
 * Builds a unary function expression in the form function(expression).
 * 
 * @author Oliver Richers
 */
public class SqlUnaryFunctionBuilder extends AbstractSqlUnaryOperationBuilder {

	private final String function;

	public SqlUnaryFunctionBuilder(String function) {

		this.function = function;
	}

	@Override
	public final void build(ISqlClauseBuilder builder, ISqlExpression<?> expression) {

		builder.addText(function);
		builder.addText("(");
		expression.build(builder);
		builder.addText(")");
	}
}

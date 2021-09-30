package com.softicar.platform.db.sql.expressions.builder;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.bases.AbstractSqlBinaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;

/**
 * Builds a binary function expression in the form function(a, b).
 * 
 * @author Oliver Richers
 */
public class SqlBinaryFunctionBuilder extends AbstractSqlBinaryOperationBuilder {

	private final String function;

	public SqlBinaryFunctionBuilder(String function) {

		this.function = function;
	}

	@Override
	public final void build(ISqlClauseBuilder builder, ISqlExpression<?> expression1, ISqlExpression<?> expression2) {

		builder.addText(function);
		builder.addText("(");
		expression1.build(builder);
		builder.addText(", ");
		expression2.build(builder);
		builder.addText(")");
	}
}

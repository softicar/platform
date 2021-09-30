package com.softicar.platform.db.sql.expressions.builder;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.bases.AbstractSqlTrinaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;

/**
 * Builds a trinary function expression in the form function(a, b, c).
 * 
 * @author Oliver Richers
 */
public class SqlTrinaryFunctionBuilder extends AbstractSqlTrinaryOperationBuilder {

	private final String function;

	public SqlTrinaryFunctionBuilder(String function) {

		this.function = function;
	}

	@Override
	public final void build(ISqlClauseBuilder builder, ISqlExpression<?> expression1, ISqlExpression<?> expression2, ISqlExpression<?> expression3) {

		builder.addText(function);
		builder.addText("(");
		expression1.build(builder);
		builder.addText(", ");
		expression2.build(builder);
		builder.addText(", ");
		expression3.build(builder);
		builder.addText(")");
	}
}

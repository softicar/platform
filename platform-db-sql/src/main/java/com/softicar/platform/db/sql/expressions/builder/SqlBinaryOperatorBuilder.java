package com.softicar.platform.db.sql.expressions.builder;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.bases.AbstractSqlBinaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;

/**
 * Builds a binary operator expression in the form (a operator b).
 * 
 * @author Oliver Richers
 */
public class SqlBinaryOperatorBuilder extends AbstractSqlBinaryOperationBuilder {

	private final String operator;

	public SqlBinaryOperatorBuilder(String operator) {

		this.operator = operator;
	}

	@Override
	public final void build(ISqlClauseBuilder builder, ISqlExpression<?> expression1, ISqlExpression<?> expression2) {

		builder.addText("(");
		expression1.build(builder);
		builder.addText(operator);
		expression2.build(builder);
		builder.addText(")");
	}
}

package com.softicar.platform.db.sql.expressions.builder;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.bases.AbstractSqlUnaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;

/**
 * Builds a suffix operator expression in the form (expression operator).
 * 
 * @author Oliver Richers
 */
public class SqlSuffixOperatorBuilder extends AbstractSqlUnaryOperationBuilder {

	private final String operator;

	public SqlSuffixOperatorBuilder(String operator) {

		this.operator = operator;
	}

	@Override
	public final void build(ISqlClauseBuilder builder, ISqlExpression<?> expression) {

		builder.addText("(");
		expression.build(builder);
		builder.addText(operator);
		builder.addText(")");
	}
}

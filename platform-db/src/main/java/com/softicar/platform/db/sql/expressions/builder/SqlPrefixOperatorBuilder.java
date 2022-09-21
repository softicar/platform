package com.softicar.platform.db.sql.expressions.builder;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.bases.AbstractSqlUnaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;

/**
 * Builds a prefix operator expression in the form (operator expression).
 * 
 * @author Oliver Richers
 */
public class SqlPrefixOperatorBuilder extends AbstractSqlUnaryOperationBuilder {

	private final String operator;

	public SqlPrefixOperatorBuilder(String operator) {

		this.operator = operator;
	}

	@Override
	public final void build(ISqlClauseBuilder builder, ISqlExpression<?> expression) {

		builder.addText("(");
		builder.addText(operator);
		expression.build(builder);
		builder.addText(")");
	}
}

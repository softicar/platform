package com.softicar.platform.db.sql.expressions.builder;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.bases.AbstractSqlUnaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;

/**
 * Identity operation, builds the expression as is.
 * 
 * @author Oliver Richers
 */
public class SqlNoOperationBuilder extends AbstractSqlUnaryOperationBuilder {

	@Override
	public final void build(ISqlClauseBuilder builder, ISqlExpression<?> expression) {

		expression.build(builder);
	}
}

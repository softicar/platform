package com.softicar.platform.db.sql.expressions.builder.interfaces;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.operations.SqlTrinaryOperation;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;

/**
 * Expression builder for a trinary operation.
 * 
 * @author Oliver Richers
 */
public interface ISqlTrinaryOperationBuilder {

	void build(ISqlClauseBuilder builder, ISqlExpression<?> expression1, ISqlExpression<?> expression2, ISqlExpression<?> expression3);

	<VALUE> SqlTrinaryOperation<VALUE> create(ISqlValueType<VALUE> valueType, ISqlExpression<?> expression1, ISqlExpression<?> expression2,
			ISqlExpression<?> expression3);

	<VALUE> SqlTrinaryOperation<VALUE> create(ISqlExpression<VALUE> expression1, ISqlExpression<?> expression2, ISqlExpression<?> expression3);
}

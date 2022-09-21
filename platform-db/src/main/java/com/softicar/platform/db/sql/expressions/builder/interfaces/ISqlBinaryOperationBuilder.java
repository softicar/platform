package com.softicar.platform.db.sql.expressions.builder.interfaces;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.operations.SqlBinaryOperation;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;

/**
 * Expression builder for a binary operation.
 * 
 * @author Oliver Richers
 */
public interface ISqlBinaryOperationBuilder {

	void build(ISqlClauseBuilder builder, ISqlExpression<?> expression1, ISqlExpression<?> expression2);

	<VALUE> SqlBinaryOperation<VALUE> create(ISqlValueType<VALUE> valueType, ISqlExpression<?> expression1, ISqlExpression<?> expression2);

	<VALUE> SqlBinaryOperation<VALUE> create(ISqlExpression<VALUE> expression1, ISqlExpression<?> expression2);
}

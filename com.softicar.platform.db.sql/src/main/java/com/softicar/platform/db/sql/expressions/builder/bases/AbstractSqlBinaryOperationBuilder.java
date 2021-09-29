package com.softicar.platform.db.sql.expressions.builder.bases;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.interfaces.ISqlBinaryOperationBuilder;
import com.softicar.platform.db.sql.operations.SqlBinaryOperation;
import com.softicar.platform.db.sql.type.ISqlValueType;

/**
 * Implements the factory methods of the operation builder.
 * 
 * @author Oliver Richers
 */
public abstract class AbstractSqlBinaryOperationBuilder implements ISqlBinaryOperationBuilder {

	@Override
	public final <VALUE> SqlBinaryOperation<VALUE> create(ISqlValueType<VALUE> valueType, ISqlExpression<?> expression1, ISqlExpression<?> expression2) {

		return new SqlBinaryOperation<>(this, valueType, expression1, expression2);
	}

	@Override
	public final <VALUE> SqlBinaryOperation<VALUE> create(ISqlExpression<VALUE> expression1, ISqlExpression<?> expression2) {

		return create(expression1.getValueType(), expression1, expression2);
	}
}

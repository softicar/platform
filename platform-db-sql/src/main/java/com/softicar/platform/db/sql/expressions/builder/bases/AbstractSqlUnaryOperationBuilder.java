package com.softicar.platform.db.sql.expressions.builder.bases;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.interfaces.ISqlUnaryOperationBuilder;
import com.softicar.platform.db.sql.operations.SqlUnaryOperation;
import com.softicar.platform.db.sql.type.ISqlValueType;

/**
 * Implements the factory methods of the operation builder.
 * 
 * @author Oliver Richers
 */
public abstract class AbstractSqlUnaryOperationBuilder implements ISqlUnaryOperationBuilder {

	@Override
	public final <VALUE> SqlUnaryOperation<VALUE> create(ISqlValueType<VALUE> valueType, ISqlExpression<?> expression) {

		return new SqlUnaryOperation<>(this, valueType, expression);
	}

	@Override
	public final <VALUE> SqlUnaryOperation<VALUE> create(ISqlExpression<VALUE> expression) {

		return create(expression.getValueType(), expression);
	}
}

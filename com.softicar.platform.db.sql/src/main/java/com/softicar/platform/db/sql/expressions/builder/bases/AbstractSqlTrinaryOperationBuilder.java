package com.softicar.platform.db.sql.expressions.builder.bases;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.interfaces.ISqlTrinaryOperationBuilder;
import com.softicar.platform.db.sql.operations.SqlTrinaryOperation;
import com.softicar.platform.db.sql.type.ISqlValueType;

/**
 * Implements the factory methods of the operation builder.
 * 
 * @author Oliver Richers
 */
public abstract class AbstractSqlTrinaryOperationBuilder implements ISqlTrinaryOperationBuilder {

	@Override
	public final <VALUE> SqlTrinaryOperation<VALUE> create(ISqlValueType<VALUE> valueType, ISqlExpression<?> expression1, ISqlExpression<?> expression2,
			ISqlExpression<?> expression3) {

		return new SqlTrinaryOperation<>(this, valueType, expression1, expression2, expression3);
	}

	@Override
	public final <VALUE> SqlTrinaryOperation<VALUE> create(ISqlExpression<VALUE> expression1, ISqlExpression<?> expression2, ISqlExpression<?> expression3) {

		return create(expression1.getValueType(), expression1, expression2, expression3);
	}
}

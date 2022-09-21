package com.softicar.platform.db.sql.expressions.builder.interfaces;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.operations.SqlUnaryOperation;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;

/**
 * Expression builder for a unary operation.
 * 
 * @author Oliver Richers
 */
public interface ISqlUnaryOperationBuilder {

	void build(ISqlClauseBuilder builder, ISqlExpression<?> expression);

	<VALUE> SqlUnaryOperation<VALUE> create(ISqlValueType<VALUE> valueType, ISqlExpression<?> expression);

	<VALUE> SqlUnaryOperation<VALUE> create(ISqlExpression<VALUE> expression);
}

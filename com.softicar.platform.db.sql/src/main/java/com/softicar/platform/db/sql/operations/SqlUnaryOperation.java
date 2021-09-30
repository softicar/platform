package com.softicar.platform.db.sql.operations;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.interfaces.ISqlUnaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;

public final class SqlUnaryOperation<VALUE> extends AbstractSqlOperation<VALUE> {

	private final ISqlUnaryOperationBuilder builder;
	private final ISqlExpression<?> expression;

	public SqlUnaryOperation(ISqlUnaryOperationBuilder builder, ISqlValueType<VALUE> valueType, ISqlExpression<?> expression) {

		super(valueType);
		this.builder = builder;
		this.expression = expression;
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		this.builder.build(builder, expression);
	}
}

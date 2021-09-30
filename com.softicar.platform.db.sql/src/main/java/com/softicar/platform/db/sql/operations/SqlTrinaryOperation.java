package com.softicar.platform.db.sql.operations;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.interfaces.ISqlTrinaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;

public final class SqlTrinaryOperation<VALUE> extends AbstractSqlOperation<VALUE> {

	private final ISqlTrinaryOperationBuilder builder;
	private final ISqlExpression<?> expression1;
	private final ISqlExpression<?> expression2;
	private final ISqlExpression<?> expression3;

	public SqlTrinaryOperation(ISqlTrinaryOperationBuilder builder, ISqlValueType<VALUE> valueType, ISqlExpression<?> expression1,
			ISqlExpression<?> expression2, ISqlExpression<?> expression3) {

		super(valueType);
		this.builder = builder;
		this.expression1 = expression1;
		this.expression2 = expression2;
		this.expression3 = expression3;
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		this.builder.build(builder, expression1, expression2, expression3);
	}
}

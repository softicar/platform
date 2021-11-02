package com.softicar.platform.db.sql.operations;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.builder.interfaces.ISqlBinaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;

public final class SqlBinaryOperation<VALUE> extends AbstractSqlOperation<VALUE> {

	private final ISqlBinaryOperationBuilder builder;
	private final ISqlExpression<?> expression1;
	private final ISqlExpression<?> expression2;
	private boolean fixTable;

	public SqlBinaryOperation(ISqlBinaryOperationBuilder builder, ISqlValueType<VALUE> valueType, ISqlExpression<?> expression1,
			ISqlExpression<?> expression2) {

		super(valueType);
		this.builder = builder;
		this.expression1 = expression1;
		this.expression2 = expression2;
	}

	public SqlBinaryOperation<VALUE> setFixTable(boolean fixTable) {

		this.fixTable = fixTable;
		return this;
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		if (fixTable) {
			builder.fixTable();
		}

		this.builder.build(builder, expression1, expression2);

		if (fixTable) {
			builder.unfixTable();
		}
	}
}

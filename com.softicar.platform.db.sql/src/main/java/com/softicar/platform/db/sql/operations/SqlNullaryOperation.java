package com.softicar.platform.db.sql.operations;

import com.softicar.platform.db.sql.expressions.builder.interfaces.ISqlNullaryOperationBuilder;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import com.softicar.platform.db.sql.type.ISqlValueType;

public final class SqlNullaryOperation<VALUE> extends AbstractSqlOperation<VALUE> {

	private final ISqlNullaryOperationBuilder builder;

	public SqlNullaryOperation(ISqlNullaryOperationBuilder builder, ISqlValueType<VALUE> valueType) {

		super(valueType);
		this.builder = builder;
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		this.builder.build(builder);
	}
}

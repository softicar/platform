package com.softicar.platform.db.sql.operations;

import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.type.ISqlValueType;

public abstract class AbstractSqlOperation<VALUE> implements ISqlExpression<VALUE> {

	private final ISqlValueType<VALUE> valueType;

	public AbstractSqlOperation(ISqlValueType<VALUE> valueType) {

		this.valueType = valueType;
	}

	@Override
	public final ISqlValueType<VALUE> getValueType() {

		return valueType;
	}

	@Override
	public final int getColumnCount() {

		return 1;
	}
}

package com.softicar.platform.db.sql;

import com.softicar.platform.db.sql.type.ISqlValueType;
import com.softicar.platform.db.sql.type.SqlValueTypes;

/**
 * Basic implementation of {@link ISqlBooleanExpression}.
 * 
 * @author Oliver Richers
 */
public abstract class SqlBooleanExpressionBase<T> implements ISqlBooleanExpression<T> {

	@Override
	public SqlBooleanExpressionBase<T> and(ISqlBooleanExpression<T> e) {

		return SqlBooleanOperator.Type.AND.get(this, e);
	}

	@Override
	public SqlBooleanExpressionBase<T> or(ISqlBooleanExpression<T> e) {

		return SqlBooleanOperator.Type.OR.get(this, e);
	}

	@Override
	public ISqlValueType<Boolean> getValueType() {

		return SqlValueTypes.BOOLEAN;
	}

	@Override
	public int getColumnCount() {

		return 1;
	}
}

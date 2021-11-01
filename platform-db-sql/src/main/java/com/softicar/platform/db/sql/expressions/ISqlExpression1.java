package com.softicar.platform.db.sql.expressions;

import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.expressions.base.ISqlBasicExpression;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression1;

/**
 * Represents an SQL expression over a single database table.
 *
 * @author Oliver Richers
 */
public interface ISqlExpression1<V, T0> extends ISqlBasicExpression<ISqlBooleanExpression<T0>, V> {

	@Override
	default ISqlBooleanExpression<T0> wrapBool(ISqlExpression<Boolean> expression) {

		return new SqlBooleanExpression1<>(expression);
	}
}

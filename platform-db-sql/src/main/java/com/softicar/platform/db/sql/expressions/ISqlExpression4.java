package com.softicar.platform.db.sql.expressions;

import com.softicar.platform.db.sql.expressions.base.ISqlBasicExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression4;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression4;

/**
 * Represents an SQL expression over four database tables.
 *
 * @author Oliver Richers
 */
public interface ISqlExpression4<V, T0, T1, T2, T3> extends ISqlBasicExpression<ISqlBooleanExpression4<T0, T1, T2, T3>, V> {

	@Override
	default ISqlBooleanExpression4<T0, T1, T2, T3> wrapBool(ISqlExpression<Boolean> expression) {

		return new SqlBooleanExpression4<>(expression);
	}
}

package com.softicar.platform.db.sql.expressions;

import com.softicar.platform.db.sql.expressions.base.ISqlBasicExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression3;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression3;

/**
 * Represents an SQL expression over three database tables.
 *
 * @author Oliver Richers
 */
public interface ISqlExpression3<V, T0, T1, T2> extends ISqlBasicExpression<ISqlBooleanExpression3<T0, T1, T2>, V> {

	@Override
	default ISqlBooleanExpression3<T0, T1, T2> wrapBool(ISqlExpression<Boolean> expression) {

		return new SqlBooleanExpression3<>(expression);
	}
}

package com.softicar.platform.db.sql.expressions;

import com.softicar.platform.db.sql.expressions.base.ISqlBasicExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression2;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression2;

/**
 * Represents an SQL expression over two database tables.
 *
 * @author Oliver Richers
 */
public interface ISqlExpression2<V, T0, T1> extends ISqlBasicExpression<ISqlBooleanExpression2<T0, T1>, V> {

	@Override
	default ISqlBooleanExpression2<T0, T1> wrapBool(ISqlExpression<Boolean> expression) {

		return new SqlBooleanExpression2<>(expression);
	}
}

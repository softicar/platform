package com.softicar.platform.db.sql.expressions;

import com.softicar.platform.db.sql.expressions.base.ISqlBasicExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression0;
import com.softicar.platform.db.sql.expressions.bool.SqlBooleanExpression0;

/**
 * Represents an independent SQL expression that is not derived from a database
 * table.
 *
 * @author Oliver Richers
 */
public interface ISqlExpression0<V> extends ISqlBasicExpression<ISqlBooleanExpression0, V> {

	@Override
	default ISqlBooleanExpression0 wrapBool(ISqlExpression<Boolean> expression) {

		return new SqlBooleanExpression0(expression);
	}
}

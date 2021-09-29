package com.softicar.platform.db.sql;

import com.softicar.platform.db.sql.expressions.ISqlExpression1;

/**
 * Special interface for boolean expressions.
 *
 * @author Oliver Richers
 */
public interface ISqlBooleanExpression<T> extends ISqlExpression1<Boolean, T> {

	ISqlBooleanExpression<T> and(ISqlBooleanExpression<T> expression);

	ISqlBooleanExpression<T> or(ISqlBooleanExpression<T> expression);
}

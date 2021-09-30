package com.softicar.platform.db.sql.field;

import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.expressions.base.ISqlComparableExpression;

/**
 * Represents an {@link ISqlField} with a comparable value type.
 * 
 * @author Oliver Richers
 */
public interface ISqlValueField<R, V> extends ISqlField<R, V>, ISqlComparableExpression<ISqlBooleanExpression<R>, V> {

	// nothing to add
}

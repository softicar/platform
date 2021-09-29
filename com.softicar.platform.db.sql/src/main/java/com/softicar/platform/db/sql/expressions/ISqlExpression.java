package com.softicar.platform.db.sql.expressions;

import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuildable;
import com.softicar.platform.db.sql.type.ISqlValueType;
import com.softicar.platform.db.sql.type.SqlValueTypes;

/**
 * Basic interface of all SQL expressions.
 * 
 * @author Oliver Richers
 */
public interface ISqlExpression<V> extends ISqlClauseBuildable {

	/**
	 * Returns the value type of this expression.
	 * 
	 * @return value type of this expression
	 * @see ISqlValueType
	 * @see SqlValueTypes
	 */
	ISqlValueType<V> getValueType();

	/**
	 * Returns the number of columns this expression represents.
	 * <p>
	 * This returns 1 for fields and literals but for {@link ISqlTable} it will
	 * return the number of columns of the table.
	 * 
	 * @return column count of this expression
	 */
	int getColumnCount();
}

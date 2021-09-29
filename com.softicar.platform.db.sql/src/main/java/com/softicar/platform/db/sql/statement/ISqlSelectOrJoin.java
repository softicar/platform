package com.softicar.platform.db.sql.statement;

import com.softicar.platform.common.container.data.table.IDataTableRowMethods;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;

/**
 * Basic interface for <i>SELECT</i> statements.
 *
 * @param <R>
 *            the row type of the selected table
 * @param <J>
 *            the row type of the joined table
 * @author Oliver Richers
 */
public interface ISqlSelectOrJoin<R, J, This>//
		extends ISqlConditionalStatement<J, This>, IDataTableRowMethods<R> {

	/**
	 * Returns the first object found by the query or null if none has been
	 * found.
	 *
	 * @return first found object or null
	 */
	R getFirst();

	/**
	 * Returns the object found by the query or null if none has been found. If
	 * more that one object has been found, this function throws an exception.
	 *
	 * @return the found object or null
	 * @throws SofticarDeveloperException
	 *             if more than one object has been found
	 */
	R getOne();

	<X> ISqlJoin<R, X> join(ISqlForeignRowField<J, X, ?> field);

	<X> ISqlJoin<R, X> joinReverse(ISqlForeignRowField<X, J, ?> field);

	<X> ISqlJoin<R, X> joinLeft(ISqlForeignRowField<J, X, ?> field);

	<X> ISqlJoin<R, X> joinLeftReverse(ISqlForeignRowField<X, J, ?> field);

	This where(ISqlBooleanExpression<J> expression, boolean doWhere);

	This orWhere(ISqlBooleanExpression<J> expression, boolean doWhere);

	This orderBy(ISqlField<J, ?> field);

	This orderDescendingBy(ISqlField<J, ?> field);

	This groupBy(ISqlField<J, ?> field);

	String getSelectText();
}

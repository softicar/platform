package com.softicar.platform.db.sql;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.table.IDbCoreTable;
import com.softicar.platform.db.sql.expressions.ISqlExpression1;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.type.ISqlValueType;
import java.util.List;

/**
 * Common interface of all database tables.
 *
 * @param <R>
 *            the type of the table rows
 * @author Oliver Richers
 */
public interface ISqlTable<R> extends IDbCoreTable, ISqlValueType<R>, ISqlExpression1<R, R> {

	/**
	 * Returns a list of all fields.
	 *
	 * @return list of all fields
	 */
	List<? extends ISqlField<R, ?>> getAllFields();

	/**
	 * Returns the corresponding table row from the {@link DbResultSet}.
	 * <p>
	 * This method reads the primary key from the result set and returns the
	 * matching table row from the cache. If the row cannot be found in the
	 * cache, a <i>stub</i> is created, inserted into the cache and then
	 * returned.
	 *
	 * @param resultSet
	 *            the {@link DbResultSet} to load the primary key from
	 * @param index
	 *            the index into the {@link DbResultSet} to read the key from
	 * @return the corresponding table row or a stub-row
	 */
	R getStub(DbResultSet resultSet, int index);
}

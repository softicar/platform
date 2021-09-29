package com.softicar.platform.db.runtime.query;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.Comparator;
import java.util.Optional;

/**
 * Represents a column of an {@link IDbQuery}.
 *
 * @author Oliver Richers
 */
public interface IDbQueryColumn<R extends IDbQueryRow<R>, T> extends IDataTableColumn<R, T> {

	/**
	 * Returns the name of this column.
	 * <p>
	 * The name of a query column is usually the alias given to the expression
	 * in the <i>select clause</i> of the query statement. For simple table
	 * column selection, the alias can be omitted and then this method returns
	 * the name of that table column, that means, table column and query column
	 * have equal names.
	 *
	 * @return the column name (never null)
	 */
	String getName();

	/**
	 * Returns the {@link Class} of the objects in this column.
	 *
	 * @return the value class
	 */
	@Override
	Class<T> getValueClass();

	/**
	 * Returns the matching value of this column from the given query row.
	 *
	 * @param queryRow
	 *            the query row to load from
	 * @return the corresponding value (may be null)
	 */
	@Override
	T getValue(R queryRow);

	/**
	 * Returns an optional value comparator.
	 *
	 * @return an {@link Optional} of {@link Comparator} (never null)
	 */
	@Override
	Optional<Comparator<T>> getValueComparator();

	/**
	 * Compares the given values
	 *
	 * @param left
	 *            the left value
	 * @param right
	 *            the right value
	 * @return integer value as defined by {@link Comparator}
	 */
	int compareValues(T left, T right);

	/**
	 * Compares the values of the given rows in this column.
	 *
	 * @param leftRow
	 *            the left row
	 * @param rightRow
	 *            the right row
	 * @return integer value as defined by {@link Comparator}
	 */
	default int compareRowValues(R leftRow, R rightRow) {

		return compareValues(getValue(leftRow), getValue(rightRow));
	}

	/**
	 * Loads the column value from the given {@link DbResultSet}.
	 * <p>
	 * If this column is not active in the given {@link IDbSqlSelect}, this
	 * method does nothing and returns 0 as the number of consumed result-set
	 * columns; the returned value will be <i>null</i>.
	 *
	 * @param select
	 *            the executed SELECT statement
	 * @param resultSet
	 *            the {@link DbResultSet}
	 * @return the loaded value
	 */
	T loadValue(IDbSqlSelect select, DbResultSet resultSet);

	/**
	 * Returns the number of physical SQL columns comprised by this SQML column.
	 *
	 * @return number of physical columns
	 */
	int getPhysicalColumnCount();

	/**
	 * Returns the title of this query column.
	 *
	 * @return the title of this column (never null)
	 */
	@Override
	IDisplayString getTitle();

	/**
	 * Returns true if this column represents objects from a database table.
	 *
	 * @return true if this is a table column
	 */
	boolean isTable();

	/**
	 * Returns true if this is a table column and if the values are
	 * stub-objects.
	 *
	 * @return true if this is a table column of stubs
	 */
	boolean isStub();

	/**
	 * Returns the table object for the values of this column.
	 * <p>
	 * This method may only be called if {@link #isTable()} returns <i>true</i>.
	 *
	 * @return the table of this column values
	 * @throws ClassCastException
	 *             if this column is not a table column
	 */
	IDbTable<T, ?> getTable();
}

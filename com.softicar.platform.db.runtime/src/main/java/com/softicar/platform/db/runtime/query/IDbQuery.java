package com.softicar.platform.db.runtime.query;

import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.container.data.table.IDataTableSorterList;
import com.softicar.platform.db.runtime.query.builder.IDbQuerySqlBuilder;
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import java.util.List;

/**
 * Represents a database query.
 *
 * @author Oliver Richers
 */
public interface IDbQuery<R extends IDbQueryRow<R>> extends IDbQueryRowFactory<R>, IDataTable<R> {

	/**
	 * Calls the database server to determine the number of rows that this query
	 * will return.
	 * <p>
	 * Note that the number of rows returned here does not necessary match the
	 * number of rows returned by {@link #list(int, int)}. Only when both
	 * methods are called on the same database connection (i.e. the same
	 * database session) and the database isolation level is <i>repeatable
	 * read</i> or higher, both methods will work consistently.
	 *
	 * @return the number of rows in the query result set
	 */
	@Override
	int count();

	/**
	 * Executes this query and returns the first result row.
	 * <p>
	 * If the result set of this query is empty, this method will throw an
	 * exception.
	 *
	 * @return the first row of the result set, never null
	 * @throws DbQueryUnexpectedEmptyResultSetException
	 *             if the result set is empty
	 */
	R getFirstRowOrThrow();

	/**
	 * Executes this query and returns the first result row.
	 * <p>
	 * In contrast to {@link #getFirstRowOrThrow()}, this method will just
	 * return <i>null</i> if the query has an empty result set.
	 *
	 * @return the first row of the result set, or null if result set is empty
	 */
	R getFirstRowOrNull();

	/**
	 * Executes this query and returns the single result row.
	 * <p>
	 * If the result set of this query is empty, this method will throw an
	 * exception. If the result set contains more than one row, this method will
	 * also throw an exception.
	 *
	 * @return the result row, never null
	 * @throws DbQueryUnexpectedEmptyResultSetException
	 *             if the result set is empty
	 * @throws DbQueryTooManyResultRowsException
	 *             if the result set contains more than one row
	 */
	R getOneRowOrThrow();

	/**
	 * Executes this query and returns the single result row.
	 * <p>
	 * In contrast to {@link #getOneRowOrThrow()}, this method will just return
	 * <i>null</i> if the query has an empty result set.
	 *
	 * @return the result row, or null if the result set is empty
	 * @throws DbQueryTooManyResultRowsException
	 *             if the result set contains more than one row
	 */
	R getOneRowOrNull();

	/**
	 * Returns all {@link IDbQueryColumn} objects defined by this query.
	 * <p>
	 * This method will be deprecated, use {@link #getDeclaredColumns()}.
	 *
	 * @return list of all query columns
	 */
	List<IDbQueryColumn<R, ?>> getColumns();

	/**
	 * Returns all declared {@link IDbQueryColumn} objects of this query.
	 * <p>
	 * Depending on the query parameters, some of the columns returned by this
	 * method may be inactive. Use {@link #getTableColumns()} to get a list of
	 * all currently active columns.
	 * <p>
	 * This method is planned to replace the method {@link #getColumns()}, so
	 * that the letter method can be changed to return only active columns.
	 *
	 * @return list of all declared query columns
	 */
	List<IDbQueryColumn<R, ?>> getDeclaredColumns();

	/**
	 * Returns all active {@link IDbQueryColumn} objects of this query.
	 * <p>
	 * Since am {@link IDbQuery} can have active and inactive columns, this
	 * method only returns the active columns to be compatible to the definition
	 * in {@link IDataTable}.
	 *
	 * @return list of all active query columns
	 */
	@Override
	List<IDbQueryColumn<R, ?>> getTableColumns();

	IDbSqlSelect createSelect();

	IDbQuerySqlBuilder createSqlBuilder();

	@Override
	<T> List<T> getDistinctColumnValues(IDataTableColumn<R, T> column);

	@Override
	<T> List<T> getDistinctColumnValues(IDataTableColumn<R, T> column, int limit);

	@Override
	IDataTableFilterList<R> getFilters();

	@Override
	IDataTableSorterList<R> getSorters();

	/**
	 * Forces the given order of JOIN clauses.
	 *
	 * @param straightJoin
	 *            force JOIN order or not
	 * @return this query
	 */
	IDbQuery<R> setStraightJoin(boolean straightJoin);
}

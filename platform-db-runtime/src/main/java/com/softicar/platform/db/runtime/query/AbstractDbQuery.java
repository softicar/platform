package com.softicar.platform.db.runtime.query;

import com.softicar.platform.common.container.data.table.DataTableFilterListOperator;
import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.container.data.table.IDataTableSorterList;
import com.softicar.platform.common.container.iterator.LimitingIterator;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.DbResultSetIterator;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.runtime.query.builder.IDbQuerySqlBuilder;
import com.softicar.platform.db.runtime.query.filters.DbQueryFilterList;
import com.softicar.platform.db.runtime.query.sorters.DbQuerySorterList;
import com.softicar.platform.db.runtime.select.DbSqlFormatter;
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractDbQuery<R extends IDbQueryRow<R>> implements IDbQuery<R> {

	private final DbQueryFilterList<R> filters = new DbQueryFilterList<>(DataTableFilterListOperator.AND);
	private final DbQuerySorterList<R> sorters = new DbQuerySorterList<>();
	private boolean straightJoin;

	@Override
	public DataTableIdentifier getIdentifier() {

		String sql = new DbSqlFormatter(createSelect()).setExpandParameters(false).format();
		return new DataTableIdentifier(sql);
	}

	@Override
	public IDataTableFilterList<R> getFilters() {

		return filters;
	}

	@Override
	public IDataTableSorterList<R> getSorters() {

		return sorters;
	}

	@Override
	public AbstractDbQuery<R> setStraightJoin(boolean straightJoin) {

		this.straightJoin = straightJoin;
		return this;
	}

	@Override
	public int count() {

		IDbQuerySqlBuilder builder = createSqlBuilder();
		builder.setStraightJoin(straightJoin);
		builder.setFilters(filters);
		// TODO: applying of sorters is unnecessary, isn't it?
		sorters.applyTo(builder);

		try (DbResultSet resultSet = execute(builder.buildCountAllSelect())) {
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DbException("Failed to count number of select result rows.");
			}
		}
	}

	@Override
	public R getFirstRowOrThrow() {

		R row = getFirstRowOrNull();
		if (row == null) {
			throw new DbQueryUnexpectedEmptyResultSetException();
		}
		return row;
	}

	@Override
	public R getFirstRowOrNull() {

		List<R> rows = list(0, 1);
		return rows.isEmpty()? null : rows.get(0);
	}

	@Override
	public R getOneRowOrThrow() {

		R row = getFirstRowOrNull();
		if (row == null) {
			throw new DbQueryUnexpectedEmptyResultSetException();
		}
		return row;
	}

	@Override
	public R getOneRowOrNull() {

		List<R> rows = list(0, 2);
		if (rows.size() > 1) {
			throw new DbQueryTooManyResultRowsException(rows.size());
		}
		return rows.isEmpty()? null : rows.get(0);
	}

	@Override
	public Iterator<R> iterator(int offset, int limit) {

		if (DbConnections.getServerQuirks().isLimitAndOffsetSupported()) {
			IDbSqlSelect select = createSelect(offset, limit);
			return new DbResultSetIterator<>(() -> execute(select), resultSet -> createRow(select, resultSet));
		} else {
			IDbSqlSelect select = createSelect();
			DbResultSetIterator<R> resultSetIterator = new DbResultSetIterator<>(() -> execute(select), resultSet -> createRow(select, resultSet));
			return new LimitingIterator<>(resultSetIterator, offset, limit);
		}
	}

	private DbResultSet execute(IDbSqlSelect select) {

		String sql = new DbSqlFormatter(select).format();
		List<Object> parameters = select.getParameters();
		return new DbStatement()//
			.addTables(select.getTables())
			.addText(sql)
			.addParameters(parameters)
			.executeQuery();
	}

	@Override
	public List<IDbQueryColumn<R, ?>> getTableColumns() {

		IDbSqlSelect select = createSelect();
		List<IDbQueryColumn<R, ?>> activeColumns = new ArrayList<>();
		for (IDbQueryColumn<R, ?> column: getColumns()) {
			if (select.isActiveColumn(column.getName())) {
				activeColumns.add(column);
			}
		}
		return activeColumns;
	}

	@Override
	public IDbSqlSelect createSelect() {

		return createSelect(0, 0);
	}

	private IDbSqlSelect createSelect(int offset, int limit) {

		IDbQuerySqlBuilder builder = createSqlBuilder();
		builder.setStraightJoin(straightJoin);
		builder.setFilters(filters);
		sorters.applyTo(builder);
		builder.setOffset(offset);
		builder.setLimit(limit);
		return builder.buildSelect();
	}

	@Override
	public <T> List<T> getDistinctColumnValues(IDataTableColumn<R, T> column) {

		return getDistinctColumnValues(column, 0);
	}

	@Override
	public <T> List<T> getDistinctColumnValues(IDataTableColumn<R, T> column, int limit) {

		// TODO: this cast is nasty
		IDbQueryColumn<R, T> queryColumn = (IDbQueryColumn<R, T>) column;
		IDbQuerySqlBuilder builder = createSqlBuilder();
		builder.setStraightJoin(straightJoin);
		IDbSqlSelect sqlSelect = builder.buildDistinctColumnValuesSelect(queryColumn, limit);
		List<T> values = new ArrayList<>();
		try (DbResultSet resultSet = execute(sqlSelect)) {
			while (resultSet.next()) {
				T value = queryColumn.loadValue(sqlSelect, resultSet);
				values.add(value);
			}
		}
		return values;
	}

	@Override
	public String toString() {

		return new DbSqlFormatter(createSelect()).setExpandParameters(true).format();
	}

	@Override
	public abstract R createRow(IDbSqlSelect select, DbResultSet resultSet);

	@Override
	public List<IDbQueryColumn<R, ?>> getColumns() {

		// one of getColumns() or getDeclaredColumns() must be overridden
		return getDeclaredColumns();
	}

	@Override
	public List<IDbQueryColumn<R, ?>> getDeclaredColumns() {

		// one of getColumns() or getDeclaredColumns() must be overridden
		return getColumns();
	}

	@Override
	public abstract IDbQuerySqlBuilder createSqlBuilder();
}

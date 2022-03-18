package com.softicar.platform.common.container.data.table.in.memory;

import com.softicar.platform.common.container.data.table.DataTableFilterListOperator;
import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.container.data.table.IDataTableSorterList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Abstract implementation of memory based {@link IDataTable}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class AbstractInMemoryDataTable<R> implements IDataTable<R> {

	private final InMemoryDataTableFilterList<R> filterList;
	private final InMemoryDataTableSorterList<R> sorterList;
	private final IInMemoryRowProvider<R> rowProvider;
	private final InMemoryDataTableStructure<R> tableStructure;

	/**
	 * Constructs a new instance with an empty table structure.
	 * <p>
	 * The table structure can be defined by calling {@link #newColumn}.
	 */
	public AbstractInMemoryDataTable() {

		this(new InMemoryDataTableStructure<>());
	}

	/**
	 * Constructs a new instance with the given table structure.
	 *
	 * @param tableStructure
	 *            the predefined table structure (never null)
	 */
	public AbstractInMemoryDataTable(InMemoryDataTableStructure<R> tableStructure) {

		this.filterList = new InMemoryDataTableFilterList<>(DataTableFilterListOperator.AND, this::onFilterChanged);
		this.sorterList = new InMemoryDataTableSorterList<>(this::onSorterChanged);
		this.rowProvider = new InMemoryCachingRowProvider<>(this::getTableRows, filterList, sorterList);
		this.tableStructure = Objects.requireNonNull(tableStructure);
	}

	// -------------------- IDataTable -------------------- //

	@Override
	public List<? extends IDataTableColumn<R, ?>> getTableColumns() {

		return tableStructure.getTableColumns();
	}

	@Override
	public <V> List<V> getDistinctColumnValues(IDataTableColumn<R, V> column, int limit) {

		return new InMemoryDataTableColumnValuesLoader<>(column).loadDistinctValues(getTableRows(), limit);
	}

	@Override
	public List<R> list(int offset, int limit) {

		return rowProvider.getFilteredAndSortedRows(offset, limit);
	}

	@Override
	public Iterator<R> iterator(int offset, int limit) {

		return rowProvider.getFilteredAndSortedRows(offset, limit).iterator();
	}

	@Override
	public int count() {

		return rowProvider.getRowCount();
	}

	@Override
	public IDataTableFilterList<R> getFilters() {

		return filterList;
	}

	@Override
	public IDataTableSorterList<R> getSorters() {

		return sorterList;
	}

	@Override
	public void invalidateCaches() {

		rowProvider.invalidateCaches();
	}

	/**
	 * Initiates the creation of a new table column.
	 *
	 * @param valueClass
	 *            the value class of the column
	 * @return a column builder instance
	 */
	public <V> InMemoryDataTableColumnBuilder<R, V>.GetterSetter newColumn(Class<V> valueClass) {

		return tableStructure.newColumn(valueClass);
	}

	// -------------------- protected -------------------- //

	protected InMemoryDataTableStructure<R> getTableStructure() {

		return tableStructure;
	}

	/**
	 * Returns all rows of this table as an {@link Iterable}.
	 *
	 * @return all table rows (never null)
	 */
	protected abstract Iterable<R> getTableRows();

	// -------------------- private -------------------- //

	private void onFilterChanged() {

		rowProvider.onFilterChanged();
	}

	private void onSorterChanged() {

		rowProvider.onSorterChanged();
	}
}

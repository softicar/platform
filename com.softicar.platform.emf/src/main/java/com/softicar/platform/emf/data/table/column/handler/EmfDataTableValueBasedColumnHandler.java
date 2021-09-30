package com.softicar.platform.emf.data.table.column.handler;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.db.runtime.query.IDbQueryTableColumn;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.building.EmfDataTableCellDefaultsProvider;
import com.softicar.platform.emf.data.table.column.handler.building.IEmfDataTableCellBuilder;
import com.softicar.platform.emf.data.table.column.handler.filtering.EmfDataTableColumnFilterNodeFactoryMap;
import com.softicar.platform.emf.data.table.column.handler.filtering.IEmfDataTableColumnFilterNodeFactory;
import java.util.Collection;

/**
 * Default implementation of {@link IEmfDataTableValueBasedColumnHandler}.
 * <p>
 * This is the recommended base class for value-based column handlers,
 * supporting filtering and sorting. For action column, the class
 * {@link AbstractEmfDataTableColumnHandler} is recommended.
 *
 * @see AbstractEmfDataTableColumnHandler
 * @author Oliver Richers
 */
public class EmfDataTableValueBasedColumnHandler<T> implements IEmfDataTableValueBasedColumnHandler<T> {

	private IEmfDataTableCellBuilder<T> cellBuilder;
	private boolean filterable;
	private boolean sortable;

	public EmfDataTableValueBasedColumnHandler() {

		this(true, true);
	}

	public EmfDataTableValueBasedColumnHandler(boolean filterable, boolean sortable) {

		this.cellBuilder = null;
		this.filterable = filterable;
		this.sortable = sortable;
	}

	// -------------------- cell building -------------------- //

	@Override
	public void prefetchData(IEmfDataTableColumn<?, T> column, Collection<T> values) {

		column.getDataColumn().prefetchData(values);
	}

	@Override
	public void buildCell(IEmfDataTableCell<?, T> cell, T value) {

		if (cellBuilder == null) {
			this.cellBuilder = getCellBuilder(cell.getColumn().getDataColumn());
		}

		this.cellBuilder.buildCell(cell, value);
	}

	private IEmfDataTableCellBuilder<T> getCellBuilder(IDataTableColumn<?, T> column) {

		return EmfDataTableCellDefaultsProvider.get().getBuilder(column);
	}

	@Override
	public boolean isSortable(IEmfDataTableColumn<?, T> column) {

		if (!sortable) {
			return false;
		}
		if (IEntity.class.isAssignableFrom(column.getDataColumn().getValueClass())) {
			if (column.getDataColumn() instanceof IDbQueryTableColumn) {
				// disallow sorting of table.* select columns (#43357)
				return false;
			}
			// Simple items are sortable in general, although in-memory data tables will
			// sort by display string while query based data tables will sort by ID.
			return true;
		} else if (IDbTableRow.class.isAssignableFrom(column.getDataColumn().getValueClass())) {
			// Columns of table row type are hardly sortable in a sensible way.
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isReverseOrderDirection() {

		return false;
	}

	@Override
	public IEmfDataTableColumnFilterNodeFactory<T> getFilterNodeFactory(IEmfDataTableColumn<?, T> column) {

		if (!filterable) {
			return null;
		}
		return EmfDataTableColumnFilterNodeFactoryMap.get().getFactory(column.getDataColumn());
	}
}

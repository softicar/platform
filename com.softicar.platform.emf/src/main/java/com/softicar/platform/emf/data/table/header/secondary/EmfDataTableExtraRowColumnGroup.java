package com.softicar.platform.emf.data.table.header.secondary;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.Collection;

/**
 * A group of columns with a common title.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class EmfDataTableExtraRowColumnGroup<R> implements IEmfDataTableExtraRowColumnGroup<R> {

	private final IEmfDataTableExtraRowCellBuilder<R> cellBuilder;
	private final Collection<? extends IDataTableColumn<?, ?>> columns;

	public EmfDataTableExtraRowColumnGroup(IEmfDataTableExtraRowCellBuilder<R> cellBuilder, Collection<? extends IDataTableColumn<?, ?>> columns) {

		this.cellBuilder = cellBuilder;
		this.columns = columns;
	}

	@Override
	public IEmfDataTableExtraRowCellBuilder<R> getCellBuilder() {

		return cellBuilder;
	}

	@Override
	public Collection<? extends IDataTableColumn<?, ?>> getColumns() {

		return columns;
	}
}

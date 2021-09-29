package com.softicar.platform.emf.data.table.configuration;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import java.util.Collection;
import java.util.function.Supplier;

class EmfDataTableConfigurationTable<R> extends AbstractInMemoryDataTable<EmfDataTableConfigurationTableRow<R>> {

	private final Supplier<Collection<EmfDataTableConfigurationTableRow<R>>> rowSupplier;
	private final IDataTableColumn<EmfDataTableConfigurationTableRow<R>, Object> positionColumn;
	private final IDataTableColumn<EmfDataTableConfigurationTableRow<R>, Object> titleColumn;
	private final IDataTableColumn<EmfDataTableConfigurationTableRow<R>, Object> orderingColumn;

	public EmfDataTableConfigurationTable(Supplier<Collection<EmfDataTableConfigurationTableRow<R>>> rowSupplier) {

		this.rowSupplier = rowSupplier;
		this.positionColumn = newColumn(Object.class)//
			.setGetter(EmfDataTableConfigurationTableRow::getIndex)
			.setTitle(EmfDataTableI18n.POSITION)
			.addColumn();

		this.titleColumn = newColumn(Object.class)//
			.setGetter(EmfDataTableConfigurationTableRow::getIndex)
			.setTitle(EmfDataTableI18n.COLUMN)
			.addColumn();

		this.orderingColumn = newColumn(Object.class)//
			.setGetter(EmfDataTableConfigurationTableRow::getIndex)
			.setTitle(EmfDataTableI18n.ORDERING_PRIORITY)
			.addColumn();
	}

	@Override
	protected Iterable<EmfDataTableConfigurationTableRow<R>> getTableRows() {

		return rowSupplier.get();
	}

	public IDataTableColumn<EmfDataTableConfigurationTableRow<R>, Object> getPositionColumn() {

		return positionColumn;
	}

	public IDataTableColumn<EmfDataTableConfigurationTableRow<R>, Object> getTitleColumn() {

		return titleColumn;
	}

	public IDataTableColumn<EmfDataTableConfigurationTableRow<R>, Object> getOrderingColumn() {

		return orderingColumn;
	}
}

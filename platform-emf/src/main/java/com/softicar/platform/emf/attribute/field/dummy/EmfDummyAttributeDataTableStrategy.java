package com.softicar.platform.emf.attribute.field.dummy;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.emf.attribute.data.table.IEmfAttributeDataTableStrategy;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;

public class EmfDummyAttributeDataTableStrategy<E> implements IEmfAttributeDataTableStrategy<E> {

	@Override
	public void addDataColumns(AbstractInMemoryDataTable<E> dataTable) {

		// nothing to do by design
	}

	@Override
	public void setColumnHandlers(EmfDataTableDivBuilder<E> tableDivBuilder) {

		// nothing to do by design
	}

	@Override
	public void addOrderBy(EmfDataTableDivBuilder<E> builder, OrderDirection direction) {

		// nothing to do by design
	}
}

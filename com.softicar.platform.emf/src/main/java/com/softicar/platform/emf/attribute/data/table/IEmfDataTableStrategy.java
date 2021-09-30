package com.softicar.platform.emf.attribute.data.table;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;

public interface IEmfDataTableStrategy<E> {

	void addDataColumns(AbstractInMemoryDataTable<E> dataTable);

	void setColumnHandlers(EmfDataTableDivBuilder<E> tableDivBuilder);

	void addOrderBy(EmfDataTableDivBuilder<E> builder, OrderDirection direction);
}

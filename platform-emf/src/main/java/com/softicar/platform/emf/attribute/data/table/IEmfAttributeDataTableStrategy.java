package com.softicar.platform.emf.attribute.data.table;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.management.EmfManagementDataTable;

/**
 * Controls how an {@link IEmfAttribute} is integrated into an
 * {@link EmfManagementDataTable}.
 *
 * @author Oliver Richers
 */
public interface IEmfAttributeDataTableStrategy<E> {

	void addDataColumns(AbstractInMemoryDataTable<E> dataTable);

	void setColumnHandlers(EmfDataTableDivBuilder<E> tableDivBuilder);

	void addOrderBy(EmfDataTableDivBuilder<E> builder, OrderDirection direction);
}

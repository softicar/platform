package com.softicar.platform.emf.attribute.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public abstract class AbstractEmfDataTableStrategy<R extends IEmfTableRow<R, ?>, V> implements IEmfDataTableStrategy<R> {

	protected final IEmfAttribute<R, V> attribute;

	public AbstractEmfDataTableStrategy(IEmfAttribute<R, V> attribute) {

		this.attribute = attribute;
	}

	protected IDataTableColumn<R, V> addDataColumn(AbstractInMemoryDataTable<R> dataTable) {

		return dataTable//
			.newColumn(attribute.getValueClass())
			.setComparator(attribute.getValueComparator())
			.setGetter(attribute::getValue)
			.setTitle(attribute.getTitle())
			.addColumn();
	}
}

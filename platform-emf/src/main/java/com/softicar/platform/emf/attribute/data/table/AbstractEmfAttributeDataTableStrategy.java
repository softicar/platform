package com.softicar.platform.emf.attribute.data.table;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public abstract class AbstractEmfAttributeDataTableStrategy<R extends IEmfTableRow<R, ?>, V> implements IEmfAttributeDataTableStrategy<R> {

	protected final IEmfAttribute<R, V> attribute;

	public AbstractEmfAttributeDataTableStrategy(IEmfAttribute<R, V> attribute) {

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

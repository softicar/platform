package com.softicar.platform.emf.attribute.data.table;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfAttributeDataTableStrategy<R extends IEmfTableRow<R, ?>, V> implements IEmfAttributeDataTableStrategy<R> {

	protected final IEmfAttribute<R, V> attribute;
	protected IDataTableColumn<R, V> dataColumn;

	public EmfAttributeDataTableStrategy(IEmfAttribute<R, V> attribute) {

		this.attribute = attribute;
	}

	@Override
	public void addDataColumns(AbstractInMemoryDataTable<R> dataTable) {

		this.dataColumn = dataTable//
			.newColumn(attribute.getValueClass())
			.setComparator(attribute.getValueComparator())
			.setGetter(attribute::getValue)
			.setTitle(attribute.getTitle())
			.addColumn();
	}

	@Override
	public void setColumnHandlers(EmfDataTableDivBuilder<R> tableDivBuilder) {

		tableDivBuilder.setColumnHandler(dataColumn, attribute.createColumnHandler());
	}

	@Override
	public void addOrderBy(EmfDataTableDivBuilder<R> builder, OrderDirection direction) {

		builder.addOrderBy(dataColumn, direction);
	}
}

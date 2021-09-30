package com.softicar.platform.emf.entity.table.attribute;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableActionCell;
import com.softicar.platform.emf.entity.table.column.handler.EmfNotSortableColumnHandler;
import com.softicar.platform.emf.entity.table.column.handler.EmfPredicateColumnHandler;
import com.softicar.platform.emf.table.IEmfTable;

public class EmfTableAttributeOverviewTab extends DomTab {

	public EmfTableAttributeOverviewTab(IEmfTable<?, ?, ?> table) {

		super(EmfI18n.ATTRIBUTES_OVERVIEW);
		EmfTableAttributeOverviewTable overviewTable = new EmfTableAttributeOverviewTable(table);
		appendChild(
			new EmfDataTableDivBuilder<>(overviewTable)//
				.setActionColumnHandler((cell, row) -> buildActionCell(cell, row))
				.setColumnHandler(overviewTable.getPredicateVisibleColumn(), new EmfPredicateColumnHandler())
				.setColumnHandler(overviewTable.getPredicateEditableColumn(), new EmfPredicateColumnHandler())
				.setColumnHandler(overviewTable.getPredicateMandatoryColumn(), new EmfPredicateColumnHandler())
				.setColumnHandler(overviewTable.getConcealedColumn(), new EmfNotSortableColumnHandler())
				.setColumnHandler(overviewTable.getEditableColumn(), new EmfNotSortableColumnHandler())
				.setColumnHandler(overviewTable.getTransientColumn(), new EmfNotSortableColumnHandler())
				.build());
	}

	private void buildActionCell(IEmfDataTableActionCell<IEmfAttribute<?, ?>> cell, IEmfAttribute<?, ?> attribute) {

		DevNull.swallow(cell);
		DevNull.swallow(attribute);
	}
}

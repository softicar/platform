package com.softicar.platform.emf.entity.table.action;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.action.IEmfAction;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableActionCell;
import com.softicar.platform.emf.entity.table.column.handler.EmfIconColumnHandler;
import com.softicar.platform.emf.entity.table.column.handler.EmfPredicateColumnHandler;
import com.softicar.platform.emf.entity.table.column.handler.EmfRoleColumnHandler;
import com.softicar.platform.emf.table.IEmfTable;

public class EmfTableActionOverviewTab extends DomTab {

	public EmfTableActionOverviewTab(IEmfTable<?, ?, ?> table) {

		super(EmfI18n.ACTIONS_OVERVIEW);
		EmfTableActionOverviewTable overviewTable = new EmfTableActionOverviewTable(table);
		appendChild(
			new EmfDataTableDivBuilder<>(overviewTable)//
				.setActionColumnHandler((cell, row) -> buildActionCell(cell, row))
				.setColumnHandler(overviewTable.getIconColumn(), new EmfIconColumnHandler())
				.setColumnHandler(overviewTable.getPreconditionColumn(), new EmfPredicateColumnHandler())
				.setColumnHandler(overviewTable.getAuthorizedRoleColumn(), new EmfRoleColumnHandler())
				.setOrderBy(overviewTable.getTitleColumn(), OrderDirection.ASCENDING)
				.build());
	}

	private void buildActionCell(IEmfDataTableActionCell<IEmfAction<?>> cell, IEmfAction<?> action) {

		DevNull.swallow(cell);
		DevNull.swallow(action);
	}
}

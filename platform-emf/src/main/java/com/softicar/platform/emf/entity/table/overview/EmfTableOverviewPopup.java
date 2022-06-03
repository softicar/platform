package com.softicar.platform.emf.entity.table.overview;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.styles.CssTextAlign;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableActionCell;
import com.softicar.platform.emf.entity.table.column.handler.EmfChildTablesColumnHandler;
import com.softicar.platform.emf.entity.table.column.handler.EmfIconColumnHandler;
import com.softicar.platform.emf.entity.table.column.handler.EmfNotSortableColumnHandler;
import com.softicar.platform.emf.entity.table.column.handler.EmfScopeFieldColumnHandler;
import com.softicar.platform.emf.entity.table.column.handler.EmfUsedPermissionsColumnHandler;
import com.softicar.platform.emf.entity.table.view.EmfTableViewButton;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.table.IEmfTable;

public class EmfTableOverviewPopup extends DomPopup {

	public EmfTableOverviewPopup(IEmfModule<?> module) {

		EmfTableOverviewTable table = new EmfTableOverviewTable(module);
		setCaption(EmfI18n.TABLE_OVERVIEW);
		appendChild(
			new EmfDataTableDivBuilder<>(table)//
				.setActionColumnHandler((cell, row) -> buildActionCell(cell, row))
				.setColumnHandler(table.getIconColumn(), new EmfIconColumnHandler())
				.setColumnHandler(table.getUsedPermissionsColumn(), new EmfUsedPermissionsColumnHandler())
				.setColumnHandler(table.getChildTablesColumn(), new EmfChildTablesColumnHandler())
				.setColumnHandler(table.getScopeFieldColumn(), new EmfScopeFieldColumnHandler())
				.setColumnHandler(table.getHasChangeLoggersColumn(), new EmfNotSortableColumnHandler())
				.setColumnHandler(table.getHasActiveFieldColumn(), new EmfNotSortableColumnHandler())
				.setOrderBy(table.getSimpleValueClassNameColumn(), OrderDirection.ASCENDING)
				.build());
	}

	private void buildActionCell(IEmfDataTableActionCell<IEmfTable<?, ?, ?>> cell, IEmfTable<?, ?, ?> table) {

		cell.setStyle(CssTextAlign.CENTER);
		cell.appendChild(new EmfTableViewButton(table).removeLabel());
	}
}

package com.softicar.platform.emf.management;

import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfManagementPopup<R extends IEmfTableRow<R, P>, P, S extends IEmfTableRow<?, ?>> extends DomPopup {

	private final EmfManagementDiv<R, P, S> managementTable;
	private final IEmfTable<R, P, S> entityTable;

	public EmfManagementPopup(IEmfTable<R, P, S> entityTable, S scopeEntity) {

		this.managementTable = new EmfManagementDiv<>(entityTable, scopeEntity);
		this.entityTable = entityTable;

		setCaption(entityTable.getPluralTitle());
		setSubCaption(scopeEntity.toDisplay());

		appendChild(managementTable);
	}

	public EmfManagementPopup<R, P, S> setRefreshable(IRefreshable refreshable) {

		managementTable.setRefreshable(refreshable);
		return this;
	}

	public IEmfTable<R, P, S> getEntityTable() {

		return entityTable;
	}
}

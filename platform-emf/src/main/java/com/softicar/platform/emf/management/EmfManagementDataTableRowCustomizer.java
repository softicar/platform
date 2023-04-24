package com.softicar.platform.emf.management;

import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.user.CurrentDomPreferences;
import com.softicar.platform.emf.data.table.IEmfDataTableRow;
import com.softicar.platform.emf.data.table.IEmfDataTableRowCustomizer;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfManagementDataTableRowCustomizer<R extends IEmfTableRow<R, P>, P> implements IEmfDataTableRowCustomizer<R> {

	@Override
	public void customizeRow(IEmfDataTableRow<R> tableRow) {

		tableRow.listenToEvent(DomEventType.CONTEXTMENU);

		if (CurrentDomPreferences.get().enableDoubleClickOnTableRows()) {
			tableRow.listenToEvent(DomEventType.DBLCLICK);
		}

		tableRow.setEventHandler(new EmfManagementDataTableRowEventHandler<>());
	}
}

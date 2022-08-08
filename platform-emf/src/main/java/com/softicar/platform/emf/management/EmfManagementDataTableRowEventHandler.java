package com.softicar.platform.emf.management;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.emf.data.table.IEmfDataTableRowEventHandler;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfManagementDataTableRowEventHandler<R extends IEmfTableRow<R, P>, P> implements IEmfDataTableRowEventHandler<R> {

	@Override
	public void handleEvent(R row) {

		switch (CurrentDomDocument.get().getCurrentEvent().getType()) {
		case CONTEXTMENU: {
			new EmfManagementActionPopover<>(row).open();
			break;
		}
		case DBLCLICK: {
			showFormPopup(row);
			break;
		}
		default:
			//nothing to do
			break;
		}
	}

	private void showFormPopup(R row) {

		DomPopupManager//
			.getInstance()
			.getPopup(row, EmfFormPopup.class, EmfFormPopup::new)
			.open();
	}
}

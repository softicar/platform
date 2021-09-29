package com.softicar.platform.emf.data.table;

import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.emf.EmfCssClasses;

class EmfDataTableRowSelectionCheckbox<R> extends DomCheckbox {

	private final EmfDataTableRow<R> tableRow;

	public EmfDataTableRowSelectionCheckbox(EmfDataTableRow<R> tableRow) {

		this.tableRow = tableRow;
		addCssClass(EmfCssClasses.EMF_DATA_TABLE_ROW_SELECTION_CHECKBOX);
		setTitle(EmfDataTableI18n.SELECTION);
		setMarker(EmfDataTableDivMarker.ROW_SELECTION_CHECKBOX);
	}

	@Override
	public void handleClick(IDomEvent event) {

		super.handleClick(event);
		tableRow.setSelected(isChecked(), true);
	}
}

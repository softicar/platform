package com.softicar.platform.emf.data.table;

import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.attribute.field.bool.EmfBooleanInput;

class EmfDataTableRowSelectionCheckbox<R> extends EmfBooleanInput {

	private final EmfDataTableRow<R> tableRow;

	public EmfDataTableRowSelectionCheckbox(EmfDataTableRow<R> tableRow) {

		super(false);
		this.tableRow = tableRow;
		addCssClass(EmfCssClasses.EMF_DATA_TABLE_ROW_SELECTION_CHECKBOX);
		setTitle(EmfDataTableI18n.SELECTION);
		addMarker(EmfDataTableDivMarker.ROW_SELECTION_CHECKBOX);
	}

	@Override
	public void handleClick(IDomEvent event) {

		super.handleClick(event);
		tableRow.setSelected(isChecked(), true);
	}

	@Override
	public void handleEnterKey(IDomEvent event) {

		super.handleEnterKey(event);
		tableRow.setSelected(isChecked(), true);
	}

	@Override
	public void handleSpaceKey(IDomEvent event) {

		super.handleSpaceKey(event);
		tableRow.setSelected(isChecked(), true);
	}
}

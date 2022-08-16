package com.softicar.platform.emf.data.table.filter;

import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;

public class EmfDataTableFilterButton<R> extends DomButton {

	private final IEmfDataTableColumn<R, ?> column;

	public EmfDataTableFilterButton(IEmfDataTableColumn<R, ?> column) {

		this.column = column;

		setLabel(column.getTitle());
		setTitle(EmfDataTableI18n.CLICK_TO_FILTER_IN_THIS_COLUMN);
		addMarker(EmfTestMarker.DATA_TABLE_FILTER_POPUP_BUTTON);
		setClickCallback(this::openFilterPopup);
	}

	private void openFilterPopup() {

		column.getFilterPopup().open();
	}
}

package com.softicar.platform.emf.entity.table.view;

import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.table.IEmfTable;

public class EmfTableViewButton extends DomButton {

	public EmfTableViewButton(IEmfTable<?, ?, ?> table) {

		setLabel(table.getTitle());
		setIcon(EmfImages.TABLE_DETAILS.getResource());
		setTitle(EmfI18n.SHOW_TABLE_INFORMATION);
		setClickCallback(() -> new EmfTableViewPopup(table).show());
	}
}

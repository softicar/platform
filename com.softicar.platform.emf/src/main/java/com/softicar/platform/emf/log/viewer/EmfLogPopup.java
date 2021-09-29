package com.softicar.platform.emf.log.viewer;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfLogPopup<R extends IEmfTableRow<R, ?>> extends DomPopup {

	public EmfLogPopup(R tableRow) {

		setCaption(EmfI18n.HISTORY);
		setSubCaption(tableRow.table().getTitle());
		appendChild(new EmfLogDiv<>(tableRow));
		appendCloseButton();
	}
}

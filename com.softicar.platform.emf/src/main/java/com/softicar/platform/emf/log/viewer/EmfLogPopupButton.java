package com.softicar.platform.emf.log.viewer;

import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfLogPopupButton<R extends IEmfTableRow<R, ?>> extends DomPopupButton {

	public EmfLogPopupButton(R tableRow) {

		setIcon(EmfImages.ENTITY_LOG.getResource());
		setTitle(EmfI18n.SHOW_HISTORY);
		setPopupFactory(() -> new EmfLogPopup<>(tableRow));
	}
}

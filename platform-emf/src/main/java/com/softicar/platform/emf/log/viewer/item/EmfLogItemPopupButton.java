package com.softicar.platform.emf.log.viewer.item;

import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.log.viewer.EmfLogMarker;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfLogItemPopupButton<R extends IEmfTableRow<R, ?>> extends DomPopupButton {

	public EmfLogItemPopupButton(EmfLogItem<R> logItem) {

		setIcon(EmfImages.ENTITY_VIEW.getResource());
		setTitle(EmfI18n.VIEW_HISTORY_ENTRY);
		setPopupFactory(() -> new EmfLogItemPopup<>(logItem));
		addMarker(EmfLogMarker.FEED_ITEM_POPUP_BUTTON);
	}
}

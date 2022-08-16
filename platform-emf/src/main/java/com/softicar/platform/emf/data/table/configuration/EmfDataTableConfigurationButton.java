package com.softicar.platform.emf.data.table.configuration;

import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.data.table.IEmfDataTableController;

class EmfDataTableConfigurationButton<R> extends DomPopupButton {

	public EmfDataTableConfigurationButton(IEmfDataTableController<R> controller) {

		setIcon(EmfImages.TABLE_CONFIGURATION.getResource());
		setTitle(EmfDataTableI18n.TABLE_CONFIGURATION);
		addMarker(EmfTestMarker.DATA_TABLE_CONFIGURATION_TABLE_CONFIGURATION_BUTTON);
		setPopupFactory(() -> new EmfDataTableConfigurationPopup<>(controller));
	}
}

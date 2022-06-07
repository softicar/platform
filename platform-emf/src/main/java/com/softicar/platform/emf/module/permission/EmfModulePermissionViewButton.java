package com.softicar.platform.emf.module.permission;

import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.module.IEmfModule;

public class EmfModulePermissionViewButton extends DomPopupButton {

	public EmfModulePermissionViewButton(IEmfModule<?> module) {

		setIcon(EmfImages.USER_PERMISSION_ASSIGNMENT.getResource());
		setLabel(EmfI18n.SHOW_USED_PERMISSIONS);
		setPopupFactory(() -> new EmfModulePermissionViewPopup(module));
	}
}

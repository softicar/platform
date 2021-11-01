package com.softicar.platform.emf.module.role;

import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.module.IEmfModule;

public class EmfModuleRoleViewButton extends DomButton {

	public EmfModuleRoleViewButton(IEmfModule<?> module) {

		setIcon(EmfImages.USER_ROLE_ASSIGNMENT.getResource());
		setLabel(EmfI18n.SHOW_USED_ROLES);
		setClickCallback(() -> new EmfModuleRoleViewPopup(module).show());
	}
}

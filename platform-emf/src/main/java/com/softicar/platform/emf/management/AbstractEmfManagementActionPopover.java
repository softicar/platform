package com.softicar.platform.emf.management;

import com.softicar.platform.dom.elements.popup.modal.DomPopover;
import com.softicar.platform.emf.EmfCssClasses;

public abstract class AbstractEmfManagementActionPopover extends DomPopover {

	public AbstractEmfManagementActionPopover() {

		addCssClass(EmfCssClasses.EMF_MANAGEMENT_ACTIONS_POPOVER);
	}
}

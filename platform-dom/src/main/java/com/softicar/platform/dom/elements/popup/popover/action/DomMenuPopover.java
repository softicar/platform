package com.softicar.platform.dom.elements.popup.popover.action;

import com.softicar.platform.dom.DomCssClasses;

public class DomMenuPopover extends DomPopover {

	public DomMenuPopover() {

		configuration.setDisplayModePopover();
		addCssClass(DomCssClasses.DOM_MENU_POPOVER);
	}
}

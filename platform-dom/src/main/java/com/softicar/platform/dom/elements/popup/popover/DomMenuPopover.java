package com.softicar.platform.dom.elements.popup.popover;

import com.softicar.platform.dom.DomCssClasses;

public class DomMenuPopover extends DomPopover {

	public DomMenuPopover() {

		configuration.setDisplayModePopover();
		addCssClass(DomCssClasses.DOM_MENU_POPOVER);
	}
}

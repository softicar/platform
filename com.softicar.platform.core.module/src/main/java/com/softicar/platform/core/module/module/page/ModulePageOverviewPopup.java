package com.softicar.platform.core.module.module.page;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.tab.DomTabBar;
import com.softicar.platform.emf.module.IEmfModule;

public class ModulePageOverviewPopup extends DomPopup {

	private final DomTabBar tabBar = new DomTabBar();

	public ModulePageOverviewPopup(IEmfModule<?> module) {

		setCaption(CoreI18n.PAGE_OVERVIEW);
		setSubCaption(module.toDisplay());
		tabBar.appendTab(new ModulePagesTab(module));
		appendChild(tabBar);
	}
}

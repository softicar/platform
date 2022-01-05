package com.softicar.platform.core.module.module.page;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.emf.module.IEmfModule;

public class ModulePageOverviewButton extends DomPopupButton {

	public ModulePageOverviewButton(IEmfModule<?> module) {

		setIcon(CoreImages.MODULES.getResource());
		setLabel(CoreI18n.SHOW_PAGES);
		setPopupFactory(() -> new ModulePageOverviewPopup(module));
	}
}

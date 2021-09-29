package com.softicar.platform.core.module.module.page;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.module.IEmfModule;

public class ModulePageOverviewButton extends DomButton {

	public ModulePageOverviewButton(IEmfModule<?> module) {

		setIcon(CoreImages.MODULES.getResource());
		setLabel(CoreI18n.SHOW_PAGES);
		setClickCallback(() -> new ModulePageOverviewPopup(module).show());
	}
}

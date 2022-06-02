package com.softicar.platform.core.module.page;

import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.page.IEmfPage;

public class PageButton<I extends IEmfModuleInstance<I>> extends DomButton {

	public PageButton(Class<? extends IEmfPage<I>> pageClass, I moduleInstance) {

		setExternalLink(new PageUrlBuilder<>(pageClass, moduleInstance).build().getStartingFromPath());
	}
}

package com.softicar.platform.core.module.module.page;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.module.IEmfModule;
import java.util.Optional;

public class ModulePageOverviewButton extends DomButton {

	private Optional<INullaryVoidFunction> callbackBeforeShow;

	public ModulePageOverviewButton(IEmfModule<?> module) {

		setIcon(CoreImages.MODULES.getResource());
		setLabel(CoreI18n.SHOW_PAGES);
		setClickCallback(() -> {
			callbackBeforeShow.ifPresent(INullaryVoidFunction::apply);
			new ModulePageOverviewPopup(module).show();
		});
	}

	public ModulePageOverviewButton setCallbackBeforeShow(INullaryVoidFunction callbackBeforeShow) {

		this.callbackBeforeShow = Optional.ofNullable(callbackBeforeShow);
		return this;
	}
}

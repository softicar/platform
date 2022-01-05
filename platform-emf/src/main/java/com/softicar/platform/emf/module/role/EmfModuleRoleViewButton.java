package com.softicar.platform.emf.module.role;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.module.IEmfModule;
import java.util.Optional;

public class EmfModuleRoleViewButton extends DomButton {

	private Optional<INullaryVoidFunction> callbackBeforeShow;

	public EmfModuleRoleViewButton(IEmfModule<?> module) {

		setIcon(EmfImages.USER_ROLE_ASSIGNMENT.getResource());
		setLabel(EmfI18n.SHOW_USED_ROLES);
		setClickCallback(() -> {
			callbackBeforeShow.ifPresent(INullaryVoidFunction::apply);
			new EmfModuleRoleViewPopup(module).show();
		});
	}

	public EmfModuleRoleViewButton setCallbackBeforeShow(INullaryVoidFunction callbackBeforeShow) {

		this.callbackBeforeShow = Optional.ofNullable(callbackBeforeShow);
		return this;
	}
}

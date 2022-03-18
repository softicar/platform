package com.softicar.platform.emf.management.importing;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;

public class EmfImportBackButton extends DomButton {

	public EmfImportBackButton(INullaryVoidFunction function) {

		setIcon(EmfImages.WIZARD_PREVIOUS.getResource());
		setLabel(EmfI18n.BACK);
		setClickCallback(() -> function.apply());
	}
}

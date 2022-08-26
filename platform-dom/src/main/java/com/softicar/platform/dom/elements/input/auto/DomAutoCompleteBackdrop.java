package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;

class DomAutoCompleteBackdrop extends DomDiv implements IDomClickEventHandler {

	private final DomAutoCompleteInput<?> input;

	public DomAutoCompleteBackdrop(DomAutoCompleteInput<?> input) {

		this.input = input;

		addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_BACKDROP);

		getDomEngine().setPreventDefaultOnMouseDown(this, true);
	}

	@Override
	public void handleClick(IDomEvent event) {

		input.onBackdropClickOrEscape();
	}
}

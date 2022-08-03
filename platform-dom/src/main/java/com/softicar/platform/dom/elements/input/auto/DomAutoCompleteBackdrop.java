package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.style.CssLength;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssPosition;

class DomAutoCompleteBackdrop extends DomDiv implements IDomClickEventHandler {

	private final DomAutoCompleteInput<?> input;

	public DomAutoCompleteBackdrop(DomAutoCompleteInput<?> input) {

		this.input = input;

		addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_BACKDROP);

		setStyle(CssPosition.FIXED);
		setStyle(CssStyle.LEFT, CssLength.ZERO);
		setStyle(CssStyle.TOP, CssLength.ZERO);
		setStyle(CssStyle.RIGHT, CssLength.ZERO);
		setStyle(CssStyle.BOTTOM, CssLength.ZERO);

		getDomEngine().setPreventDefaultOnMouseDown(this, true);
	}

	@Override
	public void handleClick(IDomEvent event) {

		input.onBackdropClickOrEscape();
	}
}

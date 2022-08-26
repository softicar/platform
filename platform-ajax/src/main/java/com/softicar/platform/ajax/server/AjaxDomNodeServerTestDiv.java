package com.softicar.platform.ajax.server;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.button.DomButton;

public class AjaxDomNodeServerTestDiv extends DomDiv {

	public AjaxDomNodeServerTestDiv() {

		appendChild(
			new DomButton()//
				.setIcon(DomElementsImages.INFO.getResource())
				.setLabel(IDisplayString.create("Test123"))
				.setClickCallback(() -> executeAlert(IDisplayString.create("Test"))));
	}
}

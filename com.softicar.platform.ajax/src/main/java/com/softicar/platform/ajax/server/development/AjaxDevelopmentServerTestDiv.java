package com.softicar.platform.ajax.server.development;

import com.softicar.platform.ajax.image.AjaxImages;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;

public class AjaxDevelopmentServerTestDiv extends DomDiv {

	public AjaxDevelopmentServerTestDiv() {

		appendChild(
			new DomButton()//
				.setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource())
				.setLabel(IDisplayString.create("Test123"))
				.setClickCallback(() -> executeAlert(IDisplayString.create("Test"))));
	}

	public static void main(String[] args) {

		new AjaxDevelopmentServer(AjaxDevelopmentServerTestDiv.class)//
			.setPort(9000)
			.startAndJoin();
	}
}

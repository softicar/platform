package com.softicar.platform.demo.invoice.module.invoice;

import com.softicar.platform.demo.DemoI18n;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.popup.DomPopup;

public class DemoInvoiceAttachmentsHelpElement extends DomDiv {

	public DemoInvoiceAttachmentsHelpElement() {

		appendText(DemoI18n.ADDITIONAL_DOCUMENTS_THAT_ARE_RELATED_TO_THIS_INVOICE);

		var bar = new DomActionBar();
		var button = new DomPopupButton()//
			.setPopupFactory(FurtherInformationPopover::new)
			.setLabel(DemoI18n.MORE.concatEllipsis());

		bar.appendChild(button);
		appendChild(bar);
	}

	private class FurtherInformationPopover extends DomPopup {

		public FurtherInformationPopover() {

			configuration.setDisplayModePopover();
			appendText(DemoI18n.FURTHER_INFORMATION.concatEllipsis());
		}
	}
}

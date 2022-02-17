package com.softicar.platform.demo.module.invoice;

import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.emf.form.popup.EmfFormPopup;

public class DemoInvoiceCreationPopup extends EmfFormPopup<AGDemoInvoice> {

	public DemoInvoiceCreationPopup(AGDemoInvoice invoice) {

		super(invoice);

		prependChild(new DomMessageDiv(DomMessageType.INFO, DemoI18n.THIS_IS_A_DEMO));
	}
}

package com.softicar.platform.demo.module.invoice.paid;

import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.emf.form.EmfFormMode;
import com.softicar.platform.emf.form.IEmfFormFrame;
import com.softicar.platform.emf.form.delegator.AbstractEmfFormDelegator;

public class DemoInvoicePaymentForm extends AbstractEmfFormDelegator<AGDemoInvoicePayment> {

	private final DomDiv container;

	public DemoInvoicePaymentForm(IEmfFormFrame<AGDemoInvoicePayment> frame, AGDemoInvoicePayment tableRow) {

		super(frame, tableRow);
		this.container = appendChild(new DomDiv());
		form.setModeChangeCallback(this::handleModeChange);
	}

	private void handleModeChange(EmfFormMode mode) {

		container.removeChildren();
		switch (mode) {
		case CREATION:
			container.appendChild(new DomMessageDiv(DomMessageType.INFO, DemoI18n.CREATE_TRAIT));
			break;
		case EDIT:
			container.appendChild(new DomMessageDiv(DomMessageType.INFO, DemoI18n.EDIT_TRAIT));
			break;
		case VIEW:
			container.appendChild(new DomMessageDiv(DomMessageType.INFO, DemoI18n.VIEW_TRAIT));
			break;
		}
		container.appendChild(form);
	}
}

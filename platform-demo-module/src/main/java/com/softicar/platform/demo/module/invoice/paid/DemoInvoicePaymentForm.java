package com.softicar.platform.demo.module.invoice.paid;

import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.emf.form.EmfForm;
import com.softicar.platform.emf.form.IEmfFormFrame;

public class DemoInvoicePaymentForm extends EmfForm<AGDemoInvoicePayment> {

	private final DomDiv container;

	public DemoInvoicePaymentForm(IEmfFormFrame<AGDemoInvoicePayment> frame, AGDemoInvoicePayment tableRow) {

		super(frame, tableRow);
		this.container = prependChild(new DomDiv());
		handleModeChange(false);
	}

	// FIXME These 3 methods are the same as in DemoInvoiceForm
	// FIXME This method should maybe have an empty default implementation in IEmfForm
	private void handleModeChange(boolean editMode) {

		container.removeChildren();
		if (getTableRow().impermanent()) {
			container.appendChild(new DomMessageDiv(DomMessageType.INFO, DemoI18n.CREATE_TRAIT));
		} else if (editMode) {
			container.appendChild(new DomMessageDiv(DomMessageType.INFO, DemoI18n.EDIT_TRAIT));
		} else {
			container.appendChild(new DomMessageDiv(DomMessageType.INFO, DemoI18n.VIEW_TRAIT));
		}
	}

	// FIXME This should maybe be the default implementation of EmfForm
	@Override
	public void enterEditMode() {

		handleModeChange(true);
	}

	// FIXME This should maybe be the default implementation of EmfForm
	@Override
	public void enterViewMode() {

		handleModeChange(false);
	}
}

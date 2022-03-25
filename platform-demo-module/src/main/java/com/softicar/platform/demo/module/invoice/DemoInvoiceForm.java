package com.softicar.platform.demo.module.invoice;

import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.emf.form.EmfFormDelegator;
import com.softicar.platform.emf.form.EmfFormMode;
import com.softicar.platform.emf.form.IEmfFormFrame;

public class DemoInvoiceForm extends EmfFormDelegator<AGDemoInvoice> {

	private final DomDiv container;

	public DemoInvoiceForm(IEmfFormFrame<AGDemoInvoice> frame, AGDemoInvoice tableRow) {

		super(frame, tableRow);
		this.container = appendChild(new DomBar());
		form.setModeChangeCallback(this::handleModeChange);
	}

	private void handleModeChange(EmfFormMode mode) {

		container.removeChildren();
		switch (mode) {
		case CREATION:
			container.appendChild(new DomMessageDiv(DomMessageType.INFO, DemoI18n.CREATE));
			break;
		case EDIT:
			container.appendChild(new DomMessageDiv(DomMessageType.INFO, DemoI18n.EDIT));
			break;
		case VIEW:
			container.appendChild(new DomMessageDiv(DomMessageType.INFO, DemoI18n.VIEW));
			break;
		}
		container.appendChild(form);
	}
}

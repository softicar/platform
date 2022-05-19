package com.softicar.platform.demo.module.invoice.module.invoice;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.form.EmfFormMode;
import com.softicar.platform.emf.form.IEmfFormFrame;
import com.softicar.platform.emf.form.delegator.AbstractEmfAdjacentNodeForm;

public class DemoInvoiceForm extends AbstractEmfAdjacentNodeForm<AGDemoInvoice> {

	public DemoInvoiceForm(IEmfFormFrame<AGDemoInvoice> frame, AGDemoInvoice tableRow) {

		super(frame, tableRow);
	}

	@Override
	protected IDomNode createAdjacentNode(EmfFormMode mode, AGDemoInvoice tableRow) {

		switch (mode) {
		case CREATION:
			return new DomMessageDiv(DomMessageType.INFO, DemoI18n.CREATE);
		case EDIT:
			return new DomMessageDiv(DomMessageType.INFO, DemoI18n.EDIT);
		case VIEW:
			return new DomMessageDiv(DomMessageType.INFO, DemoI18n.VIEW);
		}
		throw new SofticarUnknownEnumConstantException(mode);
	}

	@Override
	protected AdjacentNodeSide getAdjacentNodeSide() {

		return AdjacentNodeSide.LEFT;
	}
}

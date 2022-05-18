package com.softicar.platform.demo.module.invoice.module.paid;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.demo.module.core.module.DemoI18n;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.form.EmfFormMode;
import com.softicar.platform.emf.form.IEmfFormFrame;
import com.softicar.platform.emf.form.delegator.AbstractEmfAdjacentNodeForm;

public class DemoInvoicePaymentForm extends AbstractEmfAdjacentNodeForm<AGDemoInvoicePayment> {

	public DemoInvoicePaymentForm(IEmfFormFrame<AGDemoInvoicePayment> frame, AGDemoInvoicePayment tableRow) {

		super(frame, tableRow);
	}

	@Override
	protected IDomNode createAdjacentNode(EmfFormMode mode, AGDemoInvoicePayment tableRow) {

		switch (mode) {
		case CREATION:
			return new DomMessageDiv(DomMessageType.INFO, DemoI18n.CREATE_TRAIT);
		case EDIT:
			return new DomMessageDiv(DomMessageType.INFO, DemoI18n.EDIT_TRAIT);
		case VIEW:
			return new DomMessageDiv(DomMessageType.INFO, DemoI18n.VIEW_TRAIT);
		}
		throw new SofticarUnknownEnumConstantException(mode);
	}

	@Override
	protected AdjacentNodeSide getAdjacentNodeSide() {

		return AdjacentNodeSide.TOP;
	}
}

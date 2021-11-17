package com.softicar.platform.demo.module.invoice;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGDemoInvoice extends AGDemoInvoiceGenerated implements IEmfObject<AGDemoInvoice> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getInvoiceNumber());
	}
}

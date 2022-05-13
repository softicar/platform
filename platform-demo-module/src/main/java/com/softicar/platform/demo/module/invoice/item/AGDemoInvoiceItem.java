package com.softicar.platform.demo.module.invoice.item;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.demo.module.person.AGDemoPersonModuleInstance;
import com.softicar.platform.emf.object.IEmfObject;

public class AGDemoInvoiceItem extends AGDemoInvoiceItemGenerated implements IEmfObject<AGDemoInvoiceItem> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getItem());
	}

	public AGDemoPersonModuleInstance getDemoPersonModuleInstance() {

		return getInvoice().getDemoPersonModuleInstance();
	}
}
